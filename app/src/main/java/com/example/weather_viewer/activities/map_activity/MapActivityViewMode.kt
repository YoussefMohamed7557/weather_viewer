package com.example.weather_viewer.activities.map_activity

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weather_viewer.R
import com.example.weather_viewer.data_source.GeneralRepository
import com.example.weather_viewer.data_source.local.shared_preferences.SettingModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapActivityViewMode(application: Application) : AndroidViewModel(application) {
    val saveLatLng : MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    private val generalRepository: GeneralRepository = GeneralRepository.getInstance(application)
    val saveFav :MutableLiveData<Boolean> =MutableLiveData<Boolean>()
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

    fun saveLocationSetting(latLng: LatLng)=generalRepository.saveLocationSetting(latLng)
    fun showAlarm(context: Context) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle(context.getString(R.string.are_you_sure))
        alertDialogBuilder.setMessage(context.getString(R.string.you_want_to_add))
        alertDialogBuilder.setPositiveButton(context.getString(R.string.yes)) { _, _ ->
            saveFav.value=true
        }
        alertDialogBuilder.setNegativeButton(context.getString(R.string.no)) { _, _ ->
            saveFav.value=false
        }
        alertDialogBuilder.show()
    }

    fun getSettnig(): LiveData<SettingModel> {
        return generalRepository.getSetting()
    }

    fun saveFav(lat: String,lon: String,lang: String,units :String){
        //dataSourceViewModel.saveFave(lat,lon,lang,units)
        viewModelScope.launch(Dispatchers.IO){
            generalRepository.saveFave(lat,lon,lang,units)
        }
    }

}
