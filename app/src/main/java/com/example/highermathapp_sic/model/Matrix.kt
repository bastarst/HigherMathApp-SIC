package com.example.highermathapp_sic.model

import kotlin.random.Random

class Matrix(private val rows: Int, private val cols: Int) {
    private val data: Array<IntArray> = Array(rows) { IntArray(cols) }

    constructor(rows: Int, cols: Int, initializer: (Int, Int) -> Int) : this(rows, cols) {
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                data[i][j] = initializer(i, j)
            }
        }
    }

    override fun toString(): String {
        return data.joinToString("\n") { row ->
            row.joinToString(" ") { "%d".format(it) }
        }
    }

    fun set(i: Int, j: Int, value: Int) {
        require(i in 0 until rows && j in 0 until cols) { "Индекс вне границ" }
        data[i][j] = value
    }

    fun get(i: Int, j: Int): Int {
        require(i in 0 until rows && j in 0 until cols) { "Индекс вне границ" }
        return data[i][j]
    }

    fun getRowCount(): Int = rows

    fun getColCount(): Int = cols

    fun equals(other: Matrix): Boolean {
        if (rows != other.rows || cols != other.cols) return false

        for (i in 0 until rows) {
            for (j in 0 until cols) {
                if (data[i][j] != other.data[i][j]) return false
            }
        }

        return true
    }

    operator fun plus(other: Matrix): Matrix {
        require(rows == other.rows && cols == other.cols) { "Размеры матриц не совпадают для сложения" }
        return Matrix(rows, cols) { i, j ->
            this.data[i][j] + other.data[i][j]
        }
    }

    operator fun minus(other: Matrix): Matrix {
        require(rows == other.rows && cols == other.cols) { "Размеры матриц не совпадают для вычитания" }
        return Matrix(rows, cols) { i, j ->
            this.data[i][j] - other.data[i][j]
        }
    }

    fun fillRandom() {
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                data[i][j] = Random.nextInt(0, 11)
            }
        }
    }
}