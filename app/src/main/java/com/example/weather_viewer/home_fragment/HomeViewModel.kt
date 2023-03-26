package com.example.weather_viewer.home_fragment

import android.annotation.SuppressLint
import android.os.Build
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.example.weather_viewer.helper.GeneralFunctions

class HomeViewModel {
    private val generalFunctions : GeneralFunctions = GeneralFunctions()

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

}
