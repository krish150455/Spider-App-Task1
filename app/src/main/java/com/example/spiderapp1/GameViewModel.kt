package com.example.spiderapp1

import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spiderapp1.Models.Actions
import com.example.spiderapp1.Models.Block
import com.example.spiderapp1.Models.GameState
import com.example.spiderapp1.Models.LeaderBoard
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GameViewModel(private val repository: LeaderboardRepository): ViewModel() {

    var userHealth by mutableStateOf(200)
    var vecnaHealth by mutableStateOf(5)
    var powerUpCount by mutableStateOf(0)
    var turns by mutableStateOf(0)
    var winner by mutableStateOf("")
    var gameActive by mutableStateOf(true)

    var currentBlock by mutableStateOf(Block(1,1,1,true,3,false,false,false,true))

    var listOfBlocks by mutableStateOf<List<Block>>(listOf(
        Block(1,1,1,true,3,false,false,false,true),
        Block(2,1,2,true,5,false,false,false,true),
        Block(3,1,3,false,3,false,true,true,true),
        Block(4,1,4,true,6,false,false,false,false),
        Block(5,1,5,true,7,false,false,false,false),
        Block(6,2,1,false,4,false,false,true,false),
        Block(7,2,2,true,8,false,false,false,false),
        Block(8,2,3,true,9,false,false,false,false),
        Block(9,2,4,false,2,false,true,false,false),
        Block(10,2,5,true,1,false,false,true,false),
        Block(11,3,1,true,7,false,false,false,false),
        Block(12,3,2,false,8,true,false,false,true),
        Block(13,3,3,true,5,false,false,false,false),
        Block(14,3,4,true,3,false,false,false,false),
        Block(15,3,5,false,3,false,true,false,false),
        Block(16,4,1,true,6,false,false,false,false),
        Block(17,4,2,true,7,false,false,true,false),
        Block(18,4,3,false,4,false,false,false,false),
        Block(19,4,4,true,8,false,false,false,false),
        Block(20,4,5,true,9,false,true,false,false),
        Block(21,5,1,false,2,false,false,false,false),
        Block(22,5,2,true,1,false,false,true,false),
        Block(23,5,3,true,7,false,false,false,false),
        Block(24,5,4,false,8,false,false,false,false),
        Block(25,5,5,true,9,false,false,false,false)

    ))

    //RESET GAME MANAGEMENT
    fun resetGame(){
        listOfStates=listOf()
        generateBoard()
        diceValue=1
        diceTurn=true
        allButtonEnabled = false
        upEnabled=true
        downEnabled=true
        rightEnabled=true
        leftEnabled=true
        powerUpEnabled=false
        userHealth=200
        vecnaHealth=5
        powerUpCount=0
        turns=0
        winner=""
        gameActive=true
        currentBlock=listOfBlocks[0]
    }

    fun generateBoard() {
        val exitId = (2..25).random()
        listOfBlocks = listOfBlocks.map { block ->
            if (block.id == exitId) {
                block.copy(
                    unlockNum = (1..10).random(),
                    exitBlock = true,
                    enemyBlock = false,
                    powerUpAvlbl = false,
                    world = true,
                    opened=false
                )
            } else {
                block.copy(
                    unlockNum = if (block.id == 1) 0 else (1..10).random(),
                    exitBlock = false,
                    opened = if (block.id == 1) true else false
                )
            }
        }
    }

    //LEADERBOARD MANAGEMENT
    val leaderboard = repository.Leaderboardflow.stateIn(
        scope=viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Leaderboard.getDefaultInstance()
    )

    fun saveScore(winner:String,score:Int) {
        viewModelScope.launch {

            repository.addPlayer(winner,score)

        }
    }

    //REPLAY SCREEN AND UNDO MANAGEMENT
    var listOfStates by mutableStateOf<List<GameState>>(listOf())
    fun saveState() {
        val snapshot = GameState(
            blocks = listOfBlocks.map { it.copy() },
            currentBlock = currentBlock.copy(),
            userHealth = userHealth,
            vecnaHealth = vecnaHealth,
            powerUpCount = powerUpCount,
            turns = turns
        )
        listOfStates = listOfStates.toMutableList().apply {
            add(snapshot)
        }
    }
    fun undo() {
        if(listOfStates.isEmpty()) return
        val previousState = listOfStates.last()
        listOfBlocks = previousState.blocks
        currentBlock = previousState.currentBlock
        userHealth = previousState.userHealth
        vecnaHealth = previousState.vecnaHealth
        powerUpCount = previousState.powerUpCount
        turns = previousState.turns
        listOfStates = listOfStates.dropLast(1)
        diceTurn = true
        allButtonEnabled = false
    }

    //DICE LOGIC MANAGEMENT
    var diceValue by mutableStateOf(1)
    var diceTurn by mutableStateOf(true)

    //MOVEMENT BUTTONS LOGIC MANAGEMENT
    var upEnabled by mutableStateOf(true)
    var downEnabled by mutableStateOf(true)
    var leftEnabled by mutableStateOf(true)
    var rightEnabled by mutableStateOf(true)
    var allButtonEnabled by mutableStateOf(false)
    var powerUpEnabled by mutableStateOf(false)

    fun enablingButtons(){
        allButtonEnabled = true
        upEnabled=true
        downEnabled=true
        rightEnabled=true
        leftEnabled=true
        when(currentBlock.row){
            1->upEnabled=false
            5->downEnabled=false
        }
        when(currentBlock.column){
            1->leftEnabled=false
            5->rightEnabled=false
        }
    }
    fun nextGrid(action: Actions, context:android.content.Context) {
        val blockie = when (action) {
            Actions.UP -> {
                listOfBlocks.filter { block ->
                    (block.row == currentBlock.row - 1 && block.column == currentBlock.column)
                }[0]
            }

            Actions.DOWN -> {
                listOfBlocks.filter { block ->
                    (block.row == currentBlock.row + 1 && block.column == currentBlock.column)
                }[0]
            }

            Actions.RIGHT -> {
                listOfBlocks.filter { block ->
                    (block.column == currentBlock.column + 1 && block.row == currentBlock.row)
                }[0]
            }

            Actions.LEFT -> {
                listOfBlocks.filter { block ->
                    (block.column == currentBlock.column - 1 && block.row == currentBlock.row)
                }[0]
            }
        }
        if (blockie.unlockNum <= diceValue) {
            currentBlock=blockie
            listOfBlocks = listOfBlocks.map{
                block ->
                if (block.id==blockie.id){
                    block.copy(opened=true)
                }
                else{
                    block
                }
            }
            if (currentBlock.powerUpAvlbl) {
                powerUpCount++
                listOfBlocks = listOfBlocks.map {
                    if(it.id == currentBlock.id)
                        it.copy(powerUpAvlbl = false)
                    else
                        it
                }
            }
            if (currentBlock.enemyBlock) {
                userHealth = userHealth - 30   //IF ENEMY BLOCK, DEDUCTING 30
                viewModelScope.launch {
                    Toast.makeText(
                        context,
                        "YOU ENTERED ENEMY BLOCK. USE POWERUP IF NEEDED",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                powerUpEnabled = true
            }
            if (!currentBlock.world) {
                userHealth = userHealth - 15
            }
            allButtonEnabled=false
            diceTurn=true
        }
        else{
            viewModelScope.launch {
                Toast.makeText(
                    context,
                    "UNLOCK NUMBER NOT SATISFIED. TRY NEXT TURN.",
                    Toast.LENGTH_SHORT
                ).show()
                allButtonEnabled=false
                diceTurn=true
            }
        }
    }

    //RULES MANAGEMENT
    val rules = listOf(
        "1. Your objective is to explore the labyrinth, locate the hidden Exit Gate, and escape before Vecna becomes too powerful.",

        "2. Every turn begins by rolling a dice that generates a random number between 1 and 10.",

        "3. After rolling the dice, you may either move to an adjacent block or use the Undo feature.",

        "4. Movement is only allowed in the four cardinal directions: Up, Down, Left, and Right.",

        "5. Every hidden block has an Unlock Requirement between 1 and 10. A block can only be entered if Dice Roll is greater than or equal to the block's Unlock Requirement.",

        "6. If the Unlock Requirement is not satisfied, the move fails and the turn ends immediately.",

        "7. Hidden blocks reveal their identity only after being successfully entered.",

        "8. Hidden Blocks appear as dark gray tiles marked with a '?' symbol. Their contents remain unknown until entered.",

        "9. The START tile marks your starting position within the labyrinth.",

        "10. Hawkins Blocks (Real World) appear as bright blue tiles. These are safe zones and do not reduce your health.",

        "11. Upside Down Blocks appear as dark corrupted red tiles decorated with spore symbols (✦). Entering one immediately reduces your health by 15 HP.",

        "12. Enemy Blocks appear as red tiles marked with a skull (☠). Entering one immediately reduces your health by 30 HP.",

        "13. Powerup Blocks display a flame symbol (🔥). Entering one grants a Powerup that can later be used against enemies.",

        "14. Powerups can be used to eliminate enemies and improve your chances of survival.",

        "15. One hidden block on the board is randomly selected as the Exit Gate at the start of every game.",

        "16. The Exit Gate appears as a gold tile marked 'EXIT'. Reaching it stops Vecna's growth and triggers the final outcome of the game.",

        "17. Vecna starts every game with 5 HP.",

        "18. As soon as your journey begins, Vecna starts gathering power.",

        "19. Vecna gains 1 HP every second until the Exit Gate is discovered.",

        "20. The longer you remain inside the labyrinth, the stronger Vecna becomes.",

        "21. Entering the Exit Gate immediately stops Vecna's growth.",

        "22. To win the game, your remaining HP must be strictly greater than Vecna's HP when the Exit Gate is reached.",

        "23. If your HP is less than or equal to Vecna's HP when the Exit Gate is reached, Vecna wins and Hawkins falls.",

        "24. The Undo feature restores the previous game state, including position, health, powerups, and board progress.",

        "25. Every game is automatically recorded and can later be viewed through the Replay feature.",

        "26. Winning games are added to the Leaderboard.",

        "27. Leaderboard score is calculated as: Player HP - Vecna HP.",

        "28. Higher scores represent more dominant victories over Vecna.",

        "29. Remember: every second matters. Explore quickly, use your resources wisely, and don't let Vecna become stronger than you."
    )



}