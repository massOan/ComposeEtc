package com.example.compose_etc

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.example.compose_etc.ui.theme.Compose_etcTheme
import com.example.compose_etc.R.drawable
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContent {
            Compose_etcTheme {
                TopLevel()
            }
        }
    }

    companion object {
        const val BASE_URL = "https://picsum.photos/"

    /*   val placeHoldercardData = CardData(
            imageUrl = "${BASE_URL}id/237/200/300",
            imageDescription = "더미이미지",
            author = "picsum",
            description = "Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups."
           )
    */

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Compose_etcTheme {
    }
}



data class ToDodata(
    val key: Int,
    val text: String,
    val done: Boolean = false,
)

@Preview(showBackground = true)
@Composable
fun TopLevel() {
    val (text, setText) = remember {
        mutableStateOf("")
    }

    val toDoList = remember { mutableStateListOf<ToDodata>() }
    // MutableStateList가 추가, 삭제, 변경되었을 때만 UI갱신
    // 항목 하나의 길을 바꾸는 것 보다 항목 자체를 바꾸는게 나을 것 같다.
    val onSubmit: (String) -> Unit = { text ->
        val key = (toDoList.lastOrNull()?.key ?: 0) + 1
        toDoList.add(ToDodata(key, text))
        setText("")
    }

    val onToggle: (Int, Boolean) -> Unit = { key, checked ->
        val i = toDoList.indexOfFirst {
            it.key == key
        }
        toDoList[i] = toDoList[i].copy(done = checked)
    }

    val onDelete: (Int) -> Unit = { key ->
        val i = toDoList.indexOfFirst {
            it.key == key
        }
        toDoList.removeAt(i)
    }

    val onEdit: (Int, String) -> Unit = { key, text ->
        val i = toDoList.indexOfFirst {
            it.key == key
        }
        toDoList[i] = toDoList[i].copy(text = text)
    }

    Scaffold {
        Column(modifier = Modifier.padding(it)) {
            ToDoInput(
                text = text,
                onTextChange = setText,
                onSubmit = onSubmit,
            )

            LazyColumn() {
                items(toDoList, key = { it.key }) { toDoData ->
                    Todo(
                        toDoData = toDoData,
                        onToggle = onToggle,
                        onDelete = onDelete,
                        onEdit = onEdit
                    )
                }
            }
        }
    }
}


@Composable
fun Todo(
    toDoData: ToDodata,
    onEdit: (key: Int, text: String) -> Unit = { _, _ -> },
    onToggle: (key: Int, checked: Boolean) -> Unit = { _, _ -> },
    onDelete: (key: Int) -> Unit = { },
) {
    var isEditing by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier.padding(4.dp),
        elevation = 8.dp
    ) {
        Crossfade(targetState = isEditing) {
            when (it) {
                false -> {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(8.dp)

                    ) {
                        Text(toDoData.text, modifier = Modifier.weight(1f))
                        Text(text = "완료")

                        Checkbox(checked = toDoData.done, onCheckedChange = { checked ->
                            onToggle(toDoData.key, checked)
                        })
                        Button(onClick = {
                            isEditing = true
                        }) {
                            Text(text = "수정")
                        }
                        Spacer(modifier = Modifier.width(4.dp))

                        Button(onClick = {
                            onDelete(toDoData.key)

                        }) {
                            Text(text = "삭제")
                        }
                    }
                }

                true -> {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        var (newText, setNewText) = remember { mutableStateOf(toDoData.text) }

                        OutlinedTextField(
                            value = toDoData.text,
                            onValueChange = setNewText,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                        Button(onClick = {
                            onEdit(toDoData.key, newText)
                            isEditing = false
                        }) {
                            Text("완료")
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ToDoPreview() {
    Compose_etcTheme {
        Todo(ToDodata(1, "nice", true))
    }
}

@Composable
fun ToDoInput(
    text: String,
    onTextChange: (String) -> Unit,
    onSubmit: (String) -> Unit,
) {
    Row(modifier = Modifier.padding(8.dp)) {
        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = {
            onSubmit(text)

        }) {
            Text(text = "입력")
        }
    }
}


