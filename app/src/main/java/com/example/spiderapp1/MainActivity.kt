package com.example.spiderapp1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.spiderapp1.UIs.FinalScreen
import com.example.spiderapp1.UIs.GameScreen
import com.example.spiderapp1.UIs.HomeScreen
import com.example.spiderapp1.UIs.LeaderScreen
import com.example.spiderapp1.UIs.ReplayScreen
import com.example.spiderapp1.UIs.RulesScreen
import com.example.spiderapp1.ui.theme.SpiderApp1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpiderApp1Theme {
                val navController = rememberNavController()
                val context = LocalContext.current
                val repository = remember { LeaderboardRepository(context.leaderboards) }
                val viewModel = remember { GameViewModel(repository) }
                NavHost(navController=navController, startDestination = "home"){
                    composable("home"){
                        HomeScreen(viewModel=viewModel,navController=navController)
                    }
                    composable("game"){
                        GameScreen(viewModel=viewModel, navController = navController)
                    }
                    composable("final"){
                        FinalScreen(viewModel=viewModel,navController=navController)
                    }
                    composable("Leader Board"){
                        LeaderScreen(viewModel=viewModel,navController=navController)
                    }
                    composable("replay"){
                        ReplayScreen(viewModel=viewModel, navController = navController)
                    }
                    composable("rules"){
                        RulesScreen(navController = navController,viewModel=viewModel)
                    }
                }
            }
        }
    }
}


