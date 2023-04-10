package com.example.weather_viewer.data_source.local.shared_preferences

import androidx.lifecycle.LiveData
import com.google.android.gms.maps.model.LatLng

interface SharedPreferenceInterface {
    fun getSetting(): LiveData<SettingModel>


    fun getLocationSetting(): LiveData<LatLng>

    fun updateSetting(settingModel: SettingModel)
    fun saveLocationSetting(latLng: LatLng)
}