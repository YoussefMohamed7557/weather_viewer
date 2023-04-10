package com.example.weather_viewer.activities.main_activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.weather_viewer.data_source.GeneralRepository
import com.example.weather_viewer.data_source.local.room.entities.FavData
import com.example.weather_viewer.data_source.local.shared_preferences.SettingModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val generalRepository: GeneralRepository = GeneralRepository.getInstance(application)
    fun getFavDataAsList(): Flow<List<FavData>> {
        return generalRepository.getFavDataAsList()
    }

    fun saveFav(lat: String,lon: String,lang: String,units :String){
        viewModelScope.launch(Dispatchers.IO){
            generalRepository.saveFave(lat,lon,lang,units)
        }
    }
    fun getSettnig(): LiveData<SettingModel> {
        return generalRepository.getSetting()
    }
}