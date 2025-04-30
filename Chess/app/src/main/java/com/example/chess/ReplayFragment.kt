package com.example.chess

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.launch

class ReplayFragment : Fragment() {

    private lateinit var replayChessBoard: GridLayout
    private lateinit var btnBackward: Button
    private lateinit var btnForward: Button
    private lateinit var db: AppDatabase
    private var currentPositionIndex = 0
    private lateinit var positionHistory: List<String>
    private var gameId: Int = -1
    private var selectedTheme: String = "classic"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            gameId = it.getInt("gameId")
        }

        val sharedPreferences = requireContext().getSharedPreferences("ChessGamePrefs", Context.MODE_PRIVATE)
        selectedTheme = sharedPreferences.getString("theme", "classic") ?: "classic"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_replay, container, false)
        replayChessBoard = view.findViewById(R.id.replayChessBoard)
        btnBackward = view.findViewById(R.id.btnBackward)
        btnForward = view.findViewById(R.id.btnForward)

        // Initialize the database
        db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "chess-database"
        ).build()

        // Load the position history from the database
        loadPositionHistory()

        btnBackward.setOnClickListener {
            if (currentPositionIndex > 0) {
                currentPositionIndex--
                updateChessBoard()
            }
        }

        btnForward.setOnClickListener {
            if (currentPositionIndex < positionHistory.size - 1) {
                currentPositionIndex++
                updateChessBoard()
            }
        }

        val sharedPreferences = requireContext().getSharedPreferences("ChessGamePrefs", Context.MODE_PRIVATE)
        selectedTheme = sharedPreferences.getString("theme", "classic") ?: "classic"

        return view
    }

    private fun loadPositionHistory() {
        lifecycleScope.launch {
            val game = db.completedGameDao().getGameById(gameId)
            game?.let {
                positionHistory = it.positionHistory.split(",")
                updateChessBoard()
            }
        }
    }

    private fun updateChessBoard() {
        replayChessBoard.removeAllViews()
        val currentPosition = positionHistory[currentPositionIndex]
        val pieces = Array(8) { Array(8) {  null as Piece? } }

        for (row in 0 until 8) {
            for (col in 0 until 8) {
                val pieceStr = currentPosition[(row * 8 + col) * 2].toString() + currentPosition[(row * 8 + col) * 2 + 1].toString()
                pieces[row][col] = when (pieceStr) {
                    "wp" -> Pawn(Pair(row, col), 1)
                    "bp" -> Pawn(Pair(row, col), 0)
                    "wr" -> Rook(Pair(row, col), 1)
                    "br" -> Rook(Pair(row, col), 0)
                    "wn" -> Knight(Pair(row, col), 1)
                    "bn" -> Knight(Pair(row, col), 0)
                    "wb" -> Bishop(Pair(row, col), 1)
                    "bb" -> Bishop(Pair(row, col), 0)
                    "wq" -> Queen(Pair(row, col), 1)
                    "bq" -> Queen(Pair(row, col), 0)
                    "wk" -> King(Pair(row, col), 1)
                    "bk" -> King(Pair(row, col), 0)
                    else -> null
                }
            }
        }

        for (row in 0 until 8) {
            for (col in 0 until 8) {
                val cellContainer = FrameLayout(requireContext()).apply {
                    layoutParams = GridLayout.LayoutParams().apply {
                        rowSpec = GridLayout.spec(row)
                        columnSpec = GridLayout.spec(col)
                        width = 100
                        height = 100
                    }
                    setBackgroundColor(getCellColor(row, col))
                }

                val cell = ImageView(requireContext()).apply {
                    setImageResource(pieces[row][col]?.imageRes ?: 0)
                    layoutParams = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT
                    )
                }

                cellContainer.addView(cell)
                replayChessBoard.addView(cellContainer)
            }
        }
    }

    private fun getCellColor(row: Int, col: Int): Int {
        return if ((row + col) % 2 == 0) {
            getColor(requireContext(), getPrimaryColorRes())
        } else {
            getColor(requireContext(), getSecondaryColorRes())
        }
    }

    private fun getPrimaryColorRes(): Int {
        return when (selectedTheme) {
            "light" -> R.color.light_primary
            "dark" -> R.color.dark_primary
            else -> R.color.classic_primary
        }
    }

    private fun getSecondaryColorRes(): Int {
        return when (selectedTheme) {
            "light" -> R.color.light_secondary
            "dark" -> R.color.dark_secondary
            else -> R.color.classic_secondary
        }
    }

    companion object {
        fun newInstance(gameId: Int): ReplayFragment {
            val fragment = ReplayFragment()
            val args = Bundle()
            args.putInt("gameId", gameId)
            fragment.arguments = args
            return fragment
        }
    }
}
