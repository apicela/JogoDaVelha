package com.apicela.jogodavelha

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class FinishMatchDialog(context: Context, mainActivity: MainActivity, private val message : String) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.finish_match_layout)

        val messageText : TextView = findViewById(R.id.messageText)
        val restartButton : Button = findViewById(R.id.reiniciarJogo)

        messageText.setText(message)

    }
    }