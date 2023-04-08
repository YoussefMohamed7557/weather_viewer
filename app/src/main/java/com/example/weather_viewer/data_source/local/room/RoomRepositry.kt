package com.example.weather_viewer.data_source.local.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.weather_viewer.data_source.local.room.entities.AllData
import com.example.weather_viewer.data_source.local.room.entities.FavData

class RoomRepositry(context: Application) : AndroidViewModel(context) {
    val database : DataBaseWeather?= DataBaseWeather.getInstance(context)
    val weatherDao : WeatherDao = database!!.weatherDao()

    suspend fun saveAllData(allData : AllData){
        weatherDao.saveAllData(allData)
    }
    fun getAllData(): LiveData<List<AllData>> {
        return weatherDao.getAllData()
    }
    fun getData(): List<AllData>{
        return weatherDao.getData()
    }
    fun deleteAll(){
        return weatherDao.deleteAll()
    }

    fun deleteOneFav(lat: String,lon: String){
        weatherDao.deleteOneFav(lat,lon)
    }

    fun getFavData(): LiveData<List<FavData>>{
        return weatherDao.getFavData()
    }

    fun getFavDataNotLiveData(): List<FavData>{
        return weatherDao.getFavDataNotLiveData()
    }

    suspend fun saveFavData(favData : FavData){
        weatherDao.saveFaveData(favData)
    }

    fun getOneFav(lat: String,lon: String):LiveData<FavData>{
        return weatherDao.getOneFav(lat,lon)
    }

}