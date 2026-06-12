package com.example.spiderapp1.UIs

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.unit.times
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.spiderapp1.GameViewModel
import com.example.spiderapp1.ui.theme.SpiderApp1Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun ReplayScreen(viewModel: GameViewModel,navController: NavController){
    BoxWithConstraints(modifier=Modifier.fillMaxSize().background(
        Brush.verticalGradient(
            listOf(
                Color.Black,
                Color(0xFF2A0000),
                Color.Black
            )
        )
    ).padding(vertical=40.dp, horizontal = 20.dp)) {

        val width = maxWidth
        val height = maxHeight
        var currentTurn by remember {
            mutableStateOf(1)
        }
        val scale = remember {
            Animatable(1f)
        }
        var list by remember {
            mutableStateOf(
                viewModel.listOfStates.firstOrNull()?.blocks ?: emptyList()
            )
        }
        LaunchedEffect(currentTurn) {
            scale.snapTo(1.1f)
            scale.animateTo(
                1f,
                tween(400)
            )
        }
        LaunchedEffect(Unit) {
            for ((index, listie) in viewModel.listOfStates.withIndex()) {
                currentTurn = index + 1
                list = listie.blocks
                delay(1000)
            }
        }
        Column(modifier=Modifier.align(Alignment.TopCenter), horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF6B0000))
                    .border(
                        2.dp,
                        Color.Red
                    )
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text ="GAME REPLAY",
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    tint = Color.White,
                    modifier = Modifier.clickable {
                        navController.navigate("home")
                    }
                )
            }
            Spacer(modifier=Modifier.height(20.dp))
            Text(
                text = "TURN $currentTurn / ${viewModel.listOfStates.size}",
                color = Color.Red,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
        Box(modifier=Modifier
            .width(0.9 * width)
            .height(0.4 * height)
            .graphicsLayer {
                scaleX = scale.value
                scaleY = scale.value
            }
            .background(Color.Black).border(
                2.dp,
                Color.Red
            )
            .align(Alignment.Center)){
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)){
                        val list1 = list.slice(0..4)
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(2.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            for (block in list1) {
                                GridCell(
                                    block = block, modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f)
                                )
                            }
                        }
                        val list2 = list.slice(5..9)
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(2.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            for (block in list2) {
                                GridCell(
                                    block = block, modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f)
                                )
                            }
                        }
                        val list3 = list.slice(10..14)
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(2.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            for (block in list3) {
                                GridCell(
                                    block = block, modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f)
                                )
                            }
                        }
                        val list4 = list.slice(15..19)
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(2.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            for (block in list4) {
                                GridCell(
                                    block = block, modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f)
                                )
                            }
                        }
                        val list5 = list.slice(20..24)
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(2.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            for (block in list5) {
                                GridCell(
                                    block = block, modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f)
                                )
                            }
                        }
            }
        }
    }
}

