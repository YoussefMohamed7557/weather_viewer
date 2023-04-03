package com.example.weather_viewer.map_activity

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.weather_viewer.R
import com.example.weather_viewer.data_source.DataSourceViewModel
import com.google.android.gms.maps.model.LatLng

class MapActivityViewMode(application: Application) : AndroidViewModel(application) {
    val saveLatLng : MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    private val dataSourceViewModel: DataSourceViewModel = DataSourceViewModel(application)
    fun showLocationSavingAlarm(context: Context) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle(context.getString(R.string.are_you_sure))
        alertDialogBuilder.setMessage(context.getString(R.string.are_you_sure_location))
        alertDialogBuilder.setPositiveButton(context.getString(R.string.yes)) { _, _ ->
            saveLatLng.value=true
        }
        alertDialogBuilder.setNegativeButton(context.getString(R.string.no)) { _, _ ->
            saveLatLng.value=false
        }
        alertDialogBuilder.show()
    }

    fun saveLocationSetting(latLng: LatLng)=dataSourceViewModel.saveLocationSetting(latLng)
    fun showAlarm(mapActivity: MapActivity) {

    }

}
