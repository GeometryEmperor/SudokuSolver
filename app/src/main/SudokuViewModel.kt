package com.georgekyvelidis.sudokusolver

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SudokuViewModel : ViewModel() {

    var board by mutableStateOf(emptyBoard())
        private set

    var givenBoard by mutableStateOf(emptyBoard())
        private set

    var isSolved by mutableStateOf(false)
        private set

    var isEditable by mutableStateOf(true)
        private set

    @get:StringRes
    var errorResId by mutableStateOf<Int?>(null)
        private set

    private val solver = SudokuSolver()

    fun onCellChange(row: Int, col: Int, value: Int) {
        if (!isEditable) return

        val newBoard = board.toMutableList().map { it.toMutableList() }
        newBoard[row][col] = value
        board = newBoard

        val newGiven = givenBoard.toMutableList().map { it.toMutableList() }
        newGiven[row][col] = value
        givenBoard = newGiven

        isSolved = false
        errorResId = null
    }

    fun clear() {
        board = emptyBoard()
        givenBoard = emptyBoard()
        isSolved = false
        isEditable = true
        errorResId = null
    }

    fun solve() {
        solver.setBoard(board.map { it.toList() })

        if (!solver.isValidPuzzle()) {
            errorResId = R.string.error_invalid_puzzle
            return
        }

        if (solver.solve()) {
            // Snapshot user input before replacing with solution
            givenBoard = board.map { it.toMutableList() }.toMutableList()

            val solvedBoard = solver.getBoard()
            board = solvedBoard.map { it.toMutableList() }.toMutableList()
            isSolved = true
            isEditable = false
            errorResId = null
        } else {
            errorResId = R.string.error_no_solution
        }
    }
}