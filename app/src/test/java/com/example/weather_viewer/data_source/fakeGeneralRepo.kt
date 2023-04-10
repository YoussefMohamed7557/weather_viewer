package com.example.weather_viewer.data_source

import androidx.lifecycle.LiveData
import com.example.weather_viewer.data_source.local.room.entities.AllData
import com.example.weather_viewer.data_source.local.room.entities.FavData
import com.example.weather_viewer.data_source.local.shared_preferences.SettingModel
import com.example.weather_viewer.data_source.remote.RemoteRepoInterface
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

class fakeGeneralRepo(private var favoriteList: MutableList<FavData> = mutableListOf<FavData>(),
                      private var allData: MutableList<AllData> = mutableListOf<AllData>()
): GeneralRepositoryInterface {

    override fun getRoomDataBase(): Flow<List<AllData>> = flow{
        emit(allData)
    }

    override fun deleteOneFav(lat: String, lon: String): Unit = runBlocking {
        val favItem = getOneFav(lat,lon).first()
        favoriteList.remove(favItem)
    }

    override fun getFavDataBase(): Flow<List<FavData>> = flow {
        emit(favoriteList)
    }

    override fun getFavDataAsList(): Flow<List<FavData>> = flow {
        emit(favoriteList)
    }

    override suspend fun saveFave(lat: String, lon: String, lang: String, units: String) {
        TODO("Not yet implemented")
    }

    override fun getOneFav(lat: String, lon: String): Flow<FavData> = flow{
        for (fav in favoriteList){
            if (fav.lat.equals(lat) && fav.lat.equals(lat)){
                emit(fav)
            }
        }
    }
}

