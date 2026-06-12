package com.example.spiderapp1.Models

data class Block(
    val id:Int,
    val row:Int,
    val column:Int,
    val world: Boolean, //if Real World-True else if Upside Down-false //RAND
    val unlockNum: Int, //number needed to unlock the Block after dice roll  //RAND
    val exitBlock: Boolean, //if exitBlock or not //RAND
    val powerUpAvlbl: Boolean, //RAND
    val enemyBlock:Boolean, //RAND
    var opened:Boolean
)
enum class Actions{
    UP,DOWN,LEFT,RIGHT
}
data class LeaderBoard(
    val winner:String,
    val score:Int
)

data class GameState(
    val blocks: List<Block>,
    val currentBlock: Block,
    val userHealth: Int,
    val vecnaHealth: Int,
    val powerUpCount: Int,
    val turns: Int
)