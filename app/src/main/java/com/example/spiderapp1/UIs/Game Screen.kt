package com.example.spiderapp1.UIs

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.SegmentedButtonDefaults.Icon
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.spiderapp1.GameViewModel
import com.example.spiderapp1.Models.Actions
import com.example.spiderapp1.Models.Block
import com.example.spiderapp1.Models.LeaderBoard
import com.example.spiderapp1.ui.theme.SpiderApp1Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun GameScreen(viewModel: GameViewModel,navController: NavController){
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        while(viewModel.gameActive) {
            if (viewModel.turns >= 1) {
                viewModel.vecnaHealth++
            }
            delay(1000)
        }
    }
    LaunchedEffect(viewModel.vecnaHealth,viewModel.currentBlock) {
        if (viewModel.vecnaHealth>viewModel.userHealth){
            viewModel.winner="VECNA"
            viewModel.gameActive=false
            viewModel.saveScore("VECNA",viewModel.vecnaHealth-viewModel.userHealth)
            navController.navigate("final")
        }
        if (viewModel.currentBlock.exitBlock && viewModel.vecnaHealth < viewModel.userHealth){
            viewModel.winner="USER"
            viewModel.gameActive=false
            viewModel.saveScore("USER",viewModel.userHealth-viewModel.vecnaHealth)
            navController.navigate("final")
        }
    }
    BoxWithConstraints(modifier = Modifier
        .fillMaxSize()
        .background(
        Brush.verticalGradient(
            listOf(
                Color.Black,
                Color(0xFF180000),
                Color.Black
            )
        )
    ).padding(top = 40.dp, bottom = 40.dp, start = 20.dp, end = 20.dp)) {

        val width = maxWidth
        val height = maxHeight
        var damageFlash by remember {
            mutableStateOf(false)
        }
        var healFlash by remember {
            mutableStateOf(false)
        }
        val dangerRatio = viewModel.vecnaHealth.toFloat() / viewModel.userHealth.toFloat()
        var previousHealth by remember {
            mutableStateOf(viewModel.userHealth)
        }
        val vecnaScale = remember {
            Animatable(1f)
        }
        var bright by remember {
            mutableStateOf(true)
        }

        LaunchedEffect(Unit) {
            while(true){
                bright = !bright
                delay(600)
            }
        }
        LaunchedEffect(viewModel.userHealth){
            if(viewModel.userHealth < previousHealth){
                damageFlash = true
                delay(200)
                damageFlash = false
            }
            previousHealth = viewModel.userHealth
        }
        LaunchedEffect(viewModel.vecnaHealth){
            vecnaScale.animateTo(
                1.2f,
                tween(150)
            )
            vecnaScale.animateTo(
                1f,
                tween(150)
            )
        }
        if(damageFlash){
            Box(
                Modifier
                    .fillMaxSize()
                    .background(
                        Color.Red.copy(alpha = 0.25f)
                    )
            )
        }
        if (healFlash) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(
                        Color.Green.copy(alpha = 0.20f)
                    )
            )
        }
        Column(modifier = Modifier.align(Alignment.TopCenter)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column() {
                    Text(
                        text = "USER",
                        fontSize = 15.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "HEALTH",
                        fontSize = 15.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = String.format("%03d HP", viewModel.userHealth),
                        fontSize = 15.sp,
                        color =
                            when {
                                viewModel.userHealth > 100 -> Color.Green
                                viewModel.userHealth > 50 -> Color.Yellow
                                else -> Color.Red
                            },
                        fontWeight = FontWeight.Bold
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "POWERUPS: ${viewModel.powerUpCount}",
                        fontSize = 15.sp,
                        color = Color.White
                    )
                    Button(
                        onClick = {
                            viewModel.powerUpCount--
                            viewModel.userHealth = viewModel.userHealth + 30
                            viewModel.powerUpEnabled = false
                            healFlash = true
                            scope.launch {
                                delay(300)
                                healFlash = false
                            }
                            scope.launch {
                                Toast.makeText(
                                    context,
                                    "HEALTH RESTORED WITH POWERUP",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }, colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6B0000),
                            contentColor = Color.White
                        ),
                        enabled = if (viewModel.powerUpCount > 0 && viewModel.powerUpEnabled) true else false
                    ) {
                        Text(
                            text = "USE",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "VECNA",
                        fontSize = 15.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "POWER",
                        fontSize = 15.sp,
                        color = Color.Gray
                    )
                    if (viewModel.turns >= 1) {
                        Text(
                            text = "${viewModel.vecnaHealth} HP",
                            fontSize = 15.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.graphicsLayer {
                                scaleX = vecnaScale.value
                                scaleY = vecnaScale.value
                            }
                        )
                    }
                }

            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (viewModel.turns>= 1) {
                    Text(
                        text = "⚠ VECNA IS GROWING STRONGER ⚠",
                        color =
                            if (bright)
                                Color.Red
                            else
                                Color(0xFF990000),
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp
                    )
                }
                Text(
                    text =
                        when {

                            dangerRatio < 0.25f ->
                                "The gate is stable..."

                            dangerRatio < 0.50f ->
                                "The spores are spreading..."

                            dangerRatio < 0.75f ->
                                "You hear Vecna's voice..."

                            dangerRatio < 0.90f ->
                                "Vecna is approaching..."

                            dangerRatio < 1f ->
                                "THE GATE IS COLLAPSING!"

                            else ->
                                "HAWKINS HAS FALLEN"
                        },
                    color =
                        when {

                            dangerRatio < 0.50f ->
                                Color(0xFFAA5555)

                            dangerRatio < 0.90f ->
                                Color.Yellow

                            else ->
                                Color.Red
                        },
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }


            Box(modifier=Modifier
                .width(0.9 * width)
                .height(0.4 * height)
                .background(Color.Black)
                .align(Alignment.Center)){
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)){
                    val list1 = viewModel.listOfBlocks.slice(0..4)
                    Row(horizontalArrangement = Arrangement.spacedBy(2.dp),modifier=Modifier.weight(1f)){
                        for (block in list1){
                            GridCell(block = block,modifier=Modifier
                                .weight(1f)
                                .aspectRatio(1f))
                        }
                    }
                    val list2 = viewModel.listOfBlocks.slice(5..9)
                    Row(horizontalArrangement = Arrangement.spacedBy(2.dp),modifier=Modifier.weight(1f)){
                        for (block in list2){
                            GridCell(block = block,modifier=Modifier
                                .weight(1f)
                                .aspectRatio(1f))
                        }
                    }
                    val list3 = viewModel.listOfBlocks.slice(10..14)
                    Row(horizontalArrangement = Arrangement.spacedBy(2.dp),modifier=Modifier.weight(1f)){
                        for (block in list3){
                            GridCell(block = block,modifier=Modifier
                                .weight(1f)
                                .aspectRatio(1f))
                        }
                    }
                    val list4 = viewModel.listOfBlocks.slice(15..19)
                    Row(horizontalArrangement = Arrangement.spacedBy(2.dp),modifier=Modifier.weight(1f)){
                        for (block in list4){
                            GridCell(block = block,modifier=Modifier
                                .weight(1f)
                                .aspectRatio(1f))
                        }
                    }
                    val list5 = viewModel.listOfBlocks.slice(20..24)
                    Row(horizontalArrangement = Arrangement.spacedBy(2.dp),modifier=Modifier.weight(1f)){
                        for (block in list5){
                            GridCell(block = block,modifier=Modifier
                                .weight(1f)
                                .aspectRatio(1f))
                        }
                    }
            }
        }

        Row(modifier=Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .align(Alignment.BottomCenter), horizontalArrangement = Arrangement.SpaceBetween){
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(2.dp)){
                val rotation = remember { Animatable(0f) }
                val scope = rememberCoroutineScope()
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(
                            Color(0xFF6B0000), shape = RoundedCornerShape(12.dp)
                        )
                        .border(2.dp, Color.Red, RoundedCornerShape(12.dp))
                        .graphicsLayer { rotationZ = rotation.value },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${viewModel.diceValue}",
                        fontWeight = FontWeight.Bold,
                        modifier=Modifier.align(Alignment.Center),
                        color = Color.White
                    )
                }
                Button(
                    onClick = {
                        scope.launch {
                            rotation.snapTo(0f)
                            launch {
                                rotation.animateTo(
                                    targetValue = 1080f,
                                    animationSpec = tween(1000))
                            }
                            repeat(20) {
                                viewModel.diceValue = (1..10).random()
                                delay(50)
                            }
                            viewModel.diceValue = (1..10).random()
                        }
                        viewModel.turns++
                        viewModel.saveState()
                        viewModel.diceTurn=false
                        viewModel.enablingButtons()
                              }, colors= ButtonDefaults.buttonColors(containerColor = Color(0xFF6B0000),
                        contentColor = Color.White),
                    enabled = if (viewModel.diceTurn)true else false
                ) {
                    Text(
                        text="ROLL",
                        fontWeight = FontWeight.Bold)
                }

            }
            Button(onClick={viewModel.undo()}, enabled = if(viewModel.listOfStates.isNotEmpty()  && viewModel.allButtonEnabled) true else false,
                colors= ButtonDefaults.buttonColors(containerColor = Color(0xFF6B0000),
                    contentColor = Color.White)){
                Text(
                    text="UNDO",
                    fontWeight=FontWeight.Bold
                )
            }
            Box(modifier=Modifier.border(width=2.dp,color=Color.White)) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(
                        onClick = {viewModel.nextGrid(action=Actions.UP,context=context)},
                        enabled = viewModel.upEnabled && viewModel.allButtonEnabled,
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = Color.White,
                            disabledContentColor = Color.Black
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowUp,
                            contentDescription = "Up Arrow",
                            modifier = Modifier.size(44.dp)
                        )
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        IconButton(
                            onClick = {viewModel.nextGrid(action=Actions.LEFT,context=context)},
                            enabled = viewModel.leftEnabled && viewModel.allButtonEnabled,
                            colors = IconButtonDefaults.iconButtonColors(
                                contentColor = Color.White,
                                disabledContentColor = Color.Black
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowLeft,
                                contentDescription = "Left Arrow",
                                modifier = Modifier.size(44.dp)
                            )
                        }
                        IconButton(
                            onClick = {viewModel.nextGrid(action=Actions.RIGHT,context=context)},
                            enabled = viewModel.rightEnabled && viewModel.allButtonEnabled,
                            colors = IconButtonDefaults.iconButtonColors(
                                contentColor = Color.White,
                                disabledContentColor = Color.Black
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                contentDescription = "Right Arrow",
                                modifier = Modifier.size(44.dp)
                            )
                        }

                    }
                    IconButton(
                        onClick = {viewModel.nextGrid(action=Actions.DOWN,context=context)},
                        enabled = viewModel.downEnabled && viewModel.allButtonEnabled,
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = Color.White,
                            disabledContentColor = Color.Black
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Down Arrow",
                            modifier = Modifier.size(44.dp)
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun GridCell(
    modifier: Modifier = Modifier,
    block: Block
) {
    Box(
        modifier = modifier
            .background(
                when {
                    !block.opened ->
                        Color(0xFF1A1A1A) // Hidden

                    block.exitBlock ->
                        Color(0xFFFFD700) // Exit

                    block.enemyBlock ->
                        Color.Red // Enemy

                    block.world ->
                        Color(0xFF00D9FF) // Hawkins

                    else ->
                        Color(0xFF5C0A0A) // Upside Down
                }
            )
            .border(
                2.dp,
                Color.DarkGray
            )
    ) {

        // Start Block
        if (block.id == 1) {
            Text(
                text = "START",
                color = Color.Black,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Exit Block
        if (block.exitBlock && block.opened) {
            Text(
                text = "EXIT",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Hidden
        if (!block.opened) {
            Text(
                text = "?",
                color = Color.Gray,
                fontSize = 24.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Enemy
        if (block.enemyBlock && block.opened) {
            Text(
                text = "☠",
                color = Color.Black,
                fontSize = 18.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Powerup
        if (block.powerUpAvlbl && block.opened) {
            Text(
                text = "🔥",
                fontSize = 18.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Upside Down
        if (!block.world && block.opened) {

            Text(
                text = "✦",
                color = Color(0xFFAA5555),
                fontSize = 10.sp,
                modifier = Modifier.align(Alignment.TopStart)
            )

            Text(
                text = "✦",
                color = Color(0xFFAA5555),
                fontSize = 10.sp,
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnotherPreview() {
    SpiderApp1Theme {
        val viewModel: GameViewModel = viewModel()
        val navController = rememberNavController()
        GameScreen(viewModel=viewModel, navController = navController)
    }
}