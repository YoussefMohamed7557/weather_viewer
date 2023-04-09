package com.example.weather_viewer.activities.favourite_details_activity

import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weather_viewer.data_source.DataSourceViewModel
import com.example.weather_viewer.data_source.local.room.entities.FavData
import com.example.weather_viewer.helper.GeneralFunctions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val mApplication: Application =application
    private val dataSourceViewModel: DataSourceViewModel = DataSourceViewModel(mApplication)
    private val generalFunctions :GeneralFunctions= GeneralFunctions()
    private val _oneFav = MutableLiveData<FavData>()
    val oneFav: LiveData<FavData>
        get() = _oneFav


    fun getOneFav(lat: String,lon: String){
        viewModelScope.launch{
            dataSourceViewModel.getOneFav(lat,lon)
                .collect{
                    _oneFav.value = it
                }
        }
    }

    fun saveFave(lat: String,lon: String,lang: String,units :String){
        viewModelScope.launch(Dispatchers.IO){
            dataSourceViewModel.saveFave(lat,lon,lang,units)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadImage(imageView: ImageView, string: String) {
        generalFunctions.loadImage(imageView,string)
    }
    @SuppressLint("SimpleDateFormat")
    fun fermatTime(format: Int): String {
        return generalFunctions.formateTime(format)

    }
    @SuppressLint("SimpleDateFormat")
    fun formatDate(format: Int): String {
        return generalFunctions.formateDate(format)
    }
    fun getUnites(units: String): String {
        return generalFunctions.getUnites(units)
    }
}
