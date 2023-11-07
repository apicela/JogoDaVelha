package com.apicela.jogodavelha

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.apicela.jogodavelha.ui.theme.JogoDaVelhaTheme

    class MainActivity : AppCompatActivity() {

        val winCondition = listOf(
            listOf(0, 1, 2),
            listOf(3, 4, 5),
            listOf(6, 7, 8),
            listOf(0, 3, 6),
            listOf(1, 4, 7),
            listOf(2, 5, 8),
            listOf(0, 4, 8),
            listOf(2, 4, 6)
        )

        private val boxPositions = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
        private var playerTurn : Int = 1;

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.main_activity)

            val playerOneNickname : TextView = findViewById(R.id.playerOneNickname)
            val playerTwoNickname : TextView = findViewById(R.id.playerTwoNickname)

            val playerOneContent : LinearLayout = findViewById(R.id.playerOneContent)
            val playerTwoContent : LinearLayout = findViewById(R.id.playerTwoContent)

            val boxIds = arrayOf(
                R.id.box1, R.id.box2, R.id.box3, R.id.box4,
                R.id.box5, R.id.box6, R.id.box7, R.id.box8,
                R.id.box9
            )

            for ((index, boxId) in boxIds.withIndex()) {
                val box: ImageView = findViewById(boxId)
                box.setOnClickListener {
                    if(isBoxSelectable(index)){
                        ...
                    }
                }
            }
            val playerOneName = intent.getStringExtra("jogadorUm")
            val playerTwoName = intent.getStringExtra("jogadorDois")

        }

        private fun markBox(image : ImageView, selectedBoxPosition : Int){
            boxPositions[selectedBoxPosition] = playerTurn;

            if(playerTurn == 1 ){
                image.setImageResource(R.drawable.X_value)

            } else{
                image.setImageResource(R.drawable.O_value)
            }
        }

        private fun checkWinner() : Boolean{
            for(combination in winCondition){
                val hasWinner = combination.all { boxPositions[it] == playerTurn }
                if(hasWinner){
                    return true
                }
            }
            return false
        }

        private fun changePlayer(player : Int){
            playerTurn = player;
        }

        private fun isBoxSelectable(boxPosition: Int): Boolean {
            if(boxPositions[boxPosition] == 0 ) return true
            return false
        }


    }