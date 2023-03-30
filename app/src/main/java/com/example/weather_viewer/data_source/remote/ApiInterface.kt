package com.example.weather_viewer.data_source.remote

import com.example.weather_viewer.data_source.local.room.entities.AllData
import com.example.weather_viewer.data_source.local.room.entities.FavData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
interface ApiInterface {

    @GET("/data/2.5/onecall?")
    fun getOneCall(@Query("lat") lat: String,
                   @Query("lon") lon: String,
                   @Query("lang") lang: String,
                   @Query("appid") appid: String,
                   @Query("exclude") exclude :String,
                   @Query("units") units :String) :Call<AllData>

    @GET("/data/2.5/onecall?")
    fun getFavCall(@Query("lat") lat: String,
                   @Query("lon") lon: String,
                   @Query("lang") lang: String,
                   @Query("appid") appid: String,
                   @Query("exclude") exclude :String,
                   @Query("units") units :String) :Call<FavData>
}
