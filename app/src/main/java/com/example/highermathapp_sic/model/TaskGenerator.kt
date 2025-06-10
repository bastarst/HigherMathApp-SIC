package com.example.highermathapp_sic.model

import kotlin.random.Random

object TaskGenerator {
    fun generateLinearAlgebraTask(taskType: TaskType): String {
        return when (taskType) {
            TaskType.ADDITION -> createTwoRandomMatrix(2, 2, 2, 2)
            TaskType.SUBTRACTION -> createTwoRandomMatrix(2, 2, 2, 2)
            TaskType.MULTIPLICATION_BY_NUM -> createRandomNumAndMatrix()
            TaskType.MULTIPLICATION_BY_MATRIX_1 -> createTwoRandomMatrix(1, 2, 2, 1)
            TaskType.MULTIPLICATION_BY_MATRIX_2 -> createTwoRandomMatrix(2, 1, 1, 2)
            TaskType.DET_2X2 -> createRandomMatrix(2, 2)
            TaskType.DET_3X3 -> createRandomMatrix(3, 3)
            TaskType.MINOR -> createRandomMinor()
            TaskType.INVERSE -> createRandomMatrixNonZeroDet()
            TaskType.CRAMER_RULE -> createCramerRuleTask()
            else -> ""
        }
    }

    private fun createRandomMatrix(r: Int, c: Int): String {
        return TaskContentConverter.encode(Matrix(r, c, randomize = true))
    }

    private fun createRandomMatrixNonZeroDet(): String {
        return TaskContentConverter.encode(Matrix(2, notZeroDet = true))
    }

    private fun createTwoRandomMatrix(r1: Int, c1: Int, r2: Int, c2: Int): String {
        val matrixA = Matrix(r1, c1, randomize = true)
        val matrixB = Matrix(r2, c2, randomize = true)

        return TaskContentConverter.encode(matrixA, matrixB)
    }

    private fun createRandomNumAndMatrix(): String {
        val num = Random.nextInt(0, 11)
        val matrix = Matrix(2, 2, randomize = true)

        return TaskContentConverter.encode(num, matrix)
    }

    private fun createRandomMinor(): String {
        val minor = Pair<Int, Int> (Random.nextInt(0, 3), Random.nextInt(0, 3))
        val matrix = Matrix(3, 3, randomize = true)

        return TaskContentConverter.encode(minor, matrix)
    }

    private fun createCramerRuleTask(): String {
        var list: List<Int>

        do {
            list = List(6) { Random.nextInt(1, 11) }
            val matrix = Matrix(2, 2, listOf<Int>(
                list[0], list[1],
                list[3], list[4]
            ))
        } while (matrix.determinant() == 0)

        return list.joinToString(",")
    }

    fun generateCalculusTask(taskType: TaskType): String {
        return when(taskType) {
            TaskType.SEQUENCE_LIMIT -> createSortedUniqueIntList(1)
            TaskType.FUNCTIONAL_LIMIT_1 -> createRandomIntListString(1)
            TaskType.FUNCTIONAL_LIMIT_2 -> createRandomIntListString(2)
            TaskType.FUNCTION_ANALYSIS_1 -> createSortedUniqueIntList(2, positive = true)
            TaskType.FUNCTION_ANALYSIS_2 -> createSortedUniqueIntList(2, positive = true)
            TaskType.INDEFINITE_INTEGRALS -> createRandomIntListString(1, positive = true)
            TaskType.DEFINITE_INTEGRALS_1 -> createSortedUniqueIntList(2)
            TaskType.DEFINITE_INTEGRALS_2 -> createSortedUniqueIntList(2)
            else -> ""
        }
    }

    private fun createRandomIntListString(size: Int, positive: Boolean = false): String {
        val list = List(size) { if(positive) Random.nextInt(1, 6) else Random.nextInt(-5, 6)}
        return list.joinToString(",")
    }

    private fun createSortedUniqueIntList(size: Int, positive: Boolean = false): String {
        val range = if (positive) 1..10 else 0..10
        return range.shuffled().take(size).sorted().joinToString(",")
    }
}