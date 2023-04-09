package com.example.weather_viewer.data_source.remote

class Repository(private val apiInterface : ApiInterface) {
    suspend fun getOneCall(lat: String, lon: String, lang: String, appid: String, exclude :String, units :String) =
        apiInterface.getOneCall(lat,lon,lang,appid,exclude,units)
    suspend fun getFavCall(lat: String,lon: String,lang: String, appid: String,exclude :String,units :String) =
        apiInterface.getFavCall(lat,lon,lang,appid,exclude,units)
}