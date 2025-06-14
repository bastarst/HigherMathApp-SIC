package com.example.highermathapp_sic

import com.example.highermathapp_sic.model.Matrix
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlin.test.Test
import kotlin.test.assertEquals

class MatrixTest {
    @Test
    fun testMatrixInitEmpty() {
        val matrix = Matrix(2, 2)
        for(i in 0..1) {
            for(j in 0..1) {
                assertEquals(0, matrix.get(i, j))
            }
        }
    }

    @Test
    fun testMatrixInitializationWithLambda() {
        val matrix = Matrix(2, 2) { i, j -> i + j }
        assertEquals(0, matrix.get(0, 0))
        assertEquals(1, matrix.get(0, 1))
        assertEquals(1, matrix.get(1, 0))
        assertEquals(2, matrix.get(1, 1))
    }

    @Test
    fun testMatrixAddition() {
        val a = Matrix(2, 2) { _, _ -> 1 }
        val b = Matrix(2, 2) { _, _ -> 2 }
        val result = a + b
        assertTrue(result.equals(Matrix(2, 2) { _, _ -> 3 }))
    }

    @Test
    fun testMatrixSubtraction() {
        val a = Matrix(2, 2) { _, _ -> 5 }
        val b = Matrix(2, 2) { _, _ -> 3 }
        val result = a - b
        assertTrue(result.equals(Matrix(2, 2) { _, _ -> 2 }))
    }

    @Test
    fun testMatrixScalarMultiplication() {
        val matrix = Matrix(2, 2) { i, j -> i + j }
        val result = matrix * 2
        assertEquals(0, result.get(0, 0))
        assertEquals(2, result.get(0, 1))
        assertEquals(2, result.get(1, 0))
        assertEquals(4, result.get(1, 1))
    }

    @Test
    fun testMatrixMultiplication() {
        val a = Matrix(2, 3, listOf(1, 2, 3, 4, 5, 6))
        val b = Matrix(3, 2, listOf(7, 8, 9, 10, 11, 12))
        val result = a * b
        val expected = Matrix(2, 2, listOf(58, 64, 139, 154))
        assertTrue(result.equals(expected))
    }

    @Test
    fun testDeterminant2x2() {
        val matrix = Matrix(2, 2, listOf(1, 2, 3, 4))
        assertEquals(-2, matrix.determinant())
    }

    @Test
    fun testDeterminant3x3() {
        val matrix = Matrix(3, 3, listOf(6, 1, 1, 4, -2, 5, 2, 8, 7))
        assertEquals(-306, matrix.determinant())
    }

    @Test
    fun testMinor2x2() {
        val matrix = Matrix(2, 2, listOf(1, 2, 3, 4))
        assertEquals(4, matrix.minor(0, 0))
        assertEquals(3, matrix.minor(0, 1))
    }

    @Test
    fun testTranspose() {
        val matrix = Matrix(2, 3, listOf(1, 2, 3, 4, 5, 6))
        val expected = Matrix(3, 2, listOf(1, 4, 2, 5, 3, 6))
        assertTrue(matrix.transpose().equals(expected))
    }

    @Test
    fun testFlatSerializationAndDeserialization() {
        val matrix = Matrix(2, 2, listOf(1, 2, 3, 4))
        val serialized = matrix.toFlatString()
        val deserialized = Matrix(serialized)
        assertTrue(matrix.equals(deserialized))
    }

    @Test
    fun testInverseFromMinors2x2() {
        val matrix = Matrix(2, 2, listOf(-3, -4, 5, 2))
        val inverseMinorMatrix = matrix.inverseFromMinors()
        val expected = Matrix(2, 2, listOf(2, 4, -5, -3))
        assertTrue(inverseMinorMatrix.equals(expected))
    }

    @Test
    fun testAllMinors2x2() {
        val matrix = Matrix(2, 2, listOf(1, 2, 3, 4))
        val minors = matrix.allMinors()
        val expected = listOf(4, -3, -2, 1)
        assertEquals(expected, minors)
    }

    @Test
    fun testRandomizedMatrix() {
        val matrix = Matrix(3, 3, true)
        assertEquals(3, matrix.getRowCount())
        assertEquals(3, matrix.getColCount())
    }

    @Test
    fun testMatrixEquality() {
        val a = Matrix(2, 2, listOf(1, 2, 3, 4))
        val b = Matrix(2, 2, listOf(1, 2, 3, 4))
        val c = Matrix(2, 2, listOf(4, 3, 2, 1))
        assertTrue(a.equals(b))
        assertFalse(a.equals(c))
    }
}