package com.example.chess

class King(position: Pair<Int, Int>, color: Int) : Piece(position, color) {
    override val imageRes: Int
        get() = if (color == 1) R.drawable.wk else R.drawable.bk

    override val pieceName: String get() = "k"

    override fun getMoves(board: Array<Array<Piece?>>): Array<Array<Boolean>> {
        val moves = Array(8) { Array(8) { false } }
        val (row, col) = position

        val directions = listOf(
            Pair(-1, -1), Pair(-1, 0), Pair(-1, 1),
            Pair(0, -1), Pair(0, 1),
            Pair(1, -1), Pair(1, 0), Pair(1, 1)
        )

        for ((dr, dc) in directions) {
            val newRow = row + dr
            val newCol = col + dc
            if (newRow in 0 until 8 && newCol in 0 until 8) {
                if (board[newRow][newCol] == null || board[newRow][newCol]?.color != color) {
                    moves[newRow][newCol] = true
                }
            }
        }

        return moves
    }
}
