package com.example.chess

import org.junit.Test
import org.junit.Assert.*

class RookTest {

    @Test
    fun testRookMovesVerticallyAndHorizontally() {
        val board = Array(8) { Array<Piece?>(8) { null } }
        val whiteRook = Rook(Pair(4, 4), 1)
        board[4][4] = whiteRook

        val moves = whiteRook.getMoves(board)

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
    }

    @Test
    fun testRookCannotMoveThroughOwnPiece() {
        val board = Array(8) { Array<Piece?>(8) { null } }
        val whiteRook = Rook(Pair(4, 4), 1)
        val whitePawn = Pawn(Pair(5, 4), 1)
        board[4][4] = whiteRook
        board[5][4] = whitePawn

        val moves = whiteRook.getMoves(board)

        assertFalse(moves[6][4]) // Cannot move through own piece
    }

    @Test
    fun testRookCanCaptureOpponentPiece() {
        val board = Array(8) { Array<Piece?>(8) { null } }
        val whiteRook = Rook(Pair(4, 4), 1)
        val blackPawn = Pawn(Pair(5, 4), 0)
        board[4][4] = whiteRook
        board[5][4] = blackPawn

        val moves = whiteRook.getMoves(board)

        assertTrue(moves[5][4]) // Can capture opponent piece
        assertFalse(moves[6][4]) // Cannot move through opponent piece
    }

    @Test
    fun testRookCannotMoveThroughOpponentPiece() {
        val board = Array(8) { Array<Piece?>(8) { null } }
        val whiteRook = Rook(Pair(4, 4), 1)
        val blackPawn = Pawn(Pair(5, 4), 0)
        board[4][4] = whiteRook
        board[5][4] = blackPawn

        val moves = whiteRook.getMoves(board)

        assertFalse(moves[6][4]) // Cannot move through opponent piece
    }
}
