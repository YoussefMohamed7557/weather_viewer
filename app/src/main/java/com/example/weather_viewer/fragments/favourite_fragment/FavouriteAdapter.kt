package com.example.weather_viewer.fragments.favourite_fragment

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_viewer.R
import com.example.weather_viewer.data_source.local.room.entities.FavData

class FavouriteAdapter(var favViewModel: FavouriteViewModel,val units:String) : RecyclerView.Adapter<FavouriteAdapter.MyViewHolder>() {
    lateinit var models: List<FavData>
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var temp = itemView.findViewById<TextView>(R.id.currentTemp)
        var tempUnits = itemView.findViewById<TextView>(R.id.tempUnit)
        var description = itemView.findViewById<TextView>(R.id.description)
        var time_Zone = itemView.findViewById<TextView>(R.id.time_zone)
        var icon = itemView.findViewById<ImageView>(R.id.currentModeImg)
        var remove = itemView.findViewById<ImageView>(R.id.remove_fav)


        @RequiresApi(Build.VERSION_CODES.O)
        fun binding(favData: FavData,position: Int) {
            favViewModel.loadImage(icon, favData.current.weather[0].icon)
            description.text = favData.current.weather[0].description
            temp.text = favData.current.temp.toString()
            time_Zone.text=favData.timezone
            tempUnits.text=favViewModel.getUnites(units)

            itemView.setOnClickListener{
                favViewModel.intentLiveData(position)
            }
            itemView.setOnLongClickListener{
                favViewModel.setAlertDialogLiveData(favData)
                true
            }
            remove.setOnClickListener {
                favViewModel.setAlertDialogLiveData(favData)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = inflater.inflate(R.layout.favourite_item, parent, false)
        return MyViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding(models.get(position),position)
    }

    override fun getItemCount(): Int {
        return models.size
    }
}