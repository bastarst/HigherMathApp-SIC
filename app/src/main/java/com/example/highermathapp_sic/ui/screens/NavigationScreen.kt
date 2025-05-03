package com.example.highermathapp_sic.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavScreen(
    navController: NavController
) {
    val pages = listOf<String>(
        "MainScreen",
        "LAFirstScreen",
        "LASecondScreen",
        "CFirstScreen",
        "CSecondScreen",
        "DEFirstScreen",
        "DESecondScreen"
    )

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Выберите страницу") })
        }
    ) {
        innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(pages) { item ->
                Surface (
                    modifier = Modifier
                        .clickable {
                            navController.navigate(item)
                        }
                ) {
                    Text(text = item)
                }
            }
        }
    }
}