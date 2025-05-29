package com.example.highermathapp_sic.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TaskSection(
    task: String? = null,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (task != null) {
            Text(
                text = task,
                style = MaterialTheme.typography.titleLarge
            )
        }
        content()
    }
}