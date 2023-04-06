package com.example.weather_viewer.data_source.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weather_viewer.data_source.local.room.entities.AllData
import com.example.weather_viewer.data_source.local.room.entities.FavData

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
    //////// favourite data ///////////////

    @Query("SELECT * FROM FavData ")
    fun getFavData(): LiveData<List<FavData>>


    @Query("SELECT * FROM FavData ")
    fun getFavDataNotLiveData(): List<FavData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFaveData(favData: FavData)


    @Query("SELECT * FROM FavData WHERE lat LIKE:lat AND lon LIKE:lon LIMIT 1")
    fun getOneFav(lat: String, lon: String): LiveData<FavData>

    @Query("DELETE FROM FavData WHERE lat LIKE:lat AND lon LIKE:lon")
    fun deleteOneFav(lat: String, lon: String)


    @Query("DELETE FROM FavData")
    fun deleteAllFav()
}