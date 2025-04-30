package com.example.chess

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CompletedGameDao {
    @Insert
    suspend fun insert(game: CompletedGame)

    @Query("SELECT * FROM completed_games")
    suspend fun getAllGames(): List<CompletedGame>

    @Query("SELECT * FROM completed_games WHERE id = :gameId")
    suspend fun getGameById(gameId: Int): CompletedGame?
}
