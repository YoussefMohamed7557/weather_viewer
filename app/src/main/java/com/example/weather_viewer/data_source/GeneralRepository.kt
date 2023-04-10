package com.example.weather_viewer.data_source

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.weather_viewer.data_source.local.room.RoomRepositry
import com.example.weather_viewer.data_source.local.room.entities.AllData
import com.example.weather_viewer.data_source.local.room.entities.FavData
import com.example.weather_viewer.data_source.local.shared_preferences.SettingModel
import com.example.weather_viewer.data_source.local.shared_preferences.SharedPrefrencesReopsitory
import com.example.weather_viewer.data_source.remote.ApiClient
import com.example.weather_viewer.data_source.remote.Repository
import com.example.weather_viewer.activities.main_activity.MainActivity
import com.example.weather_viewer.data_source.local.room.RoomRepoInterface
import com.example.weather_viewer.data_source.local.shared_preferences.SharedPreferenceInterface
import com.example.weather_viewer.data_source.remote.RemoteRepoInterface
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

class GeneralRepository(private var repositoryonLine:RemoteRepoInterface,
                        private var roomRepositry: RoomRepoInterface,
                        private var sharedPreferencesReopsitory:SharedPreferenceInterface
){
   /*
    init{
        repositoryonLine = Repository(ApiClient.apiService())
        roomRepositry= RoomRepositry(context)
        sharedPreferencesReopsitory =
        SharedPrefrencesReopsitory(context)
    }

    */
    companion object{
        @Volatile
        private var instance:GeneralRepository?=null
        fun getInstance(app: Application):GeneralRepository{
            return instance?: synchronized(this){
                val apiInterface = ApiClient.apiService()
                val remoteDataSource = Repository(apiInterface)
                val roomDataSource = RoomRepositry(app)
                val sharedPrefDataSource = SharedPrefrencesReopsitory(app)
                GeneralRepository(remoteDataSource,roomDataSource,sharedPrefDataSource)
            }
        }
    }
    private lateinit var job: Job
    private lateinit var job1: Job
    fun getRoomDataBase(): Flow<List<AllData>> {
        return roomRepositry.getAllData()
    }
    fun getSetting(): LiveData<SettingModel> {
        return sharedPreferencesReopsitory.getSetting()
    }
    fun getLocationSetting(): LiveData<LatLng> {
        return sharedPreferencesReopsitory.getLocationSetting()
    }
    suspend fun getOneCall(lat: String, lon: String, lang: String, units :String) {
        if (!MainActivity.readFromDatabase) {
            coroutineScope{
                val dataResponse = async {
                    repositoryonLine.getOneCall(
                        lat,
                        lon,
                        lang,
                        "cc578004936ddce46e2c61bb7a0b729f",
                        "minutely",
                        units
                    )
                }
                if (dataResponse.await().isSuccessful) {
                    val data = dataResponse.await().body()
                    Log.d("tag", data.toString())
                    job = CoroutineScope(Dispatchers.IO).launch {
                        roomRepositry.deleteAll()
                        if (data != null)
                            roomRepositry.saveAllData(data)
                    }
                }
                /*
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
                 */
            }
        }
    }
    fun setSetting(setttingModel: SettingModel) {
        sharedPreferencesReopsitory.updateSetting(setttingModel)
    }
    fun saveLocationSetting(latLng: LatLng)=sharedPreferencesReopsitory.saveLocationSetting(latLng)
    fun deleteOneFav(lat: String,lon: String){
        roomRepositry.deleteOneFav(lat,lon)
    }
    fun getFavDataBase() : Flow<List<FavData>>{
        return roomRepositry.getFavData()
    }
    fun getFavDataAsList(): Flow<List<FavData>> {
        return roomRepositry.getFavDataAsList()
    }

    suspend fun saveFave(lat: String, lon: String, lang: String, units :String) {
        coroutineScope {
            val dataResponse = async {
                repositoryonLine.getFavCall(lat,lon,lang,"cc578004936ddce46e2c61bb7a0b729f","minutely",units)
            }
            if (dataResponse.await().isSuccessful) {
                val data  = dataResponse.await().body()
                Log.d("tag", data.toString())
                job1= CoroutineScope(Dispatchers.IO).launch {
                    roomRepositry.saveFaveData(data!!)
                }
            }
        }
        /*
        val data =  repositoryonLine.getFavCall(lat,lon,lang,"cc578004936ddce46e2c61bb7a0b729f","minutely",units)
        data.enqueue(object : Callback<FavData?> {
            override fun onResponse(call: Call<FavData?>, response: Response<FavData?>) {
                Log.d("tag", response.body().toString())
                job1= CoroutineScope(Dispatchers.IO).launch {
                    roomRepositry.saveFavData(response.body()!!)
                }
            }

            override fun onFailure(call: Call<FavData?>, t: Throwable) {
                t.printStackTrace()
            }
        })
        */
    }

    fun getOneFav(lat: String,lon: String):Flow<FavData>{
        return roomRepositry.getOneFav(lat,lon)
    }


}