package com.example.highermathapp_sic

import com.example.highermathapp_sic.model.Matrix
import com.example.highermathapp_sic.model.TaskContentConverter
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlin.test.Test

class TaskContentConverterTest {
    @Test
    fun testEncodeDecodeSingleMatrix() {
        val matrix = Matrix(2, 2, listOf(1, 2, 3, 4))
        val encoded = TaskContentConverter.encode(matrix)
        val decoded = Matrix(encoded)
        assertTrue(matrix.equals(decoded))
    }

    @Test
    fun testEncodeDecodeMatrixPair() {
        val matrixA = Matrix(2, 2, listOf(1, 2, 3, 4))
        val matrixB = Matrix(2, 2, listOf(5, 6, 7, 8))
        val encoded = TaskContentConverter.encode(matrixA, matrixB)
        val (decodedA, decodedB) = TaskContentConverter.decodePairMatrix(encoded)
        assertTrue(matrixA.equals(decodedA))
        assertTrue(matrixB.equals(decodedB))
    }

    @Test
    fun testEncodeDecodeNumAndMatrix() {
        val num = 42
        val matrix = Matrix(2, 2, listOf(1, 2, 3, 4))
        val encoded = TaskContentConverter.encode(num, matrix)
        val (decodedNum, decodedMatrix) = TaskContentConverter.decodeNumAndMatrix(encoded)
        assertEquals(num, decodedNum)
        assertTrue(matrix.equals(decodedMatrix))
    }

    @Test
    fun testEncodeDecodeMinor() {
        val minor = Pair(1, 1)
        val matrix = Matrix(2, 2, listOf(5, 6, 7, 8))
        val encoded = TaskContentConverter.encode(minor, matrix)
        val (decodedMinor, decodedMatrix) = TaskContentConverter.decodeMinor(encoded)
        assertEquals(minor, decodedMinor)
        assertTrue(matrix.equals(decodedMatrix))
    }

    @Test
    fun testDecodeCramerRule() {
        val input = "1,2,3,4,5,6"
        val (matrixA, matrixB, matrixC) = TaskContentConverter.decodeCramerRule(input)

        val expectedA = Matrix(2, 2, listOf(1, 2, 4, 5))
        val expectedB = Matrix(2, 2, listOf(3, 2, 6, 5))
        val expectedC = Matrix(2, 2, listOf(1, 3, 4, 6))

        assertTrue(matrixA.equals(expectedA))
        assertTrue(matrixB.equals(expectedB))
        assertTrue(matrixC.equals(expectedC))
    }

    @Test
    fun testDecodeList() {
        val input = "10, 20, 30, 40"
        val list = TaskContentConverter.decodeList(input)
        assertEquals(listOf(10, 20, 30, 40), list)
    }
}