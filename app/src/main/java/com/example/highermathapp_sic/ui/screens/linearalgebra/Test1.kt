package com.example.highermathapp_sic.ui.screens.linearalgebra

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.highermathapp_sic.model.Matrix
import com.example.highermathapp_sic.ui.components.MathAppTopBar
import com.example.highermathapp_sic.ui.components.MatrixInput
import com.example.highermathapp_sic.ui.components.MatrixView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LAFirstScreen(
    navController: NavController
) {
    Scaffold(
        topBar = { MathAppTopBar("Main", navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = "LA TEST 1",
            )
            TaskSection()
        }
    }
}

@Composable
fun TaskSection() {
    Task1()
}

@Composable
fun Task1() {
    val matrixA = Matrix(2, 2)
    val matrixB = Matrix(2, 2)
    matrixA.fillRandom()
    matrixB.fillRandom()
    Row() {
        MatrixView(matrixA)
        MatrixView(matrixB)
        MatrixInput(matrixA + matrixB)
    }
}