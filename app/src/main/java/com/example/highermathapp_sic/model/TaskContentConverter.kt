package com.example.highermathapp_sic.model

object TaskContentConverter {
    private const val SEPARATOR = "|"

    fun encode(matrix: Matrix): String {
        return matrix.toFlatString()
    }

    fun encode(matrixA: Matrix, matrixB: Matrix): String {
        return matrixA.toFlatString() + SEPARATOR + matrixB.toFlatString()
    }

    fun decodePairMatrix(taskContent: String): Pair<Matrix, Matrix> {
        val parts = taskContent.split(SEPARATOR)
        return Pair(Matrix(parts[0]), Matrix(parts[1]))
    }

    fun encode(num: Int, matrix: Matrix): String {
        return num.toString() + SEPARATOR + matrix.toFlatString()
    }

    fun decodeNumAndMatrix(taskContent: String): Pair<Int, Matrix> {
        val parts = taskContent.split(SEPARATOR)
        return Pair(parts[0].toInt(), Matrix(parts[1]))
    }

    fun encode(minor: Pair<Int, Int>, matrix: Matrix): String {
        return minor.first.toString() + SEPARATOR + minor.second.toString() + SEPARATOR + matrix.toFlatString()
    }

    fun decodeMinor(taskContent: String): Pair<Pair<Int, Int>, Matrix> {
        val parts = taskContent.split(SEPARATOR)
        return Pair(Pair(parts[0].toInt(), parts[1].toInt()), Matrix(parts[2]))
    }

    fun decodeCramerRule(taskContent: String): Triple<Matrix, Matrix, Matrix> {
        val numbs = taskContent.split(",").map { it.trim().toInt() }
        val matrixA = Matrix(2, 2, listOf<Int>(
            numbs[0], numbs[1],
            numbs[3], numbs[4]
        ))
        val matrixB = Matrix(2, 2, listOf<Int>(
            numbs[2], numbs[1],
            numbs[5], numbs[4]
        ))
        val matrixC = Matrix(2, 2, listOf<Int>(
            numbs[0], numbs[2],
            numbs[3], numbs[5]
        ))
        return Triple(matrixA, matrixB, matrixC)
    }

    fun decodeList(taskContent: String): List<Int> {
        return taskContent.split(",").map { it.trim().toInt() }
    }
}