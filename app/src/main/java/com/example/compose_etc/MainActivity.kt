package com.example.compose_etc

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.compose_etc.ui.theme.Compose_etcTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Compose_etcTheme {

                // A surface container using the 'background' color from the theme
                CoilFunction()

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Compose_etcTheme {
        CoilFunction()
    }
}


@Composable
fun TextFunction() {
    Text(
        modifier = Modifier.width(300.dp),
        text = "text",
        color =  Color.Blue,
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Monospace,
        textAlign = TextAlign.Center
    )
}

@Composable
fun ButtonFunction(ButtonClicked : () -> Unit) {
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
        modifier =  Modifier.width(300.dp),
        elevation = 5.dp
    )  {

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
    Image(modifier = Modifier.width(500.dp).height(500.dp), painter = painter, contentDescription = null)

}