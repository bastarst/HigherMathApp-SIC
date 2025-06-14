package com.example.highermathapp_sic.data.appsettings

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_settings")
data class AppSettings(
    @PrimaryKey val id: Int = 0,
    val mode: String? = null
)
