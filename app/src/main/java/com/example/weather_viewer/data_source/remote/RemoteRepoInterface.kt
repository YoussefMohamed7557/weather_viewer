package com.example.weather_viewer.data_source.remote

import com.example.weather_viewer.data_source.local.room.entities.AllData
import com.example.weather_viewer.data_source.local.room.entities.FavData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteRepoInterface {
    suspend fun getOneCall(@Query("lat") lat: String,
                           @Query("lon") lon: String,
                           @Query("lang") lang: String,
                           @Query("appid") appid: String,
                           @Query("exclude") exclude :String,
                           @Query("units") units :String) :Response<AllData>


    suspend fun getFavCall(@Query("lat") lat: String,
                           @Query("lon") lon: String,
                           @Query("lang") lang: String,
                           @Query("appid") appid: String,
                           @Query("exclude") exclude :String,
                           @Query("units") units :String) :Response<FavData>
}