package com.georgekyvelidis.sudokusolver.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.georgekyvelidis.sudokusolver.R

/**
 * Centralized access to the app's color palette defined in res/values/colors.xml.
 *
 */
object AppColors {
    val backgroundBlue: Color
        @Composable @ReadOnlyComposable
        get() = colorResource(R.color.background_blue)

    val cellWhite: Color
        @Composable @ReadOnlyComposable
        get() = colorResource(R.color.cell_white)

    val cellBorder: Color
        @Composable @ReadOnlyComposable
        get() = colorResource(R.color.cell_border)

    val gridFrame: Color
        @Composable @ReadOnlyComposable
        get() = colorResource(R.color.grid_frame)

    val numberUser: Color
        @Composable @ReadOnlyComposable
        get() = colorResource(R.color.number_user)

    val numberSolver: Color
        @Composable @ReadOnlyComposable
        get() = colorResource(R.color.number_solver)

    val textOnBlue: Color
        @Composable @ReadOnlyComposable
        get() = colorResource(R.color.text_on_blue)

    val textErrorOnBlue: Color
        @Composable @ReadOnlyComposable
        get() = colorResource(R.color.text_error_on_blue)

    val buttonClearContainer: Color
        @Composable @ReadOnlyComposable
        get() = colorResource(R.color.button_clear_container)

    val buttonClearContent: Color
        @Composable @ReadOnlyComposable
        get() = colorResource(R.color.button_clear_content)

    val buttonSolveContainer: Color
        @Composable @ReadOnlyComposable
        get() = colorResource(R.color.button_solve_container)

    val buttonSolveContent: Color
        @Composable @ReadOnlyComposable
        get() = colorResource(R.color.button_solve_content)

    val buttonPuzzleContainer: Color
        @Composable @ReadOnlyComposable
        get() = colorResource(R.color.button_puzzle_container)

    val buttonPuzzleContent: Color
        @Composable @ReadOnlyComposable
        get() = colorResource(R.color.button_puzzle_content)
}
