package com.example.highermathapp_sic.data.appsettings

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AppSettings::class], version = 1)
abstract class AppSettingsDatabase : RoomDatabase() {
    abstract fun appSettingsDao(): AppSettingsDao

    companion object {
        private var INSTANCE: AppSettingsDatabase? = null
        fun getInstance(context: Context): AppSettingsDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppSettingsDatabase::class.java,
                        "app_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}