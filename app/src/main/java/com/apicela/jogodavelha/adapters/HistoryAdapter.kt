package com.apicela.jogodavelha.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.apicela.jogodavelha.R
import com.apicela.jogodavelha.models.MatchGame

class HistoryAdapter(
) : RecyclerView.Adapter<HistoryAdapter.MyViewHolder>() {

    private var lista: List<MatchGame> = ArrayList()
    fun setList(newList: List<MatchGame>) {
        println("chamada set list")
        this.lista = newList
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val jogadorOriginal: TextView = itemView.findViewById(R.id.jogadorOriginal)
        val oponente: TextView = itemView.findViewById(R.id.oponente)
        val date: TextView = itemView.findViewById(R.id.dateMatch)
        val matchResult: ImageView = itemView.findViewById(R.id.matchResult)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.history_adapter, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val match = lista.get(position)
        holder.jogadorOriginal.text = match.playerOne
        holder.oponente.text = match.opponent
        holder.date.text = match.date

        when (match.resultado) {
            0 -> holder.matchResult.setImageResource(R.drawable.draw)
            1 -> holder.matchResult.setImageResource(R.drawable.trofeu)
            2 -> holder.matchResult.setImageResource(R.drawable.lose)
        }
    }
}