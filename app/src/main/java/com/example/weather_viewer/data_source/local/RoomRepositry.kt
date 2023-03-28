package com.example.weather_viewer.data_source.local

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.weather_viewer.data_source.local.entities.AllData

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

}