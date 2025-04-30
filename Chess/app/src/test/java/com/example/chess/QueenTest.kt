package com.example.chess

import org.junit.Test
import org.junit.Assert.*

class QueenTest {

    @Test
    fun testQueenMovesVerticallyHorizontallyAndDiagonally() {
        val board = Array(8) { Array<Piece?>(8) { null } }
        val whiteQueen = Queen(Pair(4, 4), 1)
        board[4][4] = whiteQueen

        val moves = whiteQueen.getMoves(board)

        assertTrue(moves[0][4]) // Up
        assertTrue(moves[1][4]) // Up
        assertTrue(moves[2][4]) // Up
        assertTrue(moves[3][4]) // Up
        assertTrue(moves[5][4]) // Down
        assertTrue(moves[6][4]) // Down
        assertTrue(moves[7][4]) // Down

        assertTrue(moves[4][0]) // Left
        assertTrue(moves[4][1]) // Left
        assertTrue(moves[4][2]) // Left
        assertTrue(moves[4][3]) // Left
        assertTrue(moves[4][5]) // Right
        assertTrue(moves[4][6]) // Right
        assertTrue(moves[4][7]) // Right

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
    fun testQueenCannotMoveThroughOwnPiece() {
        val board = Array(8) { Array<Piece?>(8) { null } }
        val whiteQueen = Queen(Pair(4, 4), 1)
        val whitePawn = Pawn(Pair(5, 4), 1)
        val whitePawn2 = Pawn(Pair(5, 5), 1)
        board[4][4] = whiteQueen
        board[5][4] = whitePawn
        board[5][5] = whitePawn2

        val moves = whiteQueen.getMoves(board)

        assertFalse(moves[6][4]) // Cannot move through own piece vertically
        assertFalse(moves[6][6]) // Cannot move through own piece diagonally
    }

    @Test
    fun testQueenCanCaptureOpponentPiece() {
        val board = Array(8) { Array<Piece?>(8) { null } }
        val whiteQueen = Queen(Pair(4, 4), 1)
        val blackPawn = Pawn(Pair(5, 4), 0)
        board[4][4] = whiteQueen
        board[5][4] = blackPawn

        val moves = whiteQueen.getMoves(board)

        assertTrue(moves[5][4]) // Can capture opponent piece vertically
        assertFalse(moves[6][4]) // Cannot move through opponent piece vertically
    }

    @Test
    fun testQueenCannotMoveThroughOpponentPiece() {
        val board = Array(8) { Array<Piece?>(8) { null } }
        val whiteQueen = Queen(Pair(4, 4), 1)
        val blackPawn = Pawn(Pair(5, 4), 0)
        board[4][4] = whiteQueen
        board[5][4] = blackPawn

        val moves = whiteQueen.getMoves(board)

        assertFalse(moves[6][4]) // Cannot move through opponent piece vertically
    }
}
