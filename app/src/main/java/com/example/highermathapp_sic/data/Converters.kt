package com.example.highermathapp_sic.data

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromTaskType(value: TaskType): String = value.name

    @TypeConverter
    fun toTaskType(value: String): TaskType = TaskType.valueOf(value)

    @TypeConverter
    fun fromTaskGroup(value: TaskGroup): String = value.name

    @TypeConverter
    fun toTaskGroup(value: String): TaskGroup = TaskGroup.valueOf(value)
}