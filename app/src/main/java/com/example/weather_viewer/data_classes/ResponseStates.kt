package com.example.weather_viewer.data_classes

sealed class ResponseStates<T>{
    class OnSuccess<T>(var data:T):ResponseStates<T>()
    class OnError<T>(var message:String):ResponseStates<T>()
    class Onloading<T>():ResponseStates<T>()
}
