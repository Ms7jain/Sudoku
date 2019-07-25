package com.example.mici.viewmodel

import android.arch.lifecycle.ViewModel
import com.example.mici.game.SudokuGame

class PlaySudokuViewModel : ViewModel() {
    val sudokuGame = SudokuGame()

}