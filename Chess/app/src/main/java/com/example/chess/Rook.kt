package com.example.chess

class Rook(position: Pair<Int, Int>, color: Int) : Piece(position, color) {
    override val imageRes: Int
        get() = if (color == 1) R.drawable.wr else R.drawable.br
    override val pieceName: String get() = "r"

    override fun getMoves(board: Array<Array<Piece?>>): Array<Array<Boolean>> {
        val moves = Array(8) { Array(8) { false } }
        val (row, col) = position

        val directions = listOf(
            Pair(-1, 0), Pair(1, 0),
            Pair(0, -1), Pair(0, 1)
        )

        for ((dr, dc) in directions) {
            var newRow = row + dr
            var newCol = col + dc
            while (newRow in 0 until 8 && newCol in 0 until 8) {
                if (board[newRow][newCol] == null) {
                    moves[newRow][newCol] = true
                } else if (board[newRow][newCol]?.color != color) {
                    moves[newRow][newCol] = true
                    break
                } else {
                    break
                }
                newRow += dr
                newCol += dc
            }
        }

        return moves
    }
}
