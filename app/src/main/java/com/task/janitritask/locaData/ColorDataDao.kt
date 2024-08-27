package com.task.janitritask.locaData

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ColorDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertColor(colorData: ColorDataEntity)

    @Query("SELECT * FROM colors")
    fun getAllColors(): LiveData<List<ColorDataEntity>>

    @Update
    suspend fun updateColor(colorData: ColorDataEntity)

    @Query("SELECT * FROM colors WHERE synced = 0")
    fun getUnsyncedColors(): LiveData<List<ColorDataEntity>>

    @Query("SELECT COUNT(*) FROM colors WHERE synced = 0")
    fun getUnsyncedColorsCount(): Flow<Int>
}