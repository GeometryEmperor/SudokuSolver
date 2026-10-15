package com.georgekyvelidis.sudokusolver

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.georgekyvelidis.sudokusolver.ui.AppColors
import com.georgekyvelidis.sudokusolver.ui.SudokuSolverTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SudokuSolverTheme {
                SudokuApp()
            }
        }
    }
}

@Composable
fun SudokuApp(
    viewModel: SudokuViewModel = viewModel()
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val onCellChange: (Int, Int, Int) -> Unit = viewModel::onCellChange
    val onClear = viewModel::clear
    val onSolve = viewModel::solve

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = AppColors.backgroundBlue
    ) {
        if (isLandscape) {
            LandscapeLayout(
                board = viewModel.board,
                givenBoard = viewModel.givenBoard,
                isEditable = viewModel.isEditable,
                isSolved = viewModel.isSolved,
                errorResId = viewModel.errorResId,
                onCellChange = onCellChange,
                onClear = onClear,
                onSolve = onSolve
            )
        } else {
            PortraitLayout(
                board = viewModel.board,
                givenBoard = viewModel.givenBoard,
                isEditable = viewModel.isEditable,
                isSolved = viewModel.isSolved,
                errorResId = viewModel.errorResId,
                onCellChange = onCellChange,
                onClear = onClear,
                onSolve = onSolve
            )
        }
    }
}

@Composable
fun PortraitLayout(
    board: List<MutableList<Int>>,
    givenBoard: List<MutableList<Int>>,
    isEditable: Boolean,
    isSolved: Boolean,
    @StringRes errorResId: Int?,
    onCellChange: (Int, Int, Int) -> Unit,
    onClear: () -> Unit,
    onSolve: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.title),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = AppColors.textOnBlue,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        SudokuGrid(
            board = board,
            givenBoard = givenBoard,
            isEditable = isEditable,
            onCellChange = onCellChange
        )

        Spacer(modifier = Modifier.height(16.dp))

        SudokuControls(
            isSolved = isSolved,
            errorResId = errorResId,
            onClear = onClear,
            onSolve = onSolve
        )
    }
}

@Composable
fun LandscapeLayout(
    board: List<MutableList<Int>>,
    givenBoard: List<MutableList<Int>>,
    isEditable: Boolean,
    isSolved: Boolean,
    @StringRes errorResId: Int?,
    onCellChange: (Int, Int, Int) -> Unit,
    onClear: () -> Unit,
    onSolve: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f),
            contentAlignment = Alignment.Center
        ) {
            SudokuGrid(
                board = board,
                givenBoard = givenBoard,
                isEditable = isEditable,
                onCellChange = onCellChange
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.title),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.textOnBlue,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            SudokuControls(
                isSolved = isSolved,
                errorResId = errorResId,
                onClear = onClear,
                onSolve = onSolve
            )
        }
    }
}

@Composable
fun SudokuControls(
    isSolved: Boolean,
    @StringRes errorResId: Int?,
    onClear: () -> Unit,
    onSolve: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = onClear,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.buttonClearContainer,
                    contentColor = AppColors.buttonClearContent
                )
            ) {
                Text(stringResource(R.string.button_clear))
            }

            Button(
                onClick = onSolve,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.buttonSolveContainer,
                    contentColor = AppColors.buttonSolveContent
                )
            ) {
                Text(stringResource(R.string.button_solve))
            }
        }

        if (isSolved) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.status_solved),
                color = AppColors.textOnBlue,
                fontWeight = FontWeight.Bold
            )
        }

        if (errorResId != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(errorResId),
                color = AppColors.textErrorOnBlue,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun SudokuGrid(
    board: List<MutableList<Int>>,
    givenBoard: List<MutableList<Int>>,
    isEditable: Boolean,
    onCellChange: (Int, Int, Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .border(width = 2.dp, color = AppColors.gridFrame)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(9),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(0.dp),
            horizontalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            items(
                board.indices.flatMap { row ->
                    board[row].indices.map { col ->
                        Triple(row, col, board[row][col])
                    }
                }
            ) { (row, col, value) ->
                SudokuCell(
                    value = value,
                    row = row,
                    col = col,
                    isEditable = isEditable,
                    isGiven = givenBoard[row][col] != 0,
                    onValueChange = { newValue -> onCellChange(row, col, newValue) }
                )
            }
        }
    }
}

@Composable
fun SudokuCell(
    value: Int,
    row: Int,
    col: Int,
    isEditable: Boolean,
    isGiven: Boolean,
    onValueChange: (Int) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    val backgroundColor = AppColors.cellWhite

    val textColor = if (isGiven) AppColors.numberUser else AppColors.numberSolver

    val fontWeight = when {
        isEditable -> FontWeight.Medium
        isGiven -> FontWeight.Bold
        else -> FontWeight.Normal
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(1f)
            .background(backgroundColor)
            .border(width = 0.5.dp, color = AppColors.cellBorder)
            .then(
                if (isEditable) {
                    Modifier.clickable { showDialog = true }
                } else {
                    Modifier
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        if (value != 0) {
            Text(
                text = value.toString(),
                fontSize = 20.sp,
                fontWeight = fontWeight,
                color = textColor
            )
        }

        if (showDialog) {
            NumberPickerDialog(
                currentValue = value,
                onDismiss = { showDialog = false },
                onNumberSelected = { selectedValue ->
                    onValueChange(selectedValue)
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun NumberPickerDialog(
    currentValue: Int = 0,
    onDismiss: () -> Unit,
    onNumberSelected: (Int) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(
                    if (currentValue != 0) R.string.dialog_title_change
                    else R.string.dialog_title_select
                )
            )
        },
        text = {
            Column {
                for (i in 1..9) {
                    TextButton(
                        onClick = { onNumberSelected(i) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = i.toString(),
                            fontSize = 20.sp,
                            fontWeight = if (i == currentValue) FontWeight.Bold else FontWeight.Normal,
                            color = if (i == currentValue) MaterialTheme.colorScheme.primary else Color.Unspecified
                        )
                    }
                }
                TextButton(
                    onClick = { onNumberSelected(0) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.dialog_button_clear),
                        color = Color.Red
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.dialog_button_cancel))
            }
        }
    )
}

fun emptyBoard(): List<MutableList<Int>> {
    return List(9) { MutableList(9) { 0 } }
}
