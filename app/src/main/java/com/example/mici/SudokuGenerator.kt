package com.example.mici

import java.util.*

class SudokuGenerator private constructor() {

    private val Available = ArrayList<ArrayList<Int>>()

    private val rand = Random()

    fun generateGrid(): Array<IntArray> {
        val Sudoku = Array(9) { IntArray(9) }

        var currentPos = 0


        while (currentPos < 81) {
            if (currentPos == 0) {
                clearGrid(Sudoku)
            }

            if (Available[currentPos].size != 0) {
                val i = rand.nextInt(Available[currentPos].size)
                val number = Available[currentPos][i]

                if (!checkConflict(Sudoku, currentPos, number)) {
                    val xPos = currentPos % 9
                    val yPos = currentPos / 9

                    Sudoku[xPos][yPos] = number

                    Available[currentPos].removeAt(i)

                    currentPos++
                } else {
                    Available[currentPos].removeAt(i)
                }

            } else {
                for (i in 1..9) {
                    Available[currentPos].add(i)
                }
                currentPos--
            }
        }


        return Sudoku
    }

    fun removeElements(Sudoku: Array<IntArray>): Array<IntArray> {
        var i = 0

        while (i < 3) {
            val x = rand.nextInt(9)
            val y = rand.nextInt(9)

            if (Sudoku[x][y] != 0) {
                Sudoku[x][y] = 0
                i++
            }
        }
        return Sudoku

    }

    private fun clearGrid(Sudoku: Array<IntArray>) {
        Available.clear()

        for (y in 0..8) {
            for (x in 0..8) {
                Sudoku[x][y] = -1
            }
        }

        for (x in 0..80) {
            Available.add(ArrayList())
            for (i in 1..9) {
                Available[x].add(i)
            }
        }
    }

    private fun checkConflict(Sudoku: Array<IntArray>, currentPos: Int, number: Int): Boolean {
        val xPos = currentPos % 9
        val yPos = currentPos / 9

        return if (checkHorizontalConflict(Sudoku, xPos, yPos, number) || checkVerticalConflict(Sudoku, xPos, yPos, number) || checkRegionConflict(Sudoku, xPos, yPos, number)) {
            true
        } else false

    }

    /**
     * Return true if there is a conflict
     * @param Sudoku
     * @param xPos
     * @param yPos
     * @param number
     * @return
     */
    private fun checkHorizontalConflict(Sudoku: Array<IntArray>, xPos: Int, yPos: Int, number: Int): Boolean {
        for (x in xPos - 1 downTo 0) {
            if (number == Sudoku[x][yPos]) {
                return true
            }
        }

        return false
    }

    private fun checkVerticalConflict(Sudoku: Array<IntArray>, xPos: Int, yPos: Int, number: Int): Boolean {
        for (y in yPos - 1 downTo 0) {
            if (number == Sudoku[xPos][y]) {
                return true
            }
        }

        return false
    }

    private fun checkRegionConflict(Sudoku: Array<IntArray>, xPos: Int, yPos: Int, number: Int): Boolean {
        val xRegion = xPos / 3
        val yRegion = yPos / 3

        for (x in xRegion * 3 until xRegion * 3 + 3) {
            for (y in yRegion * 3 until yRegion * 3 + 3) {
                if ((x != xPos || y != yPos) && number == Sudoku[x][y]) {
                    return true
                }
            }
        }

        return false
    }

    companion object {
        private var instance: SudokuGenerator? = null

        fun getInstance(): SudokuGenerator {
            if (instance == null) {
                instance = SudokuGenerator()
            }
            return instance as SudokuGenerator
        }
    }
}