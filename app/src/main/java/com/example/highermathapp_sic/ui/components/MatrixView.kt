package com.example.highermathapp_sic.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.example.highermathapp_sic.model.Matrix

@Composable
fun MatrixView(
    matrix: Matrix,
    determinant: Boolean = false
) {
    val lineColor = MaterialTheme.colorScheme.onBackground

    Box(
        modifier = Modifier
            .drawBehind {
                if(!determinant) {
                    drawLine(
                        color = lineColor,
                        start = Offset(0f, 0f),
                        end = Offset(16f, 0f),
                        strokeWidth = 2.dp.toPx()
                    )

                    drawLine(
                        color = lineColor,
                        start = Offset(0f, size.height),
                        end = Offset(16f, size.height),
                        strokeWidth = 2.dp.toPx()
                    )

                    drawLine(
                        color = lineColor,
                        start = Offset(size.width, 0f),
                        end = Offset(size.width - 16f, 0f),
                        strokeWidth = 2.dp.toPx()
                    )

                    drawLine(
                        color = lineColor,
                        start = Offset(size.width, size.height),
                        end = Offset(size.width - 16f, size.height),
                        strokeWidth = 2.dp.toPx()
                    )
                }

                drawLine(
                    color = lineColor,
                    start = Offset(0f, 0f),
                    end = Offset(0f, size.height),
                    strokeWidth = 2.dp.toPx()
                )

                drawLine(
                    color = lineColor,
                    start = Offset(size.width, 0f),
                    end = Offset(size.width, size.height),
                    strokeWidth = 2.dp.toPx()
                )
            }
            .padding(4.dp)
    ) {
        Column {
            for (i in 0 until matrix.getRowCount()) {
                Row {
                    for (j in 0 until matrix.getColCount()) {
                        Text(
                            " %d ".format(matrix.get(i, j)),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
        }
    }
}