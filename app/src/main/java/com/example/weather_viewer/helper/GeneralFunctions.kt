package com.example.weather_viewer.helper

import android.annotation.SuppressLint
import android.os.Build
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

class GeneralFunctions {
    @RequiresApi(Build.VERSION_CODES.O)
    fun loadImage(imageView: ImageView, string: String) {
        Glide.with(imageView)
            .load("https://openweathermap.org/img/w/$string.png")
            .fitCenter()
            .into(imageView)
    }

    @SuppressLint("SimpleDateFormat")
    fun formateTime(format: Int): String {
        val dateFormat = SimpleDateFormat("HH:mm a")
        val date = Date()
        date.time = format.toLong() * 1000
        return dateFormat.format(date)
    }

    fun getUnites(units: String): String {
        return when (units) {
            "standard" -> {
                "K"
            }
            "imperial" -> {
                "F"
            }
            else -> {
                "C"
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun formateDate(format: Int): String {
        val sdf = SimpleDateFormat("MM/dd/yyyy")
        val netDate = Date(format.toLong() * 1000)
        return sdf.format(netDate)
    }

}
