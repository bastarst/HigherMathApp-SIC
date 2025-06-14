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
import androidx.compose.foundation.layout.width
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
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun RegistrationScreen(
    navController: NavController
) {
    val auth = Firebase.auth

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    val emailError = remember { mutableStateOf<String?>(null) }
    val passwordError = remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Регистрация",
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
            modifier = Modifier.fillMaxWidth()
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
                    auth.createUserWithEmailAndPassword(email.value, password.value)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                navController.navigate("LoginScreen")
                            }
                        }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Зарегистрироваться")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Уже зарегистрированы?", color = MaterialTheme.colorScheme.onBackground)

            TextButton(onClick = {
                navController.navigate("LoginScreen")
            }) {
                Text("Войдите")
            }
        }
    }
}