package com.apicela.jogodavelha

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class PlayerVsPlayer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_vs_player)

        val jogadorUm : EditText = findViewById(R.id.playerOneNickname)
        val jogadorDois : EditText = findViewById(R.id.playerTwoNickname)
        val startButton : Button = findViewById(R.id.startButton)

        startButton.setOnClickListener{
            val jogadorUmNome : String = jogadorUm.text.toString()
            val jogadorDoisNome : String = jogadorDois.text.toString()

            if(jogadorUmNome.isEmpty() || jogadorDoisNome.isEmpty()){
                Toast.makeText(this@PlayerVsPlayer,"Favor inserir o nome dos jogadores", Toast.LENGTH_SHORT).show()

            } else {
                val intent = Intent(this@PlayerVsPlayer, MainActivity::class.java)
                intent.putExtra("jogadorUm",jogadorUmNome)
                intent.putExtra("jogadorDois",jogadorDoisNome)
                startActivity(intent)
            }

        }
    }
}