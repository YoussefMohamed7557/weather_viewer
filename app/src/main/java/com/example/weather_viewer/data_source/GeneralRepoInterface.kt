package com.example.weather_viewer.data_source

import androidx.lifecycle.LiveData
import com.example.weather_viewer.data_source.local.room.entities.AllData
import com.example.weather_viewer.data_source.local.room.entities.FavData
import com.example.weather_viewer.data_source.local.shared_preferences.SettingModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

interface GeneralRepositoryInterface {
    fun getRoomDataBase(): Flow<List<AllData>>
    fun deleteOneFav(lat: String, lon: String)
    fun getFavDataBase() : Flow<List<FavData>>
    fun getFavDataAsList(): Flow<List<FavData>>

    suspend fun saveFave(lat: String, lon: String, lang: String, units : String)
    fun getOneFav(lat: String, lon: String): Flow<FavData>
}
