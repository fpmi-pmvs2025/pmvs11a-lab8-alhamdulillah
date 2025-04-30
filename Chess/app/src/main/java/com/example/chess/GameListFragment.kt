package com.example.chess

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class GameListFragment : Fragment() {

    private lateinit var recyclerViewGames: RecyclerView
    private lateinit var db: AppDatabase
    private lateinit var gameAdapter: GameAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game_list, container, false)
        recyclerViewGames = view.findViewById(R.id.recyclerViewGames)

        // Initialize the database
        db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "chess-database"
        ).build()

        // Set up the RecyclerView
        recyclerViewGames.layoutManager = LinearLayoutManager(requireContext())
        gameAdapter = GameAdapter { gameId ->
            openReplayFragment(gameId)
        }
        recyclerViewGames.adapter = gameAdapter

        // Load the games from the database
        loadGames()

        return view
    }

    private fun loadGames() {
        lifecycleScope.launch {
            val games = db.completedGameDao().getAllGames()
            gameAdapter.submitList(games)
        }
    }

    private fun openReplayFragment(gameId: Int) {
        parentFragmentManager.beginTransaction()
            .add(R.id.fragment_container, ReplayFragment.newInstance(gameId))
            .addToBackStack(null)
            .commit()
    }
}
