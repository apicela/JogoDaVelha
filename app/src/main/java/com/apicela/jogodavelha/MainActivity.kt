package com.apicela.jogodavelha

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.apicela.jogodavelha.IA.Minimax
import com.apicela.jogodavelha.services.MatchService


class MainActivity() : AppCompatActivity() {
    lateinit var minimax: Minimax
    lateinit var matchService: MatchService

    private lateinit var gridLayout: GridLayout
    private var selectedBoxs: Int = 1

    private lateinit var playerOneContent: LinearLayout
    private lateinit var playerTwoContent: LinearLayout
    private lateinit var buttons: Array<Array<Button?>>
    var againstBot = false


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
            restartMatch()
        }
        val buttonHome: Button = findViewById(R.id.button_home)
        buttonHome.setOnClickListener {
            val intent = Intent(this@MainActivity, HomePage::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish()
        }



        // pegando o tamanho da tabela e configurando no gradlyout
        val playerOneName = intent.getStringExtra("playerOne")
        val playerTwoName = intent.getStringExtra("playerTwo")
        playerOneNickname.setText(playerOneName)
        playerTwoNickname.setText(playerTwoName)
        tableSize = intent.getIntExtra("tableSize", 3)
        againstBot = intent.getBooleanExtra("againstBot", true)
        gridLayout.rowCount = tableSize
        gridLayout.columnCount = tableSize

        // definindo tamanho da tela
        val widthPhone = (resources.displayMetrics.widthPixels) - 100

        //startando algumas variaveis para o funcionamento
        buttons = Array(tableSize) { linha -> Array(tableSize) { coluna -> null } }
        minimax = Minimax()
        matchService = MatchService()


        for (row in 0 until tableSize) {
            for (col in 0 until tableSize) {
                buttons[row][col] = Button(this)

                // para uma melhor leitura
                val button = buttons[row][col]

                button?.setBackgroundResource(R.drawable.button_table_grid)

                val params = GridLayout.LayoutParams()
                params.height = widthPhone / tableSize
                params.width = widthPhone / tableSize
                button?.layoutParams = params
                button?.tag = 0
                button?.setOnClickListener {
                    if (isBoxSelectable(row, col)) {
                        markBox(row, col, playerTurn, buttons)
                    }
                }
                gridLayout.addView(button)
            }
        }
    }


    private fun markBox(linha: Int, coluna: Int, value: Int, buttons: Array<Array<Button?>>) {
        val button = buttons[linha][coluna]!!
        button.tag = value
        if (value == 1) {
            button.setBackgroundResource(R.drawable.x_letter)
        } else if (value == 2) {
            button.setBackgroundResource(R.drawable.o_letter)
        } else {
            button.setBackgroundResource(0)
        }
        checkGame(againstBot)
    }

    private fun checkGame(againstBot: Boolean) {
        var board = buttonToIntArray(buttons)
        if (playerTurn == 1) {
            if (matchService.checkHasWinner(board)) {
                val winDialog = FinishMatchDialog(
                    this@MainActivity,
                    this@MainActivity,
                    "${playerOneNickname.text} venceu a partida!"
                )
                winDialog.setCancelable(false)
                winDialog.show()
            } else if (selectedBoxs == tableSize * tableSize) {
                matchService.addMatchToHistory(true)
                val drawDialog: FinishMatchDialog =
                    FinishMatchDialog(this@MainActivity, this@MainActivity, "Empate! ")
                drawDialog.setCancelable(false)
                drawDialog.show()
            } else {
                changePlayer()
                selectedBoxs++
                if (againstBot) {
                    var bestMove = minimax.findBestMove(board)
                    markBox(bestMove.first, bestMove.second, playerTurn, buttons)
                }
            }
        } else {
            if (matchService.checkHasWinner(board)) {
                var winDialog: FinishMatchDialog = FinishMatchDialog(
                    this@MainActivity,
                    this@MainActivity,
                    "${playerTwoNickname.text} venceu a partida!"
                )
                winDialog.setCancelable(false)
                winDialog.show()
            } else if (selectedBoxs == tableSize * tableSize) {
                matchService.addMatchToHistory(true)
                val drawDialog: FinishMatchDialog =
                    FinishMatchDialog(this@MainActivity, this@MainActivity, "Empate! ")
                drawDialog.setCancelable(false)
                drawDialog.show()
            } else {
                changePlayer()
                selectedBoxs++
            }
        }
    }


    private fun changePlayer() {
        if (playerTurn == 1) {
            playerTurn = 2
            playerTwoContent.setBackgroundResource(R.drawable.border_style)
            playerOneContent.setBackgroundResource(R.drawable.transparent_border_white)
        } else {
            playerTurn = 1
            playerOneContent.setBackgroundResource(R.drawable.border_style)
            playerTwoContent.setBackgroundResource(R.drawable.transparent_border_white)
        }
    }

    private fun isBoxSelectable(linha: Int, coluna: Int): Boolean {
        if (buttons[linha][coluna]?.tag == 0) return true
        return false
    }

    fun restartMatch() {
        playerTurn = 2
        changePlayer()
        selectedBoxs = 1
        for (buttonLinha in buttons) {
            for (button in buttonLinha) {
                button?.setBackgroundResource(R.drawable.button_table_grid)
                button?.tag = 0
            }
        }
    }

    fun buttonToIntArray(buttons: Array<Array<Button?>>): Array<IntArray> {
        var board = Array(tableSize) { IntArray(tableSize) { 0 } }
        for (row in 0 until tableSize) {
            for (col in 0 until tableSize) {
                board[row][col] = buttons[row][col]?.tag as Int
            }
        }
        return board
    }


    companion object {
        var tableSize: Int = 3
        var playerTurn: Int = 1
        lateinit var playerOneNickname: TextView
        lateinit var playerTwoNickname: TextView
    }
}