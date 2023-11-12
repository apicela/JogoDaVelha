package com.apicela.jogodavelha

import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDateTime
import java.util.Arrays


class MainActivity : AppCompatActivity() {
    private lateinit var gridLayout: GridLayout
    private var playerTurn: Int = 1
    private var selectedBoxs: Int = 1
    private lateinit var boxPositions: Array<Int>
    private lateinit var playerOneNickname: TextView
    private lateinit var playerTwoNickname: TextView
    private lateinit var winCondition: List<List<Int>>
    private lateinit var playerOneContent: LinearLayout
    private lateinit var playerTwoContent: LinearLayout
    private lateinit var buttons: Array<Button?>
    private val historyClass = History.getInstance()
    private var tableSize: Int = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        playerOneNickname = findViewById(R.id.playerOneNickname)
        playerTwoNickname = findViewById(R.id.playerTwoNickname)

        playerOneContent = findViewById(R.id.playerOneContent)
        playerTwoContent = findViewById(R.id.playerTwoContent)


        // Obtém o GridLayout do layout
        gridLayout = findViewById(R.id.gridContainer)
        // Define o número de linhas e colunas do GridLayout
        tableSize = intent.getIntExtra("tableSize", 3)
        gridLayout.rowCount = tableSize
        gridLayout.columnCount = tableSize
        val widthPhone = (resources.displayMetrics.widthPixels) - 100
        buttons = Array(tableSize * tableSize) { null }
        winCondition = generateWinConditionList(tableSize)
        boxPositions = Array(tableSize * tableSize) { 0 }

        println(winCondition)

        for (linha in 0 until tableSize) {
            for (coluna in 0 until tableSize) {
                val indiceAbsoluto = (linha * tableSize + coluna)
                buttons[indiceAbsoluto] = Button(this)

                // Define os parâmetros de layout para o botão
                buttons[indiceAbsoluto]?.setBackgroundResource(R.drawable.button_table_grid)

                val params = GridLayout.LayoutParams()
                params.height = widthPhone / tableSize
                params.width = widthPhone / tableSize
                buttons[indiceAbsoluto]?.layoutParams = params

                // Calcula o índice absoluto
                buttons[indiceAbsoluto]?.id = indiceAbsoluto
                // Adiciona o botão ao GridLayout usando o índice absoluto
                gridLayout.addView(buttons[indiceAbsoluto], indiceAbsoluto)
            }
        }

//        for (i in 0 until gridLayout.childCount) {
//            buttons[i] = gridLayout.getChildAt(i) as Button
//
//            buttons[i]?.setOnClickListener{
//                buttons[i]?.setBackgroundResource(R.drawable.x_letter)
//            }

        for (button in buttons) {
            button?.setOnClickListener {
                println(button.id)
                if (isBoxSelectable(button.id)) {
                    markBox(it as Button, button.id)
                }

            }
        }
    }


    private fun markBox(button: Button, selectedBoxPosition: Int) {
        boxPositions[selectedBoxPosition] = playerTurn

        if (playerTurn == 1) {
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
        } else {
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

    private fun checkWinner(): Boolean {
        for (combination in winCondition) {
            val hasWinner = combination.all { boxPositions[it] == playerTurn }
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

    private fun isBoxSelectable(boxPosition: Int): Boolean {
        if (boxPositions[boxPosition] == 0) return true
        return false
    }

    fun restartMatch() {
        Arrays.fill(boxPositions, 0)
        println("box positions " + boxPositions.toList().toString())
        playerTurn = 1;
        selectedBoxs = 1;

        for (button in buttons) {
            button?.setBackgroundResource(R.drawable.button_table_grid)
        }
    }

    private fun addMatchToHistory(empate: Boolean) {
        val playerOne = playerOneNickname.text.toString()
        val oponent = playerTwoNickname.text.toString()
        if (empate) {
            println("addMatchToHistory draw")
            historyClass.addToHistoryList(playerOne, oponent, LocalDateTime.now(), 0)
        } else {
            println("addMatchToHistory ")
            historyClass.addToHistoryList(playerOne, oponent, LocalDateTime.now(), 1)
        }
    }

    private fun generateWinConditionList(tableSize: Int): List<List<Int>> {
        val winCondition = ArrayList<List<Int>>()
        val verticalWinCondition = ArrayList<Int>(tableSize)

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
        println(winCondition)
        return winCondition
    }

}