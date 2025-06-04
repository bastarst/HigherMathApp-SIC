package com.example.highermathapp_sic.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LimitView(
    value: String = "x",
    approachingValue: String,
    numerator: String,
    denominator: String? = null,
    userInput:  MutableState<String>? = null,
    isAnswerCorrect: Boolean? = null,
    correctAnswer: String? = null,
    fontWeight: FontWeight = FontWeight.Bold
) {
    val color = MaterialTheme.colorScheme.onBackground
    val horizontalDividerWidth = remember { mutableIntStateOf(0) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "lim",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = fontWeight
            )

            Text(
                text = "$value → $approachingValue",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = fontWeight
            )
        }

        if (denominator == null) {
            Text(
                text = "($numerator)",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = fontWeight
            )
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                Text(
                    text = numerator,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = fontWeight,
                    modifier = Modifier.onSizeChanged {
                        horizontalDividerWidth.intValue = if (horizontalDividerWidth.intValue < it.width)
                            it.width
                        else horizontalDividerWidth.intValue
                    }
                )

                HorizontalDivider(
                    modifier = Modifier
                        .width(with(LocalDensity.current) { horizontalDividerWidth.intValue.toDp() }),
                    thickness = 2.dp,
                    color = color
                )

                Text(
                    text = denominator,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = fontWeight,
                    modifier = Modifier.onSizeChanged {
                        horizontalDividerWidth.intValue = if (horizontalDividerWidth.intValue < it.width)
                            it.width
                        else horizontalDividerWidth.intValue
                    }
                )
            }
        }

        if (userInput != null) {
            Text(
                text = "=",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = fontWeight
            )

            NumberInputField(
                userInput = userInput,
                isAnswerCorrect = isAnswerCorrect,
                correctAnswer = correctAnswer!!
            )
        }
    }
}

@Composable
fun IntegralView(
    expression: String,
    lowerBound: String? = null,
    upperBound: String? = null,
    fontWeight: FontWeight = FontWeight.Bold
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "∫",
            fontSize = 48.sp
        )

        if(lowerBound != null && upperBound != null) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = upperBound,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = fontWeight
                )

                Text(
                    text = lowerBound,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = fontWeight
                )
            }
        }

        Text(
            text = expression,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = fontWeight
        )
    }
}