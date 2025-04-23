class GameFragment : Fragment() {

    private lateinit var chessBoard: GridLayout
    private val highlights = Array(8) { Array(8) { false } }
    private var selectedPiece: Piece? = null
    private var moveCount = 0
    private var noCaptureOrPawnMoveCount = 0
    private val positionHistory = mutableListOf<String>()
    private var selectedTheme: String = "classic" // Default theme

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game, container, false)
        chessBoard = view.findViewById(R.id.chessBoard)
        initializePieces()
        updateChessBoard()
        return view
    }

    private fun initializePieces() {
        ChessBoardManager.resetBoard()
        val pieces = ChessBoardManager.getBoard()
        for (col in 0 until 8) {
            pieces[6][col] = Pawn(Pair(6, col), 1)
            pieces[1][col] = Pawn(Pair(1, col), 0)
        }
        pieces[7][4] = King(Pair(7, 4), 1)
        pieces[0][4] = King(Pair(0, 4), 0)
        pieces[7][3] = Queen(Pair(7, 3), 1)
        pieces[0][3] = Queen(Pair(0, 3), 0)

        pieces[7][1] = Knight(Pair(7, 1), 1)
        pieces[7][6] = Knight(Pair(7, 6), 1)
        pieces[0][1] = Knight(Pair(0, 1), 0)
        pieces[0][6] = Knight(Pair(0, 6), 0)

        pieces[7][2] = Bishop(Pair(7, 2), 1)
        pieces[7][5] = Bishop(Pair(7, 5), 1)
        pieces[0][2] = Bishop(Pair(0, 2), 0)
        pieces[0][5] = Bishop(Pair(0, 5), 0)

        pieces[7][0] = Rook(Pair(7, 0), 1)
        pieces[7][7] = Rook(Pair(7, 7), 1)
        pieces[0][0] = Rook(Pair(0, 0), 0)
        pieces[0][7] = Rook(Pair(0, 7), 0)
    }

    private fun updateChessBoard() {
        chessBoard.removeAllViews()
        val pieces = ChessBoardManager.getBoard()
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
                    setOnClickListener {
                        onCellClicked(row, col)
                    }
                }

                cellContainer.addView(cell)

                if (highlights[row][col]) {
                    val highlight = ImageView(requireContext()).apply {
                        val oval = ShapeDrawable(OvalShape()).apply {
                            intrinsicHeight = 100
                            intrinsicWidth = 100
                            paint.color = Color.argb(128, 128, 128, 128) // Полупрозрачный серый кружочек
                        }
                        setImageDrawable(oval)
                        layoutParams = FrameLayout.LayoutParams(
                            FrameLayout.LayoutParams.MATCH_PARENT,
                            FrameLayout.LayoutParams.MATCH_PARENT
                        )
                    }
                    cellContainer.addView(highlight)
                }

                if (selectedPiece?.position == Pair(row, col)) {
                    cellContainer.setBackgroundColor(Color.YELLOW)
                }

                chessBoard.addView(cellContainer)
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

    private fun onCellClicked(row: Int, col: Int) {
        if (selectedPiece != null) {
            if (selectedPiece?.position == Pair(row, col)) {
                clearHighlights()
                selectedPiece = null
            } else if (highlights[row][col]) {
                movePiece(row, col)
                clearHighlights()
                selectedPiece = null
                checkGameStatus()
            }
        } else if (ChessBoardManager.getBoard()[row][col] != null && (moveCount + 1) % 2 == ChessBoardManager.getBoard()[row][col]?.color) {
            selectedPiece = ChessBoardManager.getBoard()[row][col]
            highlightMoves(row, col)
        }
        updateChessBoard()
    }

    private fun highlightMoves(row: Int, col: Int) {
        val moves = selectedPiece?.getMoves(ChessBoardManager.getBoard()) ?: Array(8) { Array(8) { false } }
        for (r in 0 until 8) {
            for (c in 0 until 8) {
                highlights[r][c] = moves[r][c]
            }
        }
    }

    private fun clearHighlights() {
        for (r in 0 until 8) {
            for (c in 0 until 8) {
                highlights[r][c] = false
            }
        }
    }

    private fun movePiece(newRow: Int, newCol: Int) {
        if (selectedPiece is Pawn || ChessBoardManager.getBoard()[newRow][newCol] != null) {
            noCaptureOrPawnMoveCount = 0
        } else {
            noCaptureOrPawnMoveCount++
        }
        selectedPiece?.moveTo(Pair(newRow, newCol))
        moveCount++
        positionHistory.add(getCurrentPositionString())
    }

    private fun getCurrentPositionString(): String {
        val sb = StringBuilder()
        val pieces = ChessBoardManager.getBoard()
        for (row in 0 until 8) {
            for (col in 0 until 8) {
                sb.append(pieces[row][col]?.toString() ?: ".")
            }
        }
        return sb.toString()
    }

    private fun checkGameStatus() {
        val pieces = ChessBoardManager.getBoard()
        if (pieces.none { it.any { piece -> piece is King && piece.color == 0 } }) {
            showGameOverDialog("White wins!")
            return
        }
        if (pieces.none { it.any { piece -> piece is King && piece.color == 1 } }) {
            showGameOverDialog("Black wins!")
            return
        }

        if (noMovesLeft()) {
            showGameOverDialog("Draw! No moves left.")
            return
        }
        if (positionHistory.groupBy { it }.any { it.value.size >= 3 }) {
            showGameOverDialog("Draw! Position repeated three times.")
            return
        }
        if (noCaptureOrPawnMoveCount >= 75) {
            showGameOverDialog("Draw! 75 moves without capture or pawn move.")
            return
        }
        if (pieces.all { it.all { piece -> piece == null || piece is King } }) {
            showGameOverDialog("Draw! Only kings left on the board.")
            return
        }
    }

    private fun noMovesLeft(): Boolean {
        return false
    }

    private fun showGameOverDialog(message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Game Over")
            .setMessage(message)
            .setPositiveButton("Restart") { _, _ ->
                resetGame()
            }
            .setCancelable(false)
            .show()
    }

    private fun resetGame() {
        initializePieces()
        moveCount = 0
        noCaptureOrPawnMoveCount = 0
        positionHistory.clear()
        updateChessBoard()
    }

    fun setTheme(theme: String) {
        selectedTheme = theme
        updateChessBoard()
    }
}
