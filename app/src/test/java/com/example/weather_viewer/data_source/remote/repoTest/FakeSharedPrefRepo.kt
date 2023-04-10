package com.example.weather_viewer.data_source.remote.repoTest

import androidx.lifecycle.LiveData
import com.example.weather_viewer.data_source.local.shared_preferences.SettingModel
import com.example.weather_viewer.data_source.local.shared_preferences.SharedPreferenceInterface
import com.google.android.gms.maps.model.LatLng

class FakeSharedPrefRepo:SharedPreferenceInterface {
    override fun getSetting(): LiveData<SettingModel> {
        TODO("Not yet implemented")
    }

    override fun getLocationSetting(): LiveData<LatLng> {
        TODO("Not yet implemented")
    }

    override fun updateSetting(settingModel: SettingModel) {
        TODO("Not yet implemented")
    }

    override fun saveLocationSetting(latLng: LatLng) {
        TODO("Not yet implemented")
    }
}