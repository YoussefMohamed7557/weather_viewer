package com.example.weather_viewer.fragments.favourite_fragment

import android.app.Application
import android.content.Context
import android.os.Build
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.example.weather_viewer.data_classes.ResponseStates
import com.example.weather_viewer.data_source.GeneralRepository
import com.example.weather_viewer.data_source.local.room.entities.FavData
import com.example.weather_viewer.helper.GeneralFunctions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavouriteViewModel(application: Application) : AndroidViewModel(application)  {
    private val generalRepository: GeneralRepository = GeneralRepository.getInstance(application)
    private val generalFunctions :GeneralFunctions= GeneralFunctions()
    private val intentLiveData: MutableLiveData<Int> = MutableLiveData<Int>()
    private val alertDialogLiveData: MutableLiveData<FavData> = MutableLiveData<FavData>()
    private val _allFavoriteList = MutableStateFlow<ResponseStates<List<FavData>>>(ResponseStates.Onloading())
    val allFavoriteList: StateFlow<ResponseStates<List<FavData>>>
        get() = _allFavoriteList
    @RequiresApi(Build.VERSION_CODES.O)
    fun loadImage(imageView: ImageView, string: String) {
        Glide.with(imageView)  //2
            .load("https://openweathermap.org/img/wn/$string@2x.png") //3
            .centerCrop() //4
            .into(imageView)
    }

    fun getUnites(units: String): String {
        return generalFunctions.getUnites(units)
    }

    fun intentLiveData(position : Int){
        intentLiveData.value=position
    }

    fun setAlertDialogLiveData(position : FavData){
        alertDialogLiveData.value=position
    }

    fun deleteOneFav(lat: String, lon: String)= generalRepository.deleteOneFav(lat,lon)
    @RequiresApi(Build.VERSION_CODES.M)
    fun getOnline(context: Context) : Boolean{
        return generalFunctions.isOnline(context)
    }
    fun getFavDataBase() {
        viewModelScope.launch{
            generalRepository.getFavDataBase()
                .catch{
                    _allFavoriteList.value = ResponseStates.OnError(it.message.toString())
                }
                .collect{
                    _allFavoriteList.value = ResponseStates.OnSuccess(it)
                }
        }
    }

    fun getAlertDialogLiveData():LiveData<FavData>{
        return alertDialogLiveData
    }

    fun getIntent():LiveData<Int>{
        return intentLiveData
    }


}
