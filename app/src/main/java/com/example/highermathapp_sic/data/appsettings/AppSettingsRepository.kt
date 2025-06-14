package com.example.highermathapp_sic.data.appsettings

import androidx.lifecycle.LiveData

class AppSettingsRepository(private val appSettingsDao: AppSettingsDao) {
    val settings: LiveData<AppSettings> = appSettingsDao.getSettings()

    suspend fun saveSettings(settings: AppSettings) {
        appSettingsDao.insertSettings(settings)
    }

    suspend fun getSettingsOnce(): AppSettings? {
        return appSettingsDao.getSettingsOnce()
    }
}