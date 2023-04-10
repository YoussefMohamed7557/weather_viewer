package com.example.weather_viewer.data_source.remote.repoTest

import com.example.weather_viewer.data_source.local.room.RoomRepoInterface
import com.example.weather_viewer.data_source.local.room.entities.AllData
import com.example.weather_viewer.data_source.local.room.entities.FavData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

class FakeRoomRepo(private var favoriteList: MutableList<FavData> = mutableListOf<FavData>(),
                   private var allData: MutableList<AllData> = mutableListOf<AllData>()
               ) : RoomRepoInterface{
    override fun getAllData(): Flow<List<AllData>> = flow{
        emit(allData)
    }

    override suspend fun saveAllData(allData: AllData) {
        this.allData.add(allData)
    }

    override fun deleteAll() {
         allData = mutableListOf<AllData>()
    }

    override fun getFavData(): Flow<List<FavData>> = flow {
        emit(favoriteList)
    }

    override fun getFavDataAsList(): Flow<List<FavData>> = flow {
        emit(favoriteList)
    }

    override suspend fun saveFaveData(favData: FavData) {
        favoriteList.add(favData)
    }

    override fun getOneFav(lat: String, lon: String): Flow<FavData> = flow{
        for (fav in favoriteList){
            if (fav.lat.equals(lat) && fav.lat.equals(lat)){
                emit(fav)
            }
        }
    }

    override fun deleteOneFav(lat: String, lon: String): Unit = runBlocking{
        val favItem = getOneFav(lat,lon).first()
        favoriteList.remove(favItem)
    }

    override fun deleteAllFav() {
        favoriteList = mutableListOf<FavData>()
    }

}