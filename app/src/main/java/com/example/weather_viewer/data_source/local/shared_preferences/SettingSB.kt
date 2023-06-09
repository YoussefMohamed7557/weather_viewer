package com.example.weather_viewer.data_source.local.shared_preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingSB(val context: Context) {

    companion object {
        const val fileName = "Current"
    }
    private val SettingData: MutableLiveData<SettingModel> = MutableLiveData<SettingModel>()
    private val latlon: MutableLiveData<LatLng> = MutableLiveData<LatLng>()
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(fileName,
        Context.MODE_PRIVATE)


    fun saveSetting(settingModel: SettingModel){
        CoroutineScope(Dispatchers.IO).launch {
            val editor: SharedPreferences.Editor =  sharedPreferences.edit()
            editor.putString("units",settingModel.units)
            editor.putString("lang",settingModel.lang)
            editor.putString("location",settingModel.location)
            editor.apply()
            editor.commit()
        }
    }

    fun loadSetting(){
        CoroutineScope(Dispatchers.IO).launch {
            val units = sharedPreferences.getString("units","standard")
            val lang = sharedPreferences.getString("lang","en")
            val location = sharedPreferences.getString("location","gps")
            SettingData.postValue(SettingModel(units!!, lang!!, location!!))
        }
    }
    fun loadLocationSetting(){
        CoroutineScope(Dispatchers.IO).launch {
            val lat = sharedPreferences.getFloat("lat", 0.0F)
            val lon = sharedPreferences.getFloat("lon", 0.0F)
            latlon.postValue(LatLng(lat.toDouble(), lon.toDouble()))
        }
    }

    fun saveLocationSetting(latLng: LatLng){
        CoroutineScope(Dispatchers.IO).launch {
            val editor: SharedPreferences.Editor =  sharedPreferences.edit()
            editor.putFloat("lat", latLng.latitude.toFloat())
            editor.putFloat("lon", latLng.longitude.toFloat())
            editor.apply()
            editor.commit()
        }
    }
    fun getSetting(): LiveData<SettingModel> {
        return SettingData
    }
    fun getLocationSetting(): LiveData<LatLng> {
        return latlon
    }


}