package com.example.chess

import org.junit.Test
import org.junit.Assert.*

class KnightTest {

    @Test
    fun testKnightMovesInLShape() {
        val board = Array(8) { Array<Piece?>(8) { null } }
        val whiteKnight = Knight(Pair(4, 4), 1)
        board[4][4] = whiteKnight

        val moves = whiteKnight.getMoves(board)

        assertTrue(moves[2][3]) // L-shape move
        assertTrue(moves[2][5]) // L-shape move
        assertTrue(moves[3][2]) // L-shape move
        assertTrue(moves[3][6]) // L-shape move
        assertTrue(moves[5][2]) // L-shape move
        assertTrue(moves[5][6]) // L-shape move
        assertTrue(moves[6][3]) // L-shape move
        assertTrue(moves[6][5]) // L-shape move
    }

    @Test
    fun testKnightCannotMoveThroughOwnPiece() {
        val board = Array(8) { Array<Piece?>(8) { null } }
        val whiteKnight = Knight(Pair(4, 4), 1)
        val whitePawn = Pawn(Pair(2, 3), 1)
        board[4][4] = whiteKnight
        board[2][3] = whitePawn

        val moves = whiteKnight.getMoves(board)

        assertFalse(moves[2][3]) // Cannot move to own piece
    }

    @Test
    fun testKnightCanCaptureOpponentPiece() {
        val board = Array(8) { Array<Piece?>(8) { null } }
        val whiteKnight = Knight(Pair(4, 4), 1)
        val blackPawn = Pawn(Pair(2, 3), 0)
        board[4][4] = whiteKnight
        board[2][3] = blackPawn

        val moves = whiteKnight.getMoves(board)

        assertTrue(moves[2][3]) // Can capture opponent piece
    }
}
