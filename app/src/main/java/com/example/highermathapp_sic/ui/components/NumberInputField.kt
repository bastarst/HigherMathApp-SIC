package com.example.highermathapp_sic.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun NumberInputField(
    userInput: MutableState<String>,
    isAnswerCorrect: Boolean?,
    correctAnswer: String
) {
    val color = MaterialTheme.colorScheme.onBackground

    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = color,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(4.dp)
    ) {
        BasicTextField(
            value = if(isAnswerCorrect == true) correctAnswer else userInput.value,
            modifier = Modifier
                .width(32.dp),
            onValueChange = { userAnswer ->
                if (userAnswer.matches(Regex("-?\\d{0,3}"))) {
                    userInput.value = userAnswer
                }
            },
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Center,
                color = color
            ),
            singleLine = true,
            readOnly = isAnswerCorrect == true
        )
    }
}