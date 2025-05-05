package com.example.highermathapp_sic.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavScreen(
    navController: NavController
) {
    val screenGroups: Map<String, List<Pair<String, String>>> = mapOf(
        "Главный экран" to listOf("Главный экран" to "MainScreen"),
        "Линейная алгебра" to listOf(
            "1" to "LAFirstScreen",
            "2" to "LASecondScreen"
        ),
        "Математический анализ" to listOf(
            "3" to "CFirstScreen",
            "4" to "CSecondScreen"
        ),
        "Дифференциальные уравнения" to listOf(
            "5" to "DEFirstScreen",
            "6" to "DESecondScreen"
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Выберете страницу")
                        },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) {
        innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            screenGroups.forEach { (sectionTitle, itemsList) ->
                if(sectionTitle != "Главный экран") {
                    item {
                        Text(
                            text = sectionTitle
                        )
                    }
                }

                items(itemsList) { (title, screen) ->
                    Surface(
                        modifier = Modifier
                            .clickable {
                                navController.navigate(screen)
                            }
                    ) {
                        Text(text = title)
                    }
                }
            }
        }
    }
}