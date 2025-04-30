package com.example.chess

import org.junit.Test
import org.junit.Assert.*

class BishopTest {

    @Test
    fun testBishopMovesDiagonally() {
        val board = Array(8) { Array<Piece?>(8) { null } }
        val whiteBishop = Bishop(Pair(4, 4), 1)
        board[4][4] = whiteBishop

        val moves = whiteBishop.getMoves(board)

        assertTrue(moves[0][0]) // Top-left diagonal
        assertTrue(moves[1][1]) // Top-left diagonal
        assertTrue(moves[2][2]) // Top-left diagonal
        assertTrue(moves[3][3]) // Top-left diagonal
        assertTrue(moves[5][5]) // Bottom-right diagonal
        assertTrue(moves[6][6]) // Bottom-right diagonal
        assertTrue(moves[7][7]) // Bottom-right diagonal

        assertTrue(moves[1][7]) // Top-right diagonal
        assertTrue(moves[2][6]) // Top-right diagonal
        assertTrue(moves[3][5]) // Top-right diagonal
        assertTrue(moves[5][3]) // Bottom-left diagonal
        assertTrue(moves[6][2]) // Bottom-left diagonal
        assertTrue(moves[7][1]) // Bottom-left diagonal
    }

    @Test
    fun testBishopCannotMoveThroughOwnPiece() {
        val board = Array(8) { Array<Piece?>(8) { null } }
        val whiteBishop = Bishop(Pair(4, 4), 1)
        val whitePawn = Pawn(Pair(5, 5), 1)
        board[4][4] = whiteBishop
        board[5][5] = whitePawn

        val moves = whiteBishop.getMoves(board)

        assertFalse(moves[6][6]) // Cannot move through own piece diagonally
    }

    @Test
    fun testBishopCanCaptureOpponentPiece() {
        val board = Array(8) { Array<Piece?>(8) { null } }
        val whiteBishop = Bishop(Pair(4, 4), 1)
        val blackPawn = Pawn(Pair(5, 5), 0)
        board[4][4] = whiteBishop
        board[5][5] = blackPawn

        val moves = whiteBishop.getMoves(board)

        assertTrue(moves[5][5]) // Can capture opponent piece diagonally
        assertFalse(moves[6][6]) // Cannot move through opponent piece diagonally
    }

    @Test
    fun testBishopCannotMoveThroughOpponentPiece() {
        val board = Array(8) { Array<Piece?>(8) { null } }
        val whiteBishop = Bishop(Pair(4, 4), 1)
        val blackPawn = Pawn(Pair(5, 5), 0)
        board[4][4] = whiteBishop
        board[5][5] = blackPawn

        val moves = whiteBishop.getMoves(board)

        assertFalse(moves[6][6]) // Cannot move through opponent piece diagonally
    }
}
