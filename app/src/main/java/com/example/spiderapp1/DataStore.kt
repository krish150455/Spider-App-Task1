package com.example.spiderapp1

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore

val Context.leaderboards: DataStore<Leaderboard> by dataStore(fileName = "Leaderboard.pb",
    serializer = LeaderboardSerializer)

