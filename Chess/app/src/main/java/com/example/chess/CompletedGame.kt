package com.example.chess

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "completed_games")
data class CompletedGame(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val positionHistory: String,
    val endTime: Long
)
