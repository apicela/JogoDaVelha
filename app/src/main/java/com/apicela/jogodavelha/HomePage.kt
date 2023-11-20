package com.apicela.jogodavelha

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class HomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        val startGame: Button = findViewById(R.id.start_game)
        val buttonPvP: Button = findViewById(R.id.startPvP)
        val buttonPvB: Button = findViewById(R.id.startPvB)

        var againstBot = false
        val historyButton: Button = findViewById(R.id.historyButton)
        var tableSize: Int = 3
        val tableSizeButtons: Array<Button> = arrayOf(
            findViewById(R.id.button3),
            findViewById(R.id.button4),
            findViewById(R.id.button5),
            findViewById(R.id.button6),
            findViewById(R.id.button7),
            findViewById(R.id.button8),
            findViewById(R.id.button9),
            findViewById(R.id.button10),
        )

        for ((index, button) in tableSizeButtons.withIndex()) {
            button.setOnClickListener {
                tableSizeButtons.forEach { button ->
                    button.setBackgroundResource(0)
                    button.isSelected = false
                }
                button.setBackgroundResource(R.drawable.button_selected)
                button.isSelected = true;
                tableSize = index + 3
            }
        }

        buttonPvP.setOnClickListener {
            buttonPvP.setBackgroundResource(R.drawable.button_selected)
            for (button in tableSizeButtons) {
                if (!button.isEnabled) {
                    button.isEnabled = true
                }
            }

            againstBot = false
            buttonPvB.setBackgroundResource(0)
        }

        buttonPvB.setOnClickListener {
            warningOnly3x3()
            buttonPvB.setBackgroundResource(R.drawable.button_selected)
            againstBot = true
            for (button in tableSizeButtons) {
                button.isEnabled = false
                if (button.isSelected) {
                    button.setBackgroundResource(0)
                    button.isSelected = false
                }
            }
            tableSizeButtons[0].isSelected = true
            tableSizeButtons[0].setBackgroundResource(R.drawable.button_selected)
            tableSize = 3
            buttonPvP.setBackgroundResource(0)
        }


        startGame.setOnClickListener {
            val intent = Intent(this@HomePage, PlayerVsPlayer::class.java)
            intent.putExtra("tableSize", tableSize)
            intent.putExtra("againstBot", againstBot)
            startActivity(intent)
        }

        historyButton.setOnClickListener {
            val intent = Intent(this@HomePage, History::class.java)
            startActivity(intent)
        }

    }

    fun warningOnly3x3() {
        Toast.makeText(
            this@HomePage,
            "Modo vs BOT no momento disponivel apenas tabuleiro 3x3",
            Toast.LENGTH_SHORT
        ).show()
    }
}