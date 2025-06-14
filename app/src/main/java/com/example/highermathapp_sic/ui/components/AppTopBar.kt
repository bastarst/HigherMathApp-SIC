package com.example.highermathapp_sic.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.MaterialTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MathAppTopBar(
    title: String,
    navController: NavController
) {
    TopAppBar(
        title = {
            Text(title)
        },
         navigationIcon = {
             IconButton(onClick = { navController.navigate("NavigationScreen") }
             ) {
                 Icon(
                     imageVector = Icons.Default.Menu,
                     contentDescription = "Menu"
                 )
             }
         },
        actions = { IconButton(onClick = { navController.navigate("SettingsScreen")}
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings",
            )
        }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}