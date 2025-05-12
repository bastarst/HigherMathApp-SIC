package com.example.highermathapp_sic.model

object TaskContentConverter {
    private const val SEPARATOR = "|"

    fun encode(matrixA: Matrix, matrixB: Matrix): String {
        return matrixA.toFlatString() + SEPARATOR + matrixB.toFlatString()
    }

    fun decode(taskContent: String): Pair<Matrix, Matrix> {
        val parts = taskContent.split(SEPARATOR)
        if (parts.size != 2) throw IllegalArgumentException("Некорректный формат taskContent")
        val matrixA = Matrix(parts[0])
        val matrixB = Matrix(parts[1])
        return Pair(matrixA, matrixB)
    }
}