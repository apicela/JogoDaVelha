package com.apicela.jogodavelha

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class HomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        val playWithFriend : Button = findViewById(R.id.playWithFriend)
        val playAgainstIA : Button = findViewById(R.id.startButtonAgainstIA)

        playWithFriend.setOnClickListener {
            val intent = Intent(this@HomePage, PlayerVsPlayer::class.java)
            startActivity(intent)
        }


    }
}