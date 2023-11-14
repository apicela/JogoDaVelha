package com.apicela.jogodavelha

import android.os.Build.VERSION_CODES.O
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDateTime


class MainActivity : AppCompatActivity() {
    private lateinit var gridLayout: GridLayout
    private var playerTurn: Int = 1
    private var selectedBoxs: Int = 1
    private lateinit var playerOneNickname: TextView
    private lateinit var playerTwoNickname: TextView
    private lateinit var winCondition: List<List<Int>>
    private lateinit var playerOneContent: LinearLayout
    private lateinit var playerTwoContent: LinearLayout
    private lateinit var buttons: Array<Array<Button?>>
    private val historyClass = History.getInstance()
    private var tableSize: Int = 3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)


        // pegando as views
        playerOneNickname = findViewById(R.id.playerOneNickname)
        playerTwoNickname = findViewById(R.id.playerTwoNickname)
        playerOneContent = findViewById(R.id.playerOneContent)
        playerTwoContent = findViewById(R.id.playerTwoContent)
        gridLayout = findViewById(R.id.gridContainer)

        // buttons
        val voltarButton: Button = findViewById(R.id.button_voltar)
        voltarButton.setOnClickListener {
            finish()
        }
        val restartGame: Button = findViewById(R.id.restart_game)
        restartGame.setOnClickListener {
            println("restar pressed player turn ${playerTurn}")
        }


        // pegando o tamanho da tabela e configurando no gradlyout
        tableSize = intent.getIntExtra("tableSize", 3)
        gridLayout.rowCount = tableSize
        gridLayout.columnCount = tableSize

        // definindo tamanho da tela
        val widthPhone = (resources.displayMetrics.widthPixels) - 100

        //startando algumas variaveis para o funcionamento
        buttons = Array(tableSize) { linha -> Array(tableSize){ coluna -> null} }
        winCondition = generateWinConditionList(tableSize)


        for (linha in 0 until tableSize) {
            for (coluna in 0 until tableSize) {
                buttons[linha][coluna] = Button(this)

                // para uma melhor leitura
                val button = buttons[linha][coluna]

                button?.setBackgroundResource(R.drawable.button_table_grid)

                val params = GridLayout.LayoutParams()
                params.height = widthPhone / tableSize
                params.width = widthPhone / tableSize
                button?.layoutParams = params
                button?.tag = 0
                button?.setOnClickListener {
                    if (isBoxSelectable(linha,coluna)) {
                        markBox( linha, coluna)
                    }
                }
                gridLayout.addView(button)

            }

        }
    }


    private fun markBox( linha : Int, coluna : Int) {
        val button = buttons[linha][coluna]!!
        button.tag = playerTurn

        if (playerTurn == 1) {
            println("player turn 1")
            button.setBackgroundResource(R.drawable.x_letter)
            if (checkWinner()) {
                val winDialog = FinishMatchDialog(
                    this@MainActivity,
                    this@MainActivity,
                    "${playerOneNickname.text} venceu a partida!"
                )
                winDialog.setCancelable(false)
                winDialog.show()
            } else if (selectedBoxs == tableSize * tableSize) {
                addMatchToHistory(true)
                val drawDialog: FinishMatchDialog =
                    FinishMatchDialog(this@MainActivity, this@MainActivity, "Empate! ")
                drawDialog.setCancelable(false)
                drawDialog.show()
            } else {
                changePlayer(2)
                selectedBoxs++
            }
        } else{
            println("player turn 2")
            button.setBackgroundResource(R.drawable.o_letter)
            if (checkWinner()) {
                var winDialog: FinishMatchDialog = FinishMatchDialog(
                    this@MainActivity,
                    this@MainActivity,
                    "${playerTwoNickname.text} venceu a partida!"
                )
                winDialog.setCancelable(false)
                winDialog.show()
            } else if (selectedBoxs == tableSize * tableSize) {
                addMatchToHistory(true)
                val drawDialog: FinishMatchDialog =
                    FinishMatchDialog(this@MainActivity, this@MainActivity, "Empate! ")
                drawDialog.setCancelable(false)
                drawDialog.show()
            } else {
                changePlayer(1)
                selectedBoxs++
            }
        }
    }

    private fun removeMarkBox(linha : Int, coluna : Int){
        println("remove mark box called before ${buttons[linha][coluna]?.tag}")
        val button = buttons[linha][coluna]!!
        button.tag = 0
        println("remove mark box called after ${buttons[linha][coluna]?.tag}")

    }

    private fun checkWinner(): Boolean {
        for (combination in winCondition) {
            val hasWinner = combination.all { index ->
                val linha = index / tableSize
                val col = index % tableSize
                val button = buttons[linha][col]
                button?.tag == playerTurn
            }
            if (hasWinner) {
                addMatchToHistory(false)
                return true
            }
        }
        return false
    }

    private fun changePlayer(player: Int) {
        playerTurn = player
        if (playerTurn == 1) {
            playerOneContent.setBackgroundResource(R.drawable.border_style)
            playerTwoContent.setBackgroundResource(R.drawable.transparent_border_white)
        } else {
            playerTwoContent.setBackgroundResource(R.drawable.border_style)
            playerOneContent.setBackgroundResource(R.drawable.transparent_border_white)
        }
    }

    private fun isBoxSelectable(linha: Int, coluna : Int): Boolean {
        if (buttons[linha][coluna]?.tag == 0) return true
        return false
    }

    fun restartMatch() {
        changePlayer(1)
        selectedBoxs = 1;

        for (buttonLinha in buttons) {
            for(button in buttonLinha){
                button?.setBackgroundResource(R.drawable.button_table_grid)
                button?.tag = 0
            }
        }
    }




    private fun evaluateBoard(): Int {
       if(checkWinner()){
            if(playerTurn == 1){
                return 10
            }  else {return -10}
       }
        return 0
    }



    private fun addMatchToHistory(empate: Boolean) {
        val playerOne = playerOneNickname.text.toString()
        val oponent = playerTwoNickname.text.toString()
        if (empate) {
            historyClass.addToHistoryList(playerOne, oponent, LocalDateTime.now(), 0)
        } else {
            historyClass.addToHistoryList(playerOne, oponent, LocalDateTime.now(), 1)
        }
    }

    fun generateWinConditionList(tableSize: Int): List<List<Int>> {
        val winCondition = ArrayList<List<Int>>()

        for (linha in 0 until tableSize) {
            val horizontalWinCondition = ArrayList<Int>(tableSize)
            for (col in 0 until tableSize) {
                horizontalWinCondition.add(linha * tableSize + col) // horizontal values
            }
            winCondition.add(horizontalWinCondition)
        }
        for (col in 0 until tableSize) {
            val verticalWinCondition = ArrayList<Int>(tableSize)
            for (linha in 0 until tableSize) {
                verticalWinCondition.add(linha * tableSize + col)
            }
            winCondition.add(verticalWinCondition)
        }

        // Diagonal principal win condition
        val diagonalPrincipalWinCondition = ArrayList<Int>(tableSize)
        for (i in 0 until tableSize) {
            diagonalPrincipalWinCondition.add(i * (tableSize + 1))
        }
        winCondition.add(diagonalPrincipalWinCondition)

        // Diagonal secundária win condition
        val diagonalSecundariaWinCondition = ArrayList<Int>(tableSize)
        for (i in 0 until tableSize) {
            diagonalSecundariaWinCondition.add((i + 1) * (tableSize - 1))
        }
        winCondition.add(diagonalSecundariaWinCondition)
        return winCondition
    }
}