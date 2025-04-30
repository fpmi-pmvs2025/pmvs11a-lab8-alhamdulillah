package com.example.chess

abstract class Piece(var position: Pair<Int, Int>, val color: Int) {
    abstract val imageRes: Int
    abstract val pieceName: String

    abstract fun getMoves(board: Array<Array<Piece?>>): Array<Array<Boolean>>

    open fun moveTo(newPosition: Pair<Int, Int>) {
        val board = ChessBoardManager.getBoard()
        board[position.first][position.second] = null
        board[newPosition.first][newPosition.second] = this
        position = newPosition
    }

    override fun toString(): String {
        if (color == 1) {
            return "w$pieceName"
        }
        else {
            return "b$pieceName"
        }

    }
}
