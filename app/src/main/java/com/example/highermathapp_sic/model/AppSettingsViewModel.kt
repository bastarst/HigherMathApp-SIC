package com.example.highermathapp_sic.model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.highermathapp_sic.data.appsettings.AppSettings
import com.example.highermathapp_sic.data.appsettings.AppSettingsDatabase
import com.example.highermathapp_sic.data.appsettings.AppSettingsRepository
import kotlinx.coroutines.launch

class AppSettingsViewModel(application: Application) : ViewModel() {
    val settings: LiveData<AppSettings>
    val repository: AppSettingsRepository

    init {
        val appSettingsDb = AppSettingsDatabase.Companion.getInstance(application)
        val appSettingsDao = appSettingsDb.appSettingsDao()
        repository = AppSettingsRepository(appSettingsDao)
        settings = repository.settings

        viewModelScope.launch {
            val currentSettings = repository.getSettingsOnce()
            if (currentSettings == null) {
                repository.saveSettings(AppSettings(0, "online"))
            }
        }
    }

    fun setMode(mode: String) {
        viewModelScope.launch {
            repository.setMode(mode)
        }
    }

    fun updateDarkTheme(darkTheme: Boolean) {
        viewModelScope.launch {
            repository.updateDarkTheme(darkTheme)
        }
    }
}