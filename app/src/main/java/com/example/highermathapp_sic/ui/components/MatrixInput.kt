package com.example.highermathapp_sic.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.highermathapp_sic.data.TaskEntity
import com.example.highermathapp_sic.data.TaskViewModel
import com.example.highermathapp_sic.model.Matrix

@Composable
fun MatrixInput(
    matrixAnswer: Matrix,
    isAnswerCorrect: Boolean?,
    viewModel: TaskViewModel,
    taskEntity: TaskEntity
) {
    val showDialog = remember { mutableStateOf(false) }
    val createMatrix = remember { mutableStateOf(false) }
    val rows = remember { mutableIntStateOf(1) }
    val cols = remember { mutableIntStateOf(1) }
    val userMatrix = remember { mutableStateOf(Matrix(1, 1)) }
    val isCorrect = remember { mutableStateOf<Boolean>(false) }

    Column {
        Button(
            onClick = {
                showDialog.value = true
                createMatrix.value = false
            },
            enabled = isAnswerCorrect != true
        ) {
            Text(text = "Создать матрицу")
        }

        if(showDialog.value) {
            AlertDialog(
                onDismissRequest = { showDialog.value = false },
                title = { Text("Выбор размера матрицы") },
                text = {
                    MatrixSizeGrid { r, c ->
                        rows.intValue = r
                        cols.intValue = c
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showDialog.value = false
                            createMatrix.value = true
                        }
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            showDialog.value = false
                            createMatrix.value = false
                        }
                    ) {
                        Text("Отмена")
                    }
                }
            )
        }

        if (isAnswerCorrect != true && createMatrix.value && rows.intValue > 0 && cols.intValue > 0) {
            MatrixEditor(rows.intValue, cols.intValue, userMatrix, isAnswerCorrect, matrixAnswer)
        }

        if (isAnswerCorrect == true) {
            MatrixEditor(matrixAnswer.getRowCount(), matrixAnswer.getColCount(), userMatrix, isAnswerCorrect, matrixAnswer)
        }

        Column {
            Row {
                Button(
                    onClick = {
                        isCorrect.value = matrixAnswer.equals(userMatrix.value)
                        viewModel.updateAnswerCorrect(taskEntity.id, isCorrect.value)
                    }
                ) {
                    Text("Проверить")
                }

                if (isAnswerCorrect == true) {
                    IconButton(onClick = {
                        viewModel.updateTask(taskEntity)
                    }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                }
            }
        }
    }
}

@Composable
fun MatrixSizeGrid(
    sizeSelected: (Int, Int) -> Unit
) {
    var selectedRows = remember { mutableIntStateOf(1) }
    var selectedCols = remember { mutableIntStateOf(1) }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        for (row in 1..3) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                for (col in 1..3) {
                    val isSelected = row <= selectedRows.intValue && col <= selectedCols.intValue
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(
                                if (isSelected) Color.Green else Color.LightGray
                            )
                            .clickable {
                                selectedRows.intValue = row
                                selectedCols.intValue = col
                                sizeSelected(row, col)
                            }
                    )
                }
            }
        }
    }
}

@Composable
fun MatrixEditor(
    rows: Int,
    cols: Int,
    matrixState: MutableState<Matrix>,
    isAnswerCorrect: Boolean?,
    correctAnswer: Matrix
) {
    val matrix = remember {
        mutableStateListOf(
            *Array(rows) {
                mutableStateListOf(*Array(cols) { "" })
            }
        )
    }

    val textColor = MaterialTheme.colorScheme.onBackground

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        repeat(rows) { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                repeat(cols) { col ->
                    Box(
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.onBackground,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(4.dp)
                    ) {
                        BasicTextField(
                            value = if(isAnswerCorrect == true) correctAnswer.get(row, col).toString() else matrix[row][col],
                            modifier = Modifier
                                .width(32.dp),
                            onValueChange = {
                                if (it.length <= 4 && it.matches(Regex("-?\\d{0,3}"))) {
                                    matrix[row][col] = it
                                    matrixState.value = toMatrix(matrix)
                                }
                            },
                            textStyle = LocalTextStyle.current.copy(
                                textAlign = TextAlign.Center,
                                color = textColor
                            ),
                            singleLine = true,
                            readOnly = isAnswerCorrect == true
                        )
                    }
                }
            }
        }
    }
}

private fun toMatrix(data: List<List<String>>): Matrix {
    val rows = data.size
    val cols = if (rows > 0) data[0].size else 0
    return Matrix(rows, cols) { i, j ->
        data[i][j].toIntOrNull() ?: 0
    }
}