package com.georgekyvelidis.sudokusolver

class SudokuSolver {
    private val board = Array(9) { IntArray(9) }

    fun setBoard(input: List<List<Int>>) {
        for (i in 0 until 9) {
            for (j in 0 until 9) {
                board[i][j] = input[i][j]
            }
        }
    }

    fun getBoard(): List<List<Int>> {
        return board.map { it.toList() }
    }

    fun solve(): Boolean {
        for (row in 0 until 9) {
            for (col in 0 until 9) {
                if (board[row][col] == 0) {
                    for (num in 1..9) {
                        if (isValid(row, col, num)) {
                            board[row][col] = num
                            if (solve()) {
                                return true
                            }
                            board[row][col] = 0
                        }
                    }
                    return false
                }
            }
        }
        return true
    }

    private fun isValid(row: Int, col: Int, num: Int): Boolean {
        // Check row
        for (j in 0 until 9) {
            if (board[row][j] == num) return false
        }

        // Check column
        for (i in 0 until 9) {
            if (board[i][col] == num) return false
        }

        // Check 3x3 box
        val boxRow = (row / 3) * 3
        val boxCol = (col / 3) * 3
        for (i in boxRow until boxRow + 3) {
            for (j in boxCol until boxCol + 3) {
                if (board[i][j] == num) return false
            }
        }

        return true
    }

    fun isValidPuzzle(): Boolean {
        for (i in 0 until 9) {
            for (j in 0 until 9) {
                if (board[i][j] != 0) {
                    val num = board[i][j]
                    board[i][j] = 0
                    if (!isValid(i, j, num)) {
                        board[i][j] = num
                        return false
                    }
                    board[i][j] = num
                }
            }
        }
        return true
    }
}