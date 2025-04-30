package com.example.chess

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CompletedGame::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun completedGameDao(): CompletedGameDao
}
