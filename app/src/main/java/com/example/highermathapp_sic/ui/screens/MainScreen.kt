package com.example.highermathapp_sic.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.highermathapp_sic.data.TaskGroup
import com.example.highermathapp_sic.data.TaskViewModel
import com.example.highermathapp_sic.ui.components.MathAppTopBar
import com.example.highermathapp_sic.ui.components.TheoreticalPart

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    vm: TaskViewModel = viewModel()
) {
    val stats = vm.correctStats.observeAsState(listOf())
    val total = vm.taskGroupTotalStats.observeAsState(listOf())

    if(total.value.isNotEmpty()) {
        val linearCorrect =
            stats.value.find { it.taskGroup == TaskGroup.LINEAR_ALGEBRA }?.correctCount ?: 0
        val calculusCorrect =
            stats.value.find { it.taskGroup == TaskGroup.CALCULUS }?.correctCount ?: 0
        val linearTotal =
            total.value.find { it.taskGroup == TaskGroup.LINEAR_ALGEBRA }?.totalCount ?: 0
        val calculusTotal =
            total.value.find { it.taskGroup == TaskGroup.CALCULUS }?.totalCount ?: 0

        Scaffold(
            topBar = { MathAppTopBar("Главный экран", navController) }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(2.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp, 2.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "ПРОГРЕСС:",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }

                TaskGroupProgressBar("Лин. алгебра", linearCorrect, linearTotal)

                TaskGroupProgressBar("Мат. анализ", calculusCorrect, calculusTotal)

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = { navController.navigate("MatrixAddSub") }
                    ) {
                        Text(
                            text = "Начать",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }

                TheoreticalPart(fileName = "main_screen.txt")

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 8.dp)
                    ,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            vm.clearAllTasks()
                            navController.navigate("MatrixAddSub")
                        }
                    ) {
                        Text(
                            text = "Начать сначала",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
        }
    } else {
        Scaffold(
            topBar = { MathAppTopBar("Главный экран", navController) }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(2.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Загрузка...",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@Composable
fun TaskGroupProgressBar(taskGroupName: String, correctCount: Int, totalCount: Int) {
    val progress = if (totalCount > 0) correctCount.toFloat() / totalCount else 0f

    Column(modifier = Modifier.padding(16.dp, 4.dp)) {
        Text(text = "$taskGroupName: $correctCount из $totalCount", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
        )
    }
}