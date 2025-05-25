package com.example.highermathapp_sic.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun IsAnswerCorrect(
    isCorrect: Boolean?
) {
    isCorrect?.let {
        Text(
            text = if (it) "✅ Верно" else "❌ Неверно"
        )
    }
}