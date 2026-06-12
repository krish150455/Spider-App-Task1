package com.example.spiderapp1.UIs

import android.R.attr.maxHeight
import android.R.attr.maxWidth
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
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

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun FinalScreen(viewModel: GameViewModel,navController: NavController){
    BoxWithConstraints(modifier = Modifier.fillMaxSize().background(
        Brush.verticalGradient(
            listOf(
                Color.Black,
                Color(0xFF2A0000),
                Color.Black
            )
        )
    ).padding(20.dp)) {
     val width = maxWidth
     val height = maxHeight
        Column(modifier=Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally){
            Text(
                text =
                    if(viewModel.winner == "USER")
                        "HAWKINS SAVED"
                    else
                        "VECNA HAS WON",
                fontWeight = FontWeight.ExtraBold,
                color =
                    if(viewModel.winner == "USER")
                        Color.Cyan
                    else
                        Color.Red,
                fontSize = 38.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = androidx.compose.ui.text.TextStyle(
                    shadow = Shadow(
                        color =
                            if (viewModel.winner == "USER")
                                Color.Cyan
                            else
                                Color.Red,
                        offset = Offset.Zero,
                        blurRadius = 30f
                    )
                )
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth().padding(15.dp)){
                Row(horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth().weight(1f).background(Color(0xFF6B0000))
                        .border(
                            2.dp,
                            Color.Red
                        ).padding(15.dp).clickable{
                        navController.navigate("replay")
                    }){
                    Text(
                        text="VIEW REPLAY",
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
                        navController.navigate("home")
                    }){
                    Text(
                        text="GO HOME",
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
fun MeowPreview() {
    SpiderApp1Theme {
        val viewModel: GameViewModel = viewModel()
        val navController = rememberNavController()
        FinalScreen(viewModel=viewModel, navController = navController)
    }
}