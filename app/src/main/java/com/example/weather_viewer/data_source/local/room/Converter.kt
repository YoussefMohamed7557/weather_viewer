package com.example.weather_viewer.data_source.local.room
import androidx.room.TypeConverter
import com.example.weather_viewer.data_classes.one_call.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {


    @TypeConverter
    fun fromCurrentToGeson(list : Current) : String{
        return Gson().toJson(list)
    }
    @TypeConverter
    fun fromJsonToCurrrent(gson: String): Current {
        return Gson().fromJson(gson, Current::class.java)
    }

    @TypeConverter
    fun fromAlertToGeson(list :List<Alert>?) : String{
        return Gson().toJson(list)
    }
    @TypeConverter
    fun fromJsonToAlert(gson: String):List<Alert>?{
        val type = object : TypeToken<List<Alert>?>() {}.type
        return Gson().fromJson(gson,type)
    }
    @TypeConverter
    fun fromHourlyToGeson(list :List<Hourly>) : String{
        return Gson().toJson(list)
    }
    @TypeConverter
    fun fromJsonToHourly(gson: String):List<Hourly>{
        val type = object : TypeToken<List<Hourly>>() {}.type
        return Gson().fromJson(gson,type)
    }
    @TypeConverter
    fun fromDailyToGeson(list :List<Daily>) : String{
        return Gson().toJson(list)
    }
    @TypeConverter
    fun fromJsonToDaily(gson: String):List<Daily>{
        val type = object : TypeToken<List<Daily>>() {}.type
        return Gson().fromJson(gson,type)
    }
    ////////// favourite converters /////////////////////////

    @TypeConverter
    fun fromCurrentToGsonFav(list : com.example.weather_viewer.data_classes.favourite.Current) : String{
        return Gson().toJson(list)
    }
    @TypeConverter
    fun fromJsonToCurrentFav(gson: String): com.example.weather_viewer.data_classes.favourite.Current {
//        val type = object : TypeToken<List<Current>>() {}.type
        return Gson().fromJson(gson, com.example.weather_viewer.data_classes.favourite.Current::class.java)
    }

    @TypeConverter
    fun fromAlertToGsonFav(list :List<com.example.weather_viewer.data_classes.favourite.Alert>?) : String{
        return Gson().toJson(list)
    }
    @TypeConverter
    fun fromJsonToAlertFav(gson: String):List<com.example.weather_viewer.data_classes.favourite.Alert>?{
        val type = object : TypeToken<List<com.example.weather_viewer.data_classes.favourite.Alert>?>() {}.type
        return Gson().fromJson(gson,type)
    }
    @TypeConverter
    fun fromHourlyToGsonFav(list :List<com.example.weather_viewer.data_classes.favourite.Hourly>) : String{
        return Gson().toJson(list)
    }
    @TypeConverter
    fun fromJsonToHourlyFav(gson: String):List<com.example.weather_viewer.data_classes.favourite.Hourly>{
        val type = object : TypeToken<List<com.example.weather_viewer.data_classes.favourite.Hourly>>() {}.type
        return Gson().fromJson(gson,type)
    }
    @TypeConverter
    fun fromDailyToGsonFav(list :List<com.example.weather_viewer.data_classes.favourite.Daily>) : String{
        return Gson().toJson(list)
    }
    @TypeConverter
    fun fromJsonToDailyFav(gson: String):List<com.example.weather_viewer.data_classes.favourite.Daily>{
        val type = object : TypeToken<List<com.example.weather_viewer.data_classes.favourite.Daily>>() {}.type
        return Gson().fromJson(gson,type)
    }
}
