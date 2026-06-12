package com.example.spiderapp1

import androidx.datastore.core.DataStore
import com.google.protobuf.copy

class LeaderboardRepository(private val dataStore: DataStore<Leaderboard>) {
    val Leaderboardflow = dataStore.data

    suspend fun addPlayer(winner: String, score: Int){
            dataStore.updateData { leaderboard ->
                leaderboard.toBuilder()
                    .addPlayers(
                        Player.newBuilder()
                            .setWinner(winner)
                            .setScore(score)
                            .build()
                    )
                    .build()
            }

    }
}