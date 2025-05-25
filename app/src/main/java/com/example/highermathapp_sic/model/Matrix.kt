package com.example.highermathapp_sic.model

import kotlin.collections.joinToString
import kotlin.math.pow
import kotlin.random.Random
import kotlin.ranges.until
import kotlin.text.format

class Matrix(private val rows: Int, private val cols: Int) {
    private val data: Array<IntArray> = Array(rows) { IntArray(cols) }

    constructor(rows: Int, cols: Int, initializer: (Int, Int) -> Int) : this(rows, cols) {
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                data[i][j] = initializer(i, j)
            }
        }
    }

    constructor(rows: Int, cols: Int, randomize: Boolean) : this(rows, cols) {
        if (randomize) {
            for (i in 0 until rows) {
                for (j in 0 until cols) {
                    data[i][j] = Random.nextInt(-5, 6)
                }
            }
        }
    }

    constructor(size: Int, notZeroDet: Boolean) : this(size, size) {
        do {
            for (i in 0 until rows) {
                for (j in 0 until cols) {
                    data[i][j] = Random.nextInt(-5, 6)
                }
            }
        } while (this.determinant() == 0)
    }

    constructor(serialized: String) : this(
        serialized.split(";")[0].toInt(),
        serialized.split(";")[1].toInt()
    ) {
        val parts = serialized.split(";")
        require(parts.size == 3) { "Неверный формат" }

        val values = parts[2].split(",").map { it.toInt() }
        require(values.size == rows * cols) { "Размерность не совпадает с данными" }

        for (i in 0 until rows) {
            for (j in 0 until cols) {
                data[i][j] = values[i * cols + j]
            }
        }
    }

    constructor(rows: Int, cols: Int, values: List<Int>) : this(rows, cols) {
        require(values.size == rows * cols) { "Размер данных (${values.size}) не совпадает с размерами матрицы ($rows x $cols)" }
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                data[i][j] = values[i * cols + j]
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

    operator fun times(scalar: Int): Matrix {
        return Matrix(rows, cols) { i, j -> data[i][j] * scalar }
    }

    operator fun times(other: Matrix): Matrix {
        require(cols == other.rows) { "Невозможно перемножить: ${cols} != ${other.rows}" }

        return Matrix(rows, other.cols) { i, j ->
            var sum = 0
            for (k in 0 until cols) {
                sum += this.data[i][k] * other.data[k][j]
            }
            sum
        }
    }

    fun toFlatString() : String {
        val flatData = buildString {
            for (i in 0 until rows) {
                for (j in 0 until cols) {
                    append(data[i][j])
                    if (i != rows - 1 || j != cols - 1) append(",")
                }
            }
        }
        return "$rows;$cols;$flatData"
    }

    fun determinant(): Int {
        require(rows == cols) { "Определитель можно вычислить только для квадратной матрицы" }

        return when (rows) {
            1 -> data[0][0]
            2 -> data[0][0] * data[1][1] - data[0][1] * data[1][0]
            3 -> {
                val a = data
                a[0][0] * (a[1][1] * a[2][2] - a[1][2] * a[2][1]) -
                        a[0][1] * (a[1][0] * a[2][2] - a[1][2] * a[2][0]) +
                        a[0][2] * (a[1][0] * a[2][1] - a[1][1] * a[2][0])
            }
            else -> throw UnsupportedOperationException("Определитель поддерживается только для размеров 1x1, 2x2 и 3x3")
        }
    }

    fun minor(i: Int, j: Int): Int {
        require(rows == cols) { "Миноры можно вычислять только для квадратных матриц" }
        require(rows >= 2) { "Минор не определён для матриц меньше 2x2" }
        require(i in 0 until rows && j in 0 until cols) { "Индекс вне границ" }

        val minorMatrix = Matrix(rows - 1, cols - 1) { r, c ->
            val srcRow = if (r >= i) r + 1 else r
            val srcCol = if (c >= j) c + 1 else c
            data[srcRow][srcCol]
        }

        return minorMatrix.determinant()
    }

    fun allMinors(): List<Int> {
        require(rows == cols) { "Миноры определены только для квадратной матрицы" }
        require(rows >= 2) { "Миноры не определены для матриц меньше 2x2" }

        val minors = mutableListOf<Int>()

        for (i in 0 until rows) {
            for (j in 0 until cols) {
                minors.add(minor(i, j) * (-1.0).pow(i + j + 2).toInt())
            }
        }

        return minors
    }

    fun transpose(): Matrix {
        return Matrix(cols, rows) { i, j -> data[j][i]}
    }

    fun inverseFromMinors(): Matrix {
        require(rows == cols) { "Матрица должна быть квадратной" }
        require(rows >= 2 && rows <= 3) { "Поддерживаются размеры 2x2 и 3x3" }

        val result = Matrix(rows, cols) { i, j ->
            val sign = if ((i + j) % 2 == 0) 1 else -1
            sign * minor(i, j)
        }

        return result.transpose()
    }
}