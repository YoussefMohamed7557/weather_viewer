package com.example.weather_viewer.activities.main_activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.weather_viewer.data_source.DataSourceViewModel
import com.example.weather_viewer.data_source.local.room.entities.FavData
import com.example.weather_viewer.data_source.local.shared_preferences.SettingModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val dataSourceViewModel: DataSourceViewModel = DataSourceViewModel(application)
    fun getFavDataNotLiveData(): Flow<List<FavData>> {
        return dataSourceViewModel.getFavDataNotLiveData()
    }

    fun saveFav(lat: String,lon: String,lang: String,units :String){
        viewModelScope.launch(Dispatchers.IO){
            dataSourceViewModel.saveFave(lat,lon,lang,units)
        }
    }
    fun getSettnig(): LiveData<SettingModel> {
        return dataSourceViewModel.getSetting()
    }
}