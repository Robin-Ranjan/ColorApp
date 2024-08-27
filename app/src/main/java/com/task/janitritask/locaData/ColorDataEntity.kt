package com.task.janitritask.locaData

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "colors")
data class ColorDataEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val colorCode: String,
    val date: String,
    var synced: Boolean = false
)
