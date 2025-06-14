package com.example.highermathapp_sic.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.highermathapp_sic.model.TaskType
import com.example.highermathapp_sic.model.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavScreen(
    navController: NavController,
    viewModel: TaskViewModel
) {
    val screenTaskTypes: Map<String, List<TaskType>> = mapOf(
        "MatrixAddSub" to listOf(TaskType.ADDITION, TaskType.SUBTRACTION),
        "MatrixMul" to listOf(TaskType.MULTIPLICATION_BY_NUM, TaskType.MULTIPLICATION_BY_MATRIX_1,
            TaskType.MULTIPLICATION_BY_MATRIX_2),
        "MatrixDet" to listOf(TaskType.DET_2X2, TaskType.DET_3X3),
        "MatrixMinor" to listOf(TaskType.MINOR),
        "MatrixInverse" to listOf(TaskType.INVERSE),
        "MatrixCramerRule" to listOf(TaskType.CRAMER_RULE),
        "SequenceLimits" to listOf(TaskType.SEQUENCE_LIMIT),
        "FunctionLimits" to listOf(TaskType.FUNCTIONAL_LIMIT_1, TaskType.FUNCTIONAL_LIMIT_2),
        "FunctionAnalysis" to listOf(TaskType.FUNCTION_ANALYSIS_1, TaskType.FUNCTION_ANALYSIS_2),
        "IndefiniteIntegrals" to listOf(TaskType.INDEFINITE_INTEGRALS),
        "DefiniteIntegrals" to listOf(TaskType.DEFINITE_INTEGRALS_1, TaskType.DEFINITE_INTEGRALS_2)
    )

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

    val taskStatusList = viewModel.taskTypeStatusList.observeAsState(listOf())
    val taskStatusMap = taskStatusList.value.associate { it.taskType to it.isAnswerCorrect }
    val screenColor: Map<String, Color> = screenTaskTypes.mapValues { (_, taskTypes) ->
        val results = taskTypes.map { taskStatusMap[it] }

        when {
            results.all { it == null } -> MaterialTheme.colorScheme.background
            results.all { it == true } -> Color.Green
            results.all { it == false } -> Color.Red
            results.any { it == true} -> Color.Yellow
            else -> Color.Red
        }
    }

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
    ) { innerPadding ->
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
                        Divider(
                            color = MaterialTheme.colorScheme.onBackground,
                            thickness = 3.dp
                        )
                    }
                }

                items(itemsList) { (title, screen) ->
                    val backgroundColor = screenColor[screen] ?: MaterialTheme.colorScheme.background

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(backgroundColor)
                            .clickable { navController.navigate(screen)}
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.titleLarge,
                                color = if (backgroundColor == MaterialTheme.colorScheme.background)
                                MaterialTheme.colorScheme.onBackground else Color.Black
                            )
                        }
                        Divider(
                            color = MaterialTheme.colorScheme.onBackground,
                            thickness = 3.dp
                        )
                    }
                }
            }
        }
    }
}