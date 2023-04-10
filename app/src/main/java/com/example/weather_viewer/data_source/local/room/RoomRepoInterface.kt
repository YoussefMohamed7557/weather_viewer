package com.example.weather_viewer.data_source.local.room

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weather_viewer.data_source.local.room.entities.AllData
import com.example.weather_viewer.data_source.local.room.entities.FavData
import kotlinx.coroutines.flow.Flow

interface RoomRepoInterface {
    fun getAllData(): Flow<List<AllData>>

    suspend fun saveAllData(allData: AllData)

    fun deleteAll()
    //////// favourite data ///////////////

    fun getFavData(): Flow<List<FavData>>
    fun getFavDataAsList(): Flow<List<FavData>>
    suspend fun saveFaveData(favData: FavData)

    fun getOneFav(lat: String, lon: String): Flow<FavData>
    fun deleteOneFav(lat: String, lon: String)
    fun deleteAllFav()
}