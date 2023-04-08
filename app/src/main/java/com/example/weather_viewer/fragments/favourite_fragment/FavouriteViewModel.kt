package com.example.weather_viewer.fragments.favourite_fragment

import android.app.Application
import android.content.Context
import android.os.Build
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.example.weather_viewer.data_source.DataSourceViewModel
import com.example.weather_viewer.data_source.local.room.entities.FavData
import com.example.weather_viewer.helper.GeneralFunctions

class FavouriteViewModel(application: Application) : AndroidViewModel(application)  {
    private val dataSourceViewModel: DataSourceViewModel = DataSourceViewModel(application)
    private val generalFunctions :GeneralFunctions= GeneralFunctions()
    private val intentLiveData: MutableLiveData<Int> = MutableLiveData<Int>()
    private val alertDialogLiveData: MutableLiveData<FavData> = MutableLiveData<FavData>()
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

    fun deleteOneFav(lat: String, lon: String)= dataSourceViewModel.deleteOneFav(lat,lon)
    @RequiresApi(Build.VERSION_CODES.M)
    fun getOnline(context: Context) : Boolean{
        return generalFunctions.isOnline(context)
    }
    fun getFavDataBase(): LiveData<List<FavData>> {
        return dataSourceViewModel.getFavDataBase()
    }

    fun getAlertDialogLiveData():LiveData<FavData>{
        return alertDialogLiveData
    }

    fun getIntent():LiveData<Int>{
        return intentLiveData
    }


}
