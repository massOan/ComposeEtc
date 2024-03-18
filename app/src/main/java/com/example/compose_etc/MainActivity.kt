package com.example.compose_etc

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.example.compose_etc.MainActivity.Companion.placeHoldercardData
import com.example.compose_etc.ui.theme.Compose_etcTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Compose_etcTheme {
                SlotEx()
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
        TopAppBarFunction()
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
