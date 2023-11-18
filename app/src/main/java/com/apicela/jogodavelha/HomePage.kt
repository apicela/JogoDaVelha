package com.apicela.jogodavelha

import android.content.Intent
import android.os.Bundle
import android.widget.Button
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

        buttonPvP.setOnClickListener{
            buttonPvP.setBackgroundResource(R.drawable.button_selected)
            againstBot = false
            buttonPvB.setBackgroundResource(0)
        }

        buttonPvB.setOnClickListener{
            buttonPvB.setBackgroundResource(R.drawable.button_selected)
            againstBot = true
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
}