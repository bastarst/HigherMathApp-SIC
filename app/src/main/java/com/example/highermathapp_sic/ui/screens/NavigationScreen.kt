package com.example.highermathapp_sic.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
            "1. Матрицы. Сложение и вычитание" to "MatrixAddSub",
            "2. Матрицы. Умножение" to "MatrixMul",
            "3. Матрицы. Определитель" to "MatrixDet",
            "4. Матрицы. Минор" to "MatrixMinor",
            "5. Обратная матрица" to "MatrixInverse",
            "6. СЛАУ. Метод Крамера" to "MatrixCramerRule"
        ),
        "Математический анализ" to listOf(
            "1. Предел последовательности" to "SequenceLimits",
            "2. Предел функции" to "FunctionLimits",
            "3. Исследование функции" to "FunctionAnalysis",
            "4. Неопределённый интеграл" to "IndefiniteIntegrals",
            "5. Определённый интеграл" to "DefiniteIntegrals"
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
            modifier = Modifier.padding(innerPadding)
        ) {
            screenGroups.forEach { (sectionTitle, itemsList) ->
                if(sectionTitle != "Главный экран") {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = sectionTitle,
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                        Divider(color = MaterialTheme.colorScheme.onBackground)
                    }
                }

                items(itemsList) { (title, screen) ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { navController.navigate(screen)}
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                        Divider(color = MaterialTheme.colorScheme.onBackground)
                    }
                }
            }
        }
    }
}