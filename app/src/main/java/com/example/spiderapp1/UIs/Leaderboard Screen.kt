package com.example.spiderapp1.UIs

import android.annotation.SuppressLint
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.spiderapp1.GameViewModel
import com.example.spiderapp1.Leaderboard
import com.example.spiderapp1.ui.theme.SpiderApp1Theme
import kotlinx.coroutines.delay

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun LeaderScreen(viewModel: GameViewModel,navController: NavController){
    var bright by remember {
        mutableStateOf(true)
    }
    val leaderboard by viewModel.leaderboard.collectAsState()

    LaunchedEffect(Unit) {
        while (true){
            bright=!bright
            delay(600)
        }
    }
    BoxWithConstraints(modifier=Modifier.fillMaxSize().background(
        Brush.verticalGradient(
            listOf(
                Color.Black,
                Color(0xFF2A0000),
                Color.Black
            )
        )
    ).padding(30.dp)) {
        Column(modifier=Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
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
                        text = "HAWKINS SURVIVORS",
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {

                Row(
                    modifier = Modifier
                        .weight(0.6f)
                        .background(Color(0xFF6B0000))
                        .border(2.dp, Color.Red),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "#",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }

                Row(
                    modifier = Modifier
                        .weight(1.5f)
                        .background(Color(0xFF6B0000))
                        .border(2.dp, Color.Red),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "WINNER",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }

                Row(
                    modifier = Modifier
                        .weight(1f)
                        .background(Color(0xFF6B0000))
                        .border(2.dp, Color.Red),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "SCORE",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            }

            Spacer(modifier=Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = rememberLazyListState(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(
                    leaderboard.playersList.sortedByDescending { it.score }
                        .withIndex()
                        .toList()
                ) { (index, item) ->

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {

                        Row(
                            modifier = Modifier
                                .weight(0.6f)
                                .background(
                                    when(index){
                                        0 -> Color(0xFFFFD700) // Gold
                                        1 -> Color(0xFFC0C0C0) // Silver
                                        2 -> Color(0xFFCD7F32) // Bronze
                                        else -> Color(0xFF6B0000)
                                    }
                                )
                                .border(2.dp, Color.Red),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "#${index + 1}",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }

                        Row(
                            modifier = Modifier
                                .weight(1.5f)
                                .background(Color(0xFF6B0000))
                                .border(2.dp, Color.Red),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = item.winner,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }

                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .background(Color(0xFF6B0000))
                                .border(2.dp, Color.Red),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "${item.score}",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RandomiePreview() {
    SpiderApp1Theme {
        val viewModel: GameViewModel = viewModel()
        val navController = rememberNavController()
        LeaderScreen(viewModel=viewModel, navController = navController)
    }
}