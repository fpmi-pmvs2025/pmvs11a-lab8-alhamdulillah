package com.example.chess

class Knight(position: Pair<Int, Int>, color: Int) : Piece(position, color) {
    override val imageRes: Int
        get() = if (color == 1) R.drawable.wn else R.drawable.bn

    override val pieceName: String get() = "n"

    override fun getMoves(board: Array<Array<Piece?>>): Array<Array<Boolean>> {
        val moves = Array(8) { Array(8) { false } }

        val directions = listOf(
            Pair(-2, -1), Pair(-2, 1), Pair(2, -1),
            Pair(2, 1), Pair(-1, -2),
            Pair(-1, 2), Pair(1, -2), Pair(1, 2)
        )

        for ((dr, dc) in directions) {
            val newRow = position.first + dr
            val newCol = position.second + dc

            if (newRow in 0 until 8 && newCol in 0 until 8) {
                if (board[newRow][newCol] == null || board[newRow][newCol]?.color != color) {
                    moves[newRow][newCol] = true
                }
            }
        }

            return moves
    }
}