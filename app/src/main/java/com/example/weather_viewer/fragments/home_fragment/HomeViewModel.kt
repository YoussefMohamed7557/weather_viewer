package com.example.weather_viewer.fragments.home_fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.location.Location
import android.os.Build
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.weather_viewer.R
import com.example.weather_viewer.data_classes.ResponseStates
import com.example.weather_viewer.data_source.DataSourceViewModel
import com.example.weather_viewer.data_source.local.room.entities.AllData
import com.example.weather_viewer.data_source.local.shared_preferences.SettingModel
import com.example.weather_viewer.helper.GeneralFunctions
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) :  AndroidViewModel(application) {
    private val mApplication: Application=application
    private val generalFunctions : GeneralFunctions = GeneralFunctions()
    private val dataSourceViewModel = DataSourceViewModel(mApplication)
    private val locationHanding: LocationHanding = LocationHanding(mApplication.applicationContext)
    private val _allDataList = MutableStateFlow<ResponseStates<List<AllData>>>(ResponseStates.Onloading())
    val allDataList:StateFlow<ResponseStates<List<AllData>>>
        get() = _allDataList
    @RequiresApi(Build.VERSION_CODES.O)
    fun loadImage(imageView: ImageView, string: String) {
        generalFunctions.loadImage(imageView,string)
    }

    @SuppressLint("SimpleDateFormat")
    fun formateTime(format: Int): String {
        return generalFunctions.formateTime(format)
    }
    fun getUnites(units: String): String {
        return generalFunctions.getUnites(units)
    }


    @SuppressLint("SimpleDateFormat")
    fun formateDate(format: Int): String {
        return generalFunctions.formateDate(format)
    }

    fun getRoomData(){
        viewModelScope.launch{
            dataSourceViewModel.getRoomDataBase()
                .catch {
                    _allDataList.value = ResponseStates.OnError(it.message.toString())
                }
                .collect{
                _allDataList.value = ResponseStates.OnSuccess(it)
                }
        }

    }

    fun getSetting():LiveData<SettingModel>{
        return dataSourceViewModel.getSetting()
    }

    fun gettingLocation(context: Context, activity: Activity) :LiveData<Location>{

        locationHanding.loadLocation(context,activity)
        return locationHanding.getLocatin()
    }

    fun getLocationSettnig():LiveData<LatLng>{
        return dataSourceViewModel.getLocationSetting()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun loadOnlineData(lat: String, lon: String, lang: String, units: String, context: Context)
    {
        if (generalFunctions.isOnline(context)) {
            Log.d("TAG", "loadOnlineData: ")
            dataSourceViewModel.loadOneCall(lat, lon, lang, units)
        }else{
            Toast.makeText(context,context.getString(R.string.you_areoffline), Toast.LENGTH_SHORT).show()
        }
    }


}
