package com.example.weather_viewer.data_source.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weather_viewer.data_source.local.room.entities.AllData

@Dao
interface WeatherDao {

    @Query("SELECT * FROM AllData")
    fun getAllData(): LiveData<List<AllData>>

    @Query("SELECT * FROM AllData")
    fun getData(): List<AllData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllData(allData: AllData)

    @Query("DELETE FROM AllData")
    fun deleteAll()
}