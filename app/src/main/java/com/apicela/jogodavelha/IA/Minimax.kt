package com.apicela.jogodavelha.IA

import com.apicela.jogodavelha.services.MatchService

class Minimax() {
    private val matchService: MatchService = MatchService()

    fun minimax(
        board: Array<IntArray>,
        depth: Int, isMax: Boolean,
        alpha: Int, beta: Int
    ): Int {
        val score: Int = matchService.checkWinner(board)
        if (score == 10) return score
        if (score == -10) return score
        if (!matchService.hasEmptyBoxs(board)) return 0

        return if (isMax) {
            var best = Int.MIN_VALUE
            var mutableAlpha = alpha // Declare a mutable variable for alpha

            for (row in 0 until matchService.tableSize) {
                for (col in 0 until matchService.tableSize) {
                    if (board[row][col] == 0) {
                        board[row][col] = 2

                        best = Math.max(
                            best, minimax(
                                board,
                                depth + 1, !isMax,
                                mutableAlpha, beta
                            )
                        )

                        board[row][col] = 0

                        mutableAlpha = Math.max(mutableAlpha, best)

                        if (beta <= mutableAlpha)
                            break
                    }
                }
            }
            best
        } else {
            var best = Int.MAX_VALUE
            var mutableBeta = beta // Declare a mutable variable for beta

            for (row in 0 until matchService.tableSize) {
                for (col in 0 until matchService.tableSize) {
                    if (board[row][col] == 0) {
                        board[row][col] = 1

                        best = Math.min(
                            best, minimax(
                                board,
                                depth + 1, !isMax,
                                alpha, mutableBeta
                            )
                        )

                        board[row][col] = 0

                        mutableBeta = Math.min(mutableBeta, best)

                        if (mutableBeta <= alpha)
                            break
                    }
                }
            }
            best
        }
    }

    fun findBestMove(board: Array<IntArray>): Pair<Int, Int> {
        var bestVal = -1000
        var bestMove = Pair(-1, -1)

        for (row in 0 until matchService.tableSize) {
            for (col in 0 until matchService.tableSize) {
                if (board[row][col] == 0) {
                    board[row][col] = 2

                    val moveVal = minimax(board, 0, false, Int.MIN_VALUE, Int.MAX_VALUE)
                    board[row][col] = 0

                    if (moveVal > bestVal) {
                        bestMove = Pair(row, col)
                        bestVal = moveVal
                    }
                }
            }
        }
        return bestMove
    }
}