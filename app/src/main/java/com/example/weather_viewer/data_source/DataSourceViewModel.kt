package com.example.weather_viewer.data_source

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.weather_viewer.data_source.local.room.RoomRepositry
import com.example.weather_viewer.data_source.local.room.entities.AllData
import com.example.weather_viewer.data_source.local.shared_preferences.SettingModel
import com.example.weather_viewer.data_source.local.shared_preferences.SharedPrefrencesReopsitory
import com.example.weather_viewer.data_source.remote.ApiClient
import com.example.weather_viewer.data_source.remote.Repository
import com.example.weather_viewer.main_activity.MainActivity
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataSourceViewModel(application: Application) : AndroidViewModel(application) {
    private val repositoryonLine = Repository(ApiClient.apiService)
    private val roomRepositry: RoomRepositry = RoomRepositry(application)
    private val sharedPreferencesReopsitory: SharedPrefrencesReopsitory =
        SharedPrefrencesReopsitory(application)
    private lateinit var job: Job
    fun getRoomDataBase(): LiveData<List<AllData>> {
        return roomRepositry.getAllData()
    }

    fun getSetting(): LiveData<SettingModel> {
        return sharedPreferencesReopsitory.getSetting()
    }

    fun getLocationSetting(): LiveData<LatLng> {
        return sharedPreferencesReopsitory.getLocationSetting()
    }

    fun loadOneCall(lat: String,lon: String,lang: String,units :String) {
        if (!MainActivity.readFromDatabase) {
            val data = repositoryonLine.getOneCall(
                lat,
                lon,
                lang,
                "cc578004936ddce46e2c61bb7a0b729f",
                "minutely",
                units
            )
            //
            data.enqueue(object : Callback<AllData?> {
                override fun onResponse(call: Call<AllData?>, response: Response<AllData?>) {
                    Log.d("tag", response.body().toString())

                    job = CoroutineScope(Dispatchers.IO).launch {
                        roomRepositry.deleteAll()
                        if (response.body() != null)
                            roomRepositry.saveAllData(response.body()!!)
                    }
                }

                override fun onFailure(call: Call<AllData?>, t: Throwable) {
                    Log.d("tag", t.message.toString())
                    t.printStackTrace()

                }
            })
        }


    }

    fun setSetting(setttingModel:SettingModel) {
        sharedPreferencesReopsitory.updateSetting(setttingModel)
    }
    fun saveLocationSetting(latLng: LatLng)=sharedPreferencesReopsitory.saveLocationSetting(latLng)
}