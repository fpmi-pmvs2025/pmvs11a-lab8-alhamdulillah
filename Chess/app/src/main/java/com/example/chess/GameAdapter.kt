package com.example.chess

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class GameAdapter(private val onClick: (Int) -> Unit) : ListAdapter<CompletedGame, GameAdapter.GameViewHolder>(GameDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        return GameViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = getItem(position)
        holder.bind(game)
    }

    inner class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewGameInfo: TextView = itemView.findViewById(R.id.textViewGameInfo)

        fun bind(game: CompletedGame) {
            val dateFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
            val endTime = Date(game.endTime)
            textViewGameInfo.text = "Game ended on: ${dateFormat.format(endTime)}"
            itemView.setOnClickListener {
                onClick(game.id)
            }
        }
    }
}

class GameDiffCallback : DiffUtil.ItemCallback<CompletedGame>() {
    override fun areItemsTheSame(oldItem: CompletedGame, newItem: CompletedGame): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CompletedGame, newItem: CompletedGame): Boolean {
        return oldItem == newItem
    }
}
