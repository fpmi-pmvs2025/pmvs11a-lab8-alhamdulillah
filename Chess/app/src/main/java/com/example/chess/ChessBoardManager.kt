package com.example.chess

object ChessBoardManager {
    val pieces = Array(8) { Array(8) { null as Piece? } }

    fun getBoard(): Array<Array<Piece?>> {
        return pieces
    }

    fun resetBoard() {
        for (row in 0 until 8) {
            for (col in 0 until 8) {
                pieces[row][col] = null
            }
        }
    }
}
