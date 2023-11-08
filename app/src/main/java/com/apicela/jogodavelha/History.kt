package com.apicela.jogodavelha

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.apicela.jogodavelha.adapters.HistoryAdapter
import com.apicela.jogodavelha.databinding.ActivityHistoryBinding
import java.time.LocalDateTime

class History : AppCompatActivity() {

    private lateinit var binding : ActivityHistoryBinding
    private val history: MutableList<MatchGame> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_history)
        initRecyclerView()
    }

    fun getHistory(): List<String> {
        return history.map { match ->
            "Player One: ${match.playerOne}\nOpponent: ${match.opponent}\nDate: ${match.date}\nPlayer One Winner: ${match.playerOneWinner}"
        }
    }

    fun addToHistoryList(playerOne : String, oponent : String, date : LocalDateTime, playerOneWinner : Boolean) {
        val match = MatchGame(playerOne, oponent, date, playerOneWinner)
        history.add(match)
        println("\n\n\n ${history.toString()}")
    }

    private fun initRecyclerView(){
        binding.recyclerViewHistory.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewHistory.setHasFixedSize(true)
        binding.recyclerViewHistory.adapter = HistoryAdapter(history)
    }

}

data class MatchGame(
    val playerOne: String,
    val opponent: String,
    val date: LocalDateTime,
    val playerOneWinner: Boolean
)
