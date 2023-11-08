package com.apicela.jogodavelha.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.apicela.jogodavelha.MatchGame
import com.apicela.jogodavelha.R

class HistoryAdapter(
    private val lista : List<MatchGame>
) : RecyclerView.Adapter<HistoryAdapter.MyViewHolder>() {


    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val jogadorOriginal : TextView = itemView.findViewById(R.id.jogadorOriginal)
        val oponente : TextView = itemView.findViewById(R.id.oponente)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_history, parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val match = lista.get(position)
        val name = match.playerOne
        val oponent = match.opponent
        val date = match.date
        val winner = match.playerOneWinner

        holder.jogadorOriginal.text = name
        holder.oponente.text = oponent

    }
}