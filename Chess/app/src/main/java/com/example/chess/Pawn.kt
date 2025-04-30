package com.example.chess

import android.graphics.Color

class Pawn(position: Pair<Int, Int>, color: Int) : Piece(position, color) {
    override val imageRes: Int
        get() = if (color == 1) R.drawable.wp else R.drawable.bp
    override val pieceName: String get() = "p"

    override fun getMoves(board: Array<Array<Piece?>>): Array<Array<Boolean>> {
        val moves = Array(8) { Array(8) { false } }
        val (row, col) = position
        val direction = if (color == 1) -1 else 1

        if (row + direction in 0 until 8 && board[row + direction][col] == null) {
            moves[row + direction][col] = true
        }

        if ((row == 6 && color == Color.WHITE || row == 1 && color == Color.BLACK) &&
            board[row + direction][col] == null && board[row + 2 * direction][col] == null) {
            moves[row + 2 * direction][col] = true
        }

        if (col - 1 in 0 until 8 && row + direction in 0 until 8 &&
            board[row + direction][col - 1] != null && board[row + direction][col - 1]?.color != color) {
            moves[row + direction][col - 1] = true
        }
        if (col + 1 in 0 until 8 && row + direction in 0 until 8 &&
            board[row + direction][col + 1] != null && board[row + direction][col + 1]?.color != color) {
            moves[row + direction][col + 1] = true
        }

        return moves
    }

    override fun moveTo(newPosition: Pair<Int, Int>) {
        if ((color == 1 && newPosition.first == 0) || (color == 0 && newPosition.first == 7)) {
            val queen = Queen(newPosition, color)
            val board = ChessBoardManager.getBoard()
            board[newPosition.first][newPosition.second] = queen
            board[position.first][position.second] = null
        }
        else {
            super.moveTo(newPosition)
        }
    }
}
