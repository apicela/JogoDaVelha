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
    private lateinit var boxs: Array<ImageView?>
    private var boxPositions = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
    private var playerTurn : Int = 1;
    private var selectedBoxs : Int = 1;


    private lateinit var playerOneNickname : TextView
    private lateinit var playerTwoNickname : TextView

    private lateinit var playerOneContent : LinearLayout
    private lateinit var playerTwoContent : LinearLayout

    val boxIds = arrayOf(
        R.id.box1, R.id.box2, R.id.box3, R.id.box4,
        R.id.box5, R.id.box6, R.id.box7, R.id.box8,
        R.id.box9
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        playerOneNickname  = findViewById(R.id.playerOneNickname)
        playerTwoNickname  = findViewById(R.id.playerTwoNickname)

        playerOneContent  = findViewById(R.id.playerOneContent)
        playerTwoContent = findViewById(R.id.playerTwoContent)



        boxs = Array(9){ findViewById(boxIds[it]) }

        for ((index, box) in boxs.withIndex()) {
            box?.setOnClickListener {
                if(isBoxSelectable(index)){
                    markBox(it as ImageView, index)
                }
            }
        }
        val playerOneName = intent.getStringExtra("playerOne")
        val playerTwoName = intent.getStringExtra("playerTwo")

        playerOneNickname.setText(playerOneName)
        playerTwoNickname.setText(playerTwoName)

    }

    private fun markBox(image : ImageView, selectedBoxPosition : Int){
        boxPositions[selectedBoxPosition] = playerTurn;

        if(playerTurn == 1 ){
            image.setImageResource(R.drawable.x_value)
            if(checkWinner()){
                var  winDialog : FinishMatchDialog = FinishMatchDialog(this@MainActivity,this@MainActivity, "${playerOneNickname.text} venceu a partida!")
                winDialog.setCancelable(false)
                winDialog.show()
            } else if(selectedBoxs == 9){
                var  drawDialog : FinishMatchDialog = FinishMatchDialog(this@MainActivity,this@MainActivity, "Empate! ")
                drawDialog.setCancelable(false)
                drawDialog.show()
            } else{
                changePlayer(2)
                selectedBoxs++
            }
        } else{
            image.setImageResource(R.drawable.zero_value)
            if(checkWinner()){
                var  winDialog : FinishMatchDialog = FinishMatchDialog(this@MainActivity,this@MainActivity, "${playerTwoNickname.text} venceu a partida!")
                winDialog.setCancelable(false)
                winDialog.show()
            } else if(selectedBoxs == 9){
                var  drawDialog : FinishMatchDialog = FinishMatchDialog(this@MainActivity,this@MainActivity, "Empate! ")
                drawDialog.setCancelable(false)
                drawDialog.show()
            } else{
                changePlayer(1)
                selectedBoxs++
            }
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
        if(playerTurn == 1){
            playerOneContent.setBackgroundResource(R.drawable.border_style)
            playerTwoContent.setBackgroundResource(R.drawable.fundo_transparente)
        } else{
            playerTwoContent.setBackgroundResource(R.drawable.border_style)
            playerOneContent.setBackgroundResource(R.drawable.fundo_transparente)
        }
    }

    private fun isBoxSelectable(boxPosition: Int): Boolean {
        if(boxPositions[boxPosition] == 0 ) return true
        return false
    }

    fun restartMatch(){
        boxPositions = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
        playerTurn = 1;
        selectedBoxs = 1;

        for (box in boxs){
            box?.setImageResource(R.drawable.fundo_transparente)
        }
    }

}