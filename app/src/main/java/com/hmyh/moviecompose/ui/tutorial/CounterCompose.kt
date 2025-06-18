package com.hmyh.moviecompose.ui.tutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmyh.moviecompose.ui.theme.MovieComposeTheme

class CounterCompose: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieComposeTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding->
                    MyApp(innerPadding)
                }
            }
        }
    }
}

@Composable
private fun MyApp(innerPadding: PaddingValues){

    var moneyCounter = remember{
        mutableStateOf(0)
    }

    Surface(
        modifier = Modifier.fillMaxSize().padding(innerPadding),
        color = Color(0xFF90A4AE)
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "$${moneyCounter.value}",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White)
            Spacer(modifier = Modifier.height(24.dp))
            CreateCircle(moneyCounter.value){newValue->
                moneyCounter.value = newValue
            }



        }

    }
}


@Composable()
private fun CreateCircle(moneyCounter: Int = 0,updateMoneyCounter: (Int)->Unit){

    Card (
        modifier = Modifier
            .padding(4.dp)
            .size(120.dp),
        shape = CircleShape,
        elevation = CardDefaults.cardElevation(4.dp),
        onClick = {
            updateMoneyCounter(moneyCounter +1)
        }
    ){
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = "Tap",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                fontSize = 20.sp)
        }

    }

}

@Preview
@Composable
private fun Preview(){
    MovieComposeTheme {
        MyApp(innerPadding = PaddingValues(0.dp))
    }
}