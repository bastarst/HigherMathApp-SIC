package com.example.highermathapp_sic.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun BaseScreenLayout(
    navController: NavController,
    title: String,
    onPrevious: String = "",
    onNext: String = "",
    content: @Composable ColumnScope.() -> Unit
) {
    Scaffold(
        topBar = { MathAppTopBar(title, navController) },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(2.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            content()
            NavigationButtons(
                navController,
                onPrevious,
                onNext
            )
        }
    }
}