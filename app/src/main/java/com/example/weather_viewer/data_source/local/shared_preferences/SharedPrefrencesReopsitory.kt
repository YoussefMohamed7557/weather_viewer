package com.example.weather_viewer.data_source.local.shared_preferences

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.google.android.gms.maps.model.LatLng

class SharedPrefrencesReopsitory(context: Application) : AndroidViewModel(context) {
    private val setting : SettingSB = SettingSB(context)
    fun getSetting(): LiveData<SettingModel> {
        setting.loadSetting()
        return setting.getSetting()
    }


    fun getLocationSetting(): LiveData<LatLng> {
        setting.loadLocationSetting()
        return setting.getLocationSetting()
    }

    fun updateSetting(settingModel: SettingModel)=setting.saveSetting(settingModel)
    fun saveLocationSetting(latLng: LatLng)=setting.saveLocationSetting(latLng)

}