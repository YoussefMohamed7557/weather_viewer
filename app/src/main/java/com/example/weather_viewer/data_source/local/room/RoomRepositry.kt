package com.example.weather_viewer.data_source.local.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.weather_viewer.data_source.local.room.entities.AllData
import com.example.weather_viewer.data_source.local.room.entities.FavData
import kotlinx.coroutines.flow.Flow

class RoomRepositry(context: Application):RoomRepoInterface {
    val database : DataBaseWeather?= DataBaseWeather.getInstance(context)
    val weatherDao : WeatherDao = database!!.weatherDao()

    override suspend fun saveAllData(allData : AllData){
        weatherDao.saveAllData(allData)
    }
    override fun getAllData(): Flow<List<AllData>> {
        return weatherDao.getAllData()
    }
    override fun deleteAll(){
        return weatherDao.deleteAll()
    }

    override fun deleteOneFav(lat: String,lon: String){
        weatherDao.deleteOneFav(lat,lon)
    }

    override fun deleteAllFav() {
        weatherDao.deleteAllFav()
    }


    override fun getFavData(): Flow<List<FavData>>{
        return weatherDao.getFavData()
    }

    override fun getFavDataAsList(): Flow<List<FavData>> {
        return weatherDao.getFavDataAsList()
    }

    override suspend fun saveFaveData(favData: FavData) {
        weatherDao.saveFaveData(favData)
    }


    override fun getOneFav(lat: String,lon: String):Flow<FavData>{
        return weatherDao.getOneFav(lat,lon)
    }

}