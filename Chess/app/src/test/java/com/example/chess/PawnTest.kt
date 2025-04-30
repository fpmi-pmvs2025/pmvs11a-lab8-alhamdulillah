package com.example.chess

import org.junit.Test
import org.junit.Assert.*

class PawnTest {

    @Test
    fun testPawnMovesForward() {
        val board = Array(8) { Array<Piece?>(8) { null } }
        val whitePawn = Pawn(Pair(6, 3), 1)
        board[6][3] = whitePawn

        val moves = whitePawn.getMoves(board)

        assertTrue(moves[5][3])
        assertFalse(moves[3][3])
    }

    @Test
    fun testPawnCapturesDiagonally() {
        val board = Array(8) { Array<Piece?>(8) { null } }
        val blackPawn = Pawn(Pair(4, 4), 0)
        val whitePawn = Pawn(Pair(5, 5),1)
        board[4][4] = blackPawn
        board[5][5] = whitePawn

        val moves = whitePawn.getMoves(board)

        assertTrue(moves[4][4])
        assertFalse(moves[5][3])
    }

    @Test
    fun testPawnCannotMoveBackward() {
        val board = Array(8) { Array<Piece?>(8) { null } }
        val whitePawn = Pawn(Pair(4, 4), 1)
        board[4][4] = whitePawn

        val moves = whitePawn.getMoves(board)

        assertFalse(moves[5][4]) // Cannot move backward
    }

    @Test
    fun testPawnCannotCaptureStraight() {
        val board = Array(8) { Array<Piece?>(8) { null } }
        val whitePawn = Pawn(Pair(4, 4), 1)
        val blackPawn = Pawn(Pair(5, 4), 0)
        board[4][4] = whitePawn
        board[5][4] = blackPawn

        val moves = whitePawn.getMoves(board)

        assertFalse(moves[5][4])
    }
}
