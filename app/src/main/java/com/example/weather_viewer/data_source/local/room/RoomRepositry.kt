package com.example.weather_viewer.data_source.local.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.weather_viewer.data_source.local.room.entities.AllData
import com.example.weather_viewer.data_source.local.room.entities.FavData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class RoomRepositry(context: Application) : AndroidViewModel(context) {
    val database : DataBaseWeather?= DataBaseWeather.getInstance(context)
    val weatherDao : WeatherDao = database!!.weatherDao()

    suspend fun saveAllData(allData : AllData){
        weatherDao.saveAllData(allData)
    }
    fun getAllData(): Flow<List<AllData>> {
        return weatherDao.getAllData()
    }
    fun deleteAll(){
        return weatherDao.deleteAll()
    }

    fun deleteOneFav(lat: String,lon: String){
        weatherDao.deleteOneFav(lat,lon)
    }

    fun getFavData(): Flow<List<FavData>>{
        return weatherDao.getFavData()
    }

    fun getFavDataNotLiveData(): Flow<List<FavData>> {
        return weatherDao.getFavDataNotLiveData()
    }

    suspend fun saveFavData(favData : FavData){
        weatherDao.saveFaveData(favData)
    }

    fun getOneFav(lat: String,lon: String):Flow<FavData>{
        return weatherDao.getOneFav(lat,lon)
    }

}