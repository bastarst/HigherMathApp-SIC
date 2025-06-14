package com.example.highermathapp_sic.ui.screens.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.highermathapp_sic.model.AppSettingsViewModel
import com.example.highermathapp_sic.model.TaskViewModel
import com.example.highermathapp_sic.remote.TaskFireStoreService
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun LoginScreen(
    navController: NavController,
    taskViewModel: TaskViewModel,
    appSettingsViewModel: AppSettingsViewModel
) {
    val auth = Firebase.auth

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    val emailError = remember { mutableStateOf<String?>(null) }
    val passwordError = remember { mutableStateOf<String?>(null) }
    val loginError = remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Вход в аккаунт",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Логин") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )

        if (emailError.value != null) {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = emailError.value ?: "",
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Пароль") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        if (passwordError.value != null) {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = passwordError.value ?: "",
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches()
                val isPasswordValid = password.value.length >= 8

                if(!isEmailValid) {
                    emailError.value = "Некорректный email"
                }
                if (!isPasswordValid) {
                    passwordError.value = "Пароль должен содержать минимум 8 символов"
                }

                if (isEmailValid && isPasswordValid) {
                    auth.signInWithEmailAndPassword(email.value, password.value)
                        .addOnCompleteListener { login ->
                            if (login.isSuccessful) {
                                appSettingsViewModel.setMode("online")
                                TaskFireStoreService.downloadTasksFromFireStoreOrInit(taskViewModel)
                                navController.navigate("MainScreen")
                            } else {
                                loginError.value = "Ошибка входа"
                            }
                        }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Войти")
        }

        Spacer(modifier = Modifier.height(16.dp))

        loginError.value?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Нет аккаунта?", color = MaterialTheme.colorScheme.onBackground)

            TextButton(onClick = {
                navController.navigate("RegistrationScreen")
            }) {
                Text("Зарегистрируйтесь")
            }
        }

        TextButton(onClick = {
            appSettingsViewModel.setMode("offline")
            navController.navigate("MainScreen")
        }) {
            Text("Вы можете использовать приложение оффлайн")
        }
    }
}