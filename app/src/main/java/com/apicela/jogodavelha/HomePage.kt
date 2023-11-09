package com.apicela.jogodavelha

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        val playWithFriend: Button = findViewById(R.id.playWithFriend)
        val playAgainstIA: Button = findViewById(R.id.startButtonAgainstIA)
        val historyButton: Button = findViewById(R.id.historyButton)

        playWithFriend.setOnClickListener {
            val intent = Intent(this@HomePage, PlayerVsPlayer::class.java)
            startActivity(intent)
        }

        historyButton.setOnClickListener {
            val intent = Intent(this@HomePage, History::class.java)
            startActivity(intent)
        }

    }
}