package com.example.weather_viewer.data_source.remote

class Repository(private val apiInterface : ApiInterface) {
    fun getOneCall(lat: String,lon: String,lang: String, appid: String,exclude :String,units :String) =
        apiInterface.getOneCall(lat,lon,lang,appid,exclude,units)
    fun getFavCall(lat: String,lon: String,lang: String, appid: String,exclude :String,units :String) =
        apiInterface.getFavCall(lat,lon,lang,appid,exclude,units)

}