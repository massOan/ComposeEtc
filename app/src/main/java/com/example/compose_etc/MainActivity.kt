package com.example.compose_etc

import android.content.ClipData.Item
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.example.compose_etc.MainActivity.Companion.placeHoldercardData
import com.example.compose_etc.ui.theme.Compose_etcTheme
import com.example.compose_etc.R.drawable
import kotlin.contracts.contract


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContent {
            Compose_etcTheme {
                CatalogFuntion(items)
            }
        }
    }

    companion object {
        const val BASE_URL = "https://picsum.photos/"

        val placeHoldercardData = CardData(
            imageUrl = "${BASE_URL}id/237/200/300",
            imageDescription = "더미이미지",
            author = "picsum",
            description = "Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups."

        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Compose_etcTheme {
        ConstraintSetFunction()
    }
}


@Composable
fun TextFunction() {
    Text(
        modifier = Modifier.width(300.dp),
        text = "text",
        color = Color.Blue,
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Monospace,
        textAlign = TextAlign.Center
    )
}

@Composable
fun ButtonFunction(ButtonClicked: () -> Unit) {
    Button(
        onClick = ButtonClicked,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Blue,
            contentColor = Color.White
        ),

        border = BorderStroke(1.dp, Color.Black),
        shape = CircleShape

    ) {
        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
        Spacer(modifier = Modifier.width(5.dp))
        Text(text = "Send Button")
    }
}

@Composable
fun SurfaceFunction() {
    Surface(
        modifier = Modifier.width(300.dp),
        elevation = 5.dp
    ) {

    }
}

@Composable
fun BoxWithConstraints() {
    Column() {
        Inner(Modifier.widthIn(min = 100.dp, max = 200.dp))
    }

}

@Composable
private fun Inner(modifier: Modifier = Modifier) {

    BoxWithConstraints(modifier) {
        if (maxWidth > 200.dp) {
            Text("Modifier : Min width : ${minWidth}")

        }
    }
}
// 스텝 3: rememberImagePainter를 이용해 Image의 painter를 설정합니다.
// (Compose 한국어 문서의 추천, but Deprecated)
// 이미지 URI: https://raw.githubusercontent.com/Fastcampus-Android-Lecture-Project-2023/part1-chapter3/main/part-chapter3-10/app/src/main/res/drawable-hdpi/wall.jpg

// 스텝 4: AsyncImage를 이용해봅니다. model에 주소를 적으면 됩니다.

@Composable
private fun CoilFunction() {
    val painter = rememberImagePainter(data = "https://picsum.photos/id/237/200/300")
    Image(
        modifier = Modifier
            .width(500.dp)
            .height(500.dp), painter = painter, contentDescription = null
    )

}

@Composable
private fun ProfileCardFunction(cardData: CardData) {
    Card(
        elevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            AsyncImage(
                model = cardData.imageUrl,
                contentDescription = "${cardData.imageDescription}",
                modifier = Modifier.clip(CircleShape),
                placeholder = ColorPainter(Color.Gray)

            )
            Spacer(modifier = Modifier.width(10.dp))
            Column() {
                Text(
                    text = cardData.author
                )

                Text(
                    text = cardData.description,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Composable
private fun CheckboxFuction() {
    var (ischecked, setCheck) = remember { mutableStateOf(false) }
    //비 구조화를 이용 문법
    //컴포저블함수는 언제든지 다시 그려질 수 있고, 상황때문에 상태가 날아갈 수 있다고 가정 해야한다.
    //여러 스레드에서 동시에 여러 컴포저블이 동시에 그리고 있을 수도 있다.
    //그냥 mutableStateOf 라고 선언할 경우 상태가 날아갈 수도 있다.

    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable {
            setCheck(!ischecked)
        }) {

        Checkbox(
            checked = ischecked,
            onCheckedChange = setCheck
            //onCheckedChange 는 반전된 결과일 경우만 호출이 된다 그럴경우,
            // 굳이 왜 우리가 checked를 반전 시켜서 사용하는가? ,

        )
        Text(text = "Compose 실습중입니까?")
    }
}

data class CardData(
    val imageUrl: String,
    val imageDescription: String,
    val author: String,
    val description: String,
) {
}

@Composable
private fun textFieldFuntion() {
    var name by remember { mutableStateOf("Tom") }

    Column(modifier = Modifier.padding(18.dp)) {
        OutlinedTextField(
            value = name,
            label = { Text(text = "인간 이름 ") },
            onValueChange = { name = it })

        Spacer(modifier = Modifier.size(8.dp))
        Text(text = "Hello $name")
    }
}

@Composable
private fun TopAppBarFunction() {
    Column() {
        TopAppBar(
            title = { Text(text = "topappbar") },

            navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = ""
                    )
                }
            },

            actions = {
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Filled.Search, contentDescription = "검색")

                }
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Filled.Settings, contentDescription = "설정")

                }
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Filled.Person, contentDescription = "계정")

                }
            },
        )
    }
}

@Composable
private fun CheckBoxWithSlotFunction(
    checked: Boolean,
    onCheckedChanged: () -> Unit,
    content: @Composable RowScope.() -> Unit,

    ) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable {
            onCheckedChanged()
        }) {

        Checkbox(
            checked = checked,
            onCheckedChange = {
                onCheckedChanged()
            }
            //onCheckedChange 는 반전된 결과일 경우만 호출이 된다 그럴경우,
            // 굳이 왜 우리가 checked를 반전 시켜서 사용하는가? ,

        )
        content()
    }
}


@Composable
private fun SlotEx() {
    var checked1 by remember { mutableStateOf(false) }
    var checked2 by remember { mutableStateOf(false) }

    Column() {
        CheckBoxWithSlotFunction(checked = checked1,
            onCheckedChanged = { checked1 = !checked1 }) {
            Text("체크박스 11")

        }
        CheckBoxWithSlotFunction(checked = checked2,
            onCheckedChanged = { checked2 = !checked2 }) {
            Text("체크박스 222")

        }

    }
}


@Composable
fun ScaffoldEx2() {
    var checked by remember { mutableStateOf(false) }

    Scaffold(topBar = {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "onBackPressedIcon"
                    )
                }

            },

            title = { Text(text = "텍스트") }
        )
        // 스텝 1: `topBar`를 `TopAppBar`로 채워 봅시다.

    }) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column {
                CheckBoxWithSlotFunction(checked = checked,
                    onCheckedChanged = { checked = !checked }) {
                    Text("체크박스 11")
                }
            }
        }
    }
}

@Composable
fun Item(itemData: ItemData) {
    Card(
        elevation = 8.dp,
        modifier = Modifier.padding(16.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = painterResource(id = itemData.imageId),
                contentDescription = itemData.title
            )
            Spacer(modifier = Modifier.padding(8.dp))

            Text(text = itemData.title)

            Spacer(modifier = Modifier.padding(8.dp))

            Text(text = itemData.description)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemPreview() {
    Compose_etcTheme {
        CatalogFuntion(items)
    }
}


@Composable
fun CatalogFuntion(itemList: List<ItemData>) {

    LazyColumn {
        items(itemList) { it ->
            Item(it)
        }
    }
}


data class ItemData(

    @DrawableRes val imageId: Int,
    val title: String,
    val description: String,
) {}

val items = listOf(

    ItemData(
        imageId = drawable.a1,
        title = "해변 놀이 공원",
        description = "해변 놀이 공원 설명입니다. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. "
    ),
    ItemData(
        imageId = drawable.a2,
        title = "캐년",
        description = "미국의 캐년입니다. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. "
    ),
    ItemData(
        imageId = drawable.a3,
        title = "워터월드",
        description = "워터월드입니다. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. "
    ),
    ItemData(
        imageId = drawable.a4,
        title = "미국의 캐년",
        description = "미국의 캐년입니다. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. "
    ),
    ItemData(
        imageId = drawable.a5,
        title = "라스베가스",
        description = "라스베가스입니다. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. "
    ),
    ItemData(
        imageId = drawable.a6,
        title = "호르슈 밴드",
        description = "호르슈 밴드입니다. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. "
    ),
    ItemData(
        imageId = drawable.a7,
        title = "미국의 길",
        description = "미국의 길입니다. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. "
    ),
    ItemData(
        imageId = drawable.a8,
        title = "엔텔로프 캐년",
        description = "엔텔로프 캐년입니다. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. "
    ),
    ItemData(
        imageId = drawable.a9,
        title = "그랜드 캐년",
        description = "그랜드 캐년입니다. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. "
    )
)


@Composable
fun ConstraintsLayoutFunction() {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
    }


    @Composable
    fun ConstraintSetFunction() {

        val constraintSet = ConstraintSet {

            val redBox = createRefFor("redBox")
            val magentaBox = createRefFor("magentaBox")
            val greenBox = createRefFor("greenBox")
            val yellowBox = createRefFor("yellowBox")


            constrain(redBox) {
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
            }

            constrain(magentaBox) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }

            constrain(greenBox) {
                centerTo(parent)

            }

            constrain(yellowBox) {
                top.linkTo(greenBox.bottom)
                start.linkTo(greenBox.end)
            }

        }

        ConstraintLayout(
            constraintSet,
            modifier = Modifier.fillMaxSize()
        ) {

//        val (redBox, magentaBox, greenBox, yellowBox) = createRefs()

            Box(
                modifier =
                Modifier
                    .size(40.dp)
                    .background(Color.Red)
                    .layoutId("redBox")

            ) {

            }
            Box(
                modifier =
                Modifier
                    .size(40.dp)
                    .background(Color.Magenta)
                    .layoutId("magentaBox")

            ) {

            }
            Box(
                modifier =
                Modifier
                    .size(40.dp)
                    .background(Color.Green)
                    .layoutId("greenBox")

            ) {

            }
            Box(
                modifier =
                Modifier
                    .size(40.dp)
                    .background(Color.Yellow)
                    .layoutId("yellowBox")

            ) {

            }

        }
    }
}

