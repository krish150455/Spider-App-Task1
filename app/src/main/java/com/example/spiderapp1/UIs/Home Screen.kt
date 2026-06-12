package com.example.spiderapp1.UIs

import android.R.attr.scaleX
import android.R.attr.scaleY
import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.spiderapp1.GameViewModel
import com.example.spiderapp1.ui.theme.SpiderApp1Theme
import kotlinx.coroutines.delay

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun HomeScreen(viewModel: GameViewModel,navController: NavController){
    var bright by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(Unit) {
        while(true){
            bright = !bright
            delay(600)
        }
    }
    val scale = remember {
        Animatable(1f)
    }

    LaunchedEffect(Unit) {
        while (true) {
            scale.animateTo(
                1.05f,
                tween(700)
            )

            scale.animateTo(
                1f,
                tween(700)
            )
        }
    }
    BoxWithConstraints(modifier = Modifier.fillMaxSize().background(
        brush = Brush.verticalGradient(
            listOf(
                Color.Black,
                Color(0xFF2A0000)
            )
        )
    )) {

        Column(verticalArrangement = Arrangement.spacedBy(2.dp), horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier.fillMaxWidth().align(Alignment.Center)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally){
                Text(
                    text = "THE HAWKINS",
                    color = if(bright) Color.Red
                        else
                            Color(0xFF990000),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 42.sp,
                    lineHeight = 42.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "LABYRINTH",
                    color = if(bright) Color.Red
                        else
                            Color(0xFF990000),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 42.sp,
                    lineHeight = 42.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "Escape The Upside Down",
                    color = Color.Gray,
                    fontSize = 16.sp
                )
                Spacer(modifier=Modifier.height(24.dp))
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier =  Modifier
                    .fillMaxWidth(0.9f)
                    .graphicsLayer {
                        scaleX = scale.value
                        scaleY = scale.value
                    }
                    .background(Color(0xFF6B0000)).border(2.dp,Color.Red)
                    .clickable {
                        viewModel.resetGame()
                        navController.navigate("game")
                    }.padding(15.dp)) {
                Text(
                    text = "PLAY >",
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp,
                    color = Color.White
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth().padding(15.dp)){
                Row(horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth().weight(1f).background(Color(0xFF6B0000))
                        .border(
                            2.dp,
                            Color.Red
                        ).padding(15.dp).clickable{navController.navigate("Leader Board")}){
                    Text(
                        text="LEADERBOARD",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize=15.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Row(horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth(1f).weight(1f).background(Color(0xFF6B0000))
                        .border(
                            2.dp,
                            Color.Red
                        ).padding(15.dp).clickable{
                        navController.navigate("rules")
                    }){
                    Text(
                        text="RULES",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize=15.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SpiderApp1Theme {
        val viewModel: GameViewModel = viewModel()
        val navController = rememberNavController()
        HomeScreen(viewModel=viewModel, navController = navController)
    }
}