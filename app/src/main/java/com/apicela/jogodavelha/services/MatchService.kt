package com.apicela.jogodavelha.services

import com.apicela.jogodavelha.History
import com.apicela.jogodavelha.MainActivity
import java.time.LocalDateTime


class MatchService {
    val tableSize = MainActivity.tableSize
    val winCondition = generateWinConditionList(tableSize)
    private val historyClass = History.getInstance()

    internal fun checkWinner(board: Array<IntArray>): Int {
        for (combination in winCondition) {
            if (combination.all { index ->
                    val linha = index / tableSize
                    val col = index % tableSize
                    val button = board[linha][col]
                    button == 1
                }) return -10
            else if (combination.all { index ->
                    val linha = index / tableSize
                    val col = index % tableSize
                    val button = board[linha][col]
                    button == 2
                }) return 10
        }
        return 0
    }


    fun checkHasWinner(board: Array<IntArray>): Boolean {
        for (combination in winCondition) {
            val hasWinner = combination.all { index ->
                val linha = index / tableSize
                val col = index % tableSize
                val button = board[linha][col]
                button == MainActivity.playerTurn
            }
            if (hasWinner) {
                addMatchToHistory(false)
                return true
            }
        }
        return false
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
//        println(winCondition)
        return winCondition
    }

    fun printBoard(board: Array<IntArray>) {
        for (linha in board) {
            for (col in linha) {
                print("$col ")
            }
            println()
        }
    }

    fun hasEmptyBoxs(board: Array<IntArray>): Boolean {
        for (i in 0 until tableSize) {
            for (j in 0 until tableSize) {
                if (board[i][j] == 0) return true
            }
        }
        return false
    }

    internal fun addMatchToHistory(empate: Boolean) {
        val playerOne = MainActivity.playerOneNickname.text.toString()
        val oponent = MainActivity.playerTwoNickname.text.toString()
        if (empate) {
            historyClass.addToHistoryList(playerOne, oponent, LocalDateTime.now(), 0)
        } else  {
            if(MainActivity.playerTurn == 1)
                historyClass.addToHistoryList(playerOne, oponent, LocalDateTime.now(), 1)
            else
                historyClass.addToHistoryList(playerOne, oponent, LocalDateTime.now(), 2)
        }
    }

}