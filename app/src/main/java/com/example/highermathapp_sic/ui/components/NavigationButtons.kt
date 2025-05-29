package com.example.highermathapp_sic.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun NavigationButtons(
    navController: NavController,
    onPrevious: String,
    onNext: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = { navController.navigate(onPrevious) }
        ) {
            Text("<- Назад", style = MaterialTheme.typography.titleLarge)
        }

        Button(
            onClick = { navController.navigate(onNext) }
        ) {
            Text("Вперёд ->", style = MaterialTheme.typography.titleLarge)
        }
    }
}