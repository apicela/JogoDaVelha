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

        val playerOneNickname : EditText = findViewById(R.id.playerOneNickname)
        val playerTwoNickname : EditText = findViewById(R.id.playerTwoNickname)
        val startButton : Button = findViewById(R.id.startButton)

        startButton.setOnClickListener{
            val jogadorUmNome : String = playerOneNickname.text.toString()
            val jogadorDoisNome : String = playerTwoNickname.text.toString()

            if(jogadorUmNome.isEmpty() || jogadorDoisNome.isEmpty()){
                Toast.makeText(this@PlayerVsPlayer,"Favor inserir o nome dos jogadores", Toast.LENGTH_SHORT).show()

            } else {
                val intent = Intent(this@PlayerVsPlayer, MainActivity::class.java)
                intent.putExtra("playerOne",jogadorUmNome)
                intent.putExtra("playerTwo",jogadorDoisNome)
                startActivity(intent)
            }

        }
    }
}