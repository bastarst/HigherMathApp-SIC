package com.example.highermathapp_sic.data.appsettings

import androidx.lifecycle.LiveData

class AppSettingsRepository(private val appSettingsDao: AppSettingsDao) {
    val settings: LiveData<AppSettings> = appSettingsDao.getSettings()

    suspend fun saveSettings(settings: AppSettings) {
        appSettingsDao.insertSettings(settings)
    }

    suspend fun setMode(mode: String) {
        appSettingsDao.setMode(mode)
    }

    suspend fun getSettingsOnce(): AppSettings? {
        return appSettingsDao.getSettingsOnce()
    }

    suspend fun updateDarkTheme(darkTheme: Boolean) {
        appSettingsDao.updateDarkTheme(darkTheme)
    }
}