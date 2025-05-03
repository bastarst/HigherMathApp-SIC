package com.example.highermathapp_sic.ui.screens.differentialequations

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.highermathapp_sic.ui.components.MathAppTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DEFirstScreen(
    navController: NavController
) {
    Scaffold(
        topBar = { MathAppTopBar("Main", navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = "DE TEST 1",
            )
        }
    }
}