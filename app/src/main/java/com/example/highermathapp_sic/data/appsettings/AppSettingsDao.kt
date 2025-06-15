package com.example.highermathapp_sic.data.appsettings

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AppSettingsDao {
    @Query("SELECT * FROM app_settings WHERE id = 0 LIMIT 1")
    fun getSettings(): LiveData<AppSettings>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSettings(settings: AppSettings)

    @Query("UPDATE app_settings SET mode = :mode WHERE id = 0")
    suspend fun setMode(mode: String)

    @Query("SELECT * FROM app_settings WHERE id = 0 LIMIT 1")
    suspend fun getSettingsOnce(): AppSettings?

    @Query("UPDATE app_settings SET darkTheme = :darkTheme WHERE id = 0")
    suspend fun updateDarkTheme(darkTheme: Boolean)
}