package com.example.compose_etc

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
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
import com.example.compose_etc.ui.theme.Compose_etcTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Compose_etcTheme {

                // A surface container using the 'background' color from the theme

                    ButtonFunction(ButtonClicked = {
                        Toast.makeText(this, "Send Button Clicked", Toast.LENGTH_SHORT).show()
                    })
                }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Compose_etcTheme {
        ButtonFunction(ButtonClicked = {

        } )
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
        border = BorderStroke(1.dp, Color.Black),
        shape = CircleShape

    ) {
        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
        Spacer(modifier = Modifier.width(5.dp))
        Text(text = "Send Button")
    }
}