package com.example.spiderapp1

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object LeaderboardSerializer : Serializer<Leaderboard> {

    override val defaultValue: Leaderboard =
        Leaderboard.getDefaultInstance()

    override suspend fun readFrom(
        input: InputStream
    ): Leaderboard {
        try {
            return Leaderboard.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException(
                "Cannot read proto.",
                exception
            )
        }
    }

    override suspend fun writeTo(
        t: Leaderboard,
        output: OutputStream
    ) {
        t.writeTo(output)
    }
}