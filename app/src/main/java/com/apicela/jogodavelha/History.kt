package com.apicela.jogodavelha

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.apicela.jogodavelha.adapters.HistoryAdapter
import com.apicela.jogodavelha.databinding.ActivityHistoryBinding
import com.apicela.jogodavelha.models.MatchResultado
import java.time.LocalDateTime

class History(
) : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var adapter: HistoryAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRecycler()
        val voltarButton: Button = findViewById(R.id.button_voltar)
        voltarButton.setOnClickListener {
            finish()
        }
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
        val match = MatchResultado(playerOne, oponent, date, resultado)
        history.add(match)
    }


    companion object {
        @Volatile
        private var instance: History? = null
        val history: MutableList<MatchResultado> by lazy { mutableListOf<MatchResultado>() }

        fun getInstance(): History {
            return instance ?: synchronized(this) {
                instance ?: History().also { instance = it }
            }
        }
    }
}

