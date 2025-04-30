package com.example.chess

import org.junit.Test
import org.junit.Assert.*

class KingTest {

    @Test
    fun testKingMovesOneStep() {
        val board = Array(8) { Array<Piece?>(8) { null } }
        val whiteKing = King(Pair(4, 4), 1)
        board[4][4] = whiteKing

        val moves = whiteKing.getMoves(board)

        assertTrue(moves[3][3]) // Top-left
        assertTrue(moves[3][4]) // Top
        assertTrue(moves[3][5]) // Top-right
        assertTrue(moves[4][3]) // Left
        assertTrue(moves[4][5]) // Right
        assertTrue(moves[5][3]) // Bottom-left
        assertTrue(moves[5][4]) // Bottom
        assertTrue(moves[5][5]) // Bottom-right

        assertFalse(moves[2][4]) // Two steps forward is not allowed
        assertFalse(moves[6][4]) // Two steps backward is not allowed
    }

    @Test
    fun testKingCannotMoveToOwnPiece() {
        val board = Array(8) { Array<Piece?>(8) { null } }
        val whiteKing = King(Pair(4, 4), 1)
        val whitePawn = Pawn(Pair(3, 4), 1)
        board[4][4] = whiteKing
        board[3][4] = whitePawn

        val moves = whiteKing.getMoves(board)

        assertFalse(moves[3][4]) // Cannot move to own piece
    }

    @Test
    fun testKingCanCaptureOpponentPiece() {
        val board = Array(8) { Array<Piece?>(8) { null } }
        val whiteKing = King(Pair(4, 4), 1)
        val blackPawn = Pawn(Pair(3, 4), 0)
        board[4][4] = whiteKing
        board[3][4] = blackPawn

        val moves = whiteKing.getMoves(board)

        assertTrue(moves[3][4])
    }
}
