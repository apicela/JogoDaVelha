package com.apicela.jogodavelha

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.apicela.jogodavelha.adapters.HistoryAdapter
import com.apicela.jogodavelha.databinding.ActivityHistoryBinding
import com.apicela.jogodavelha.models.MatchGame
import java.time.LocalDateTime

class History(
) : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var adapter: HistoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRecycler()
    }

    private fun initRecycler() {
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val recyclerView = binding.recyclerViewHistory
        adapter = HistoryAdapter()
        adapter.setList(history)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }


    fun addToHistoryList(playerOne: String, oponent: String, date: LocalDateTime, resultado: Int) {
        val match = MatchGame(playerOne, oponent, date, resultado)
        history.add(match)
    }


    companion object {
        @Volatile
        private var instance: History? = null
        val history: MutableList<MatchGame> by lazy { mutableListOf<MatchGame>() }

        fun getInstance(): History {
            return instance ?: synchronized(this) {
                instance ?: History().also { instance = it }
            }
        }
    }
}

