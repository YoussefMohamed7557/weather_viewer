package com.example.weather_viewer.fragments.favourite_fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_viewer.R
import com.example.weather_viewer.activities.favourite_details_activity.FavouriteDetails
import com.example.weather_viewer.data_source.local.room.entities.FavData
import com.example.weather_viewer.databinding.FragmentFavouriteBinding
import com.example.weather_viewer.activities.main_activity.MainActivity
import com.example.weather_viewer.activities.map_activity.MapActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavouriteFragment : Fragment() {

    private lateinit var binding : FragmentFavouriteBinding
    private lateinit var favouriteViewModel: FavouriteViewModel
    private lateinit var adapter: FavouriteAdapter
    private lateinit var dataList : List<FavData>
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        favouriteViewModel= ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application))[FavouriteViewModel::class.java]

        adapter= FavouriteAdapter(favouriteViewModel, MainActivity.units)
        binding.addButton.setOnClickListener{
            if (favouriteViewModel.getOnline(requireActivity())) {
                val intent = Intent(activity, MapActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(requireActivity(),getString(R.string.you_are_offline), Toast.LENGTH_SHORT).show()
            }
        }
        lifecycleScope.launch {
            favouriteViewModel.getFavDataBase()
            favouriteViewModel.allFavoriteList.collect{
                if (it.isNotEmpty()) {
                    binding.recyclerViewFav.visibility = View.VISIBLE
                    binding.empty.visibility = View.GONE
                    loadFavourite(it)
                    dataList = it
                } else {
                    binding.empty.visibility = View.VISIBLE
                    binding.recyclerViewFav.visibility = View.GONE
                }
            }
        }
        /*
        favouriteViewModel.getFavDataBase().observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.recyclerViewFav.visibility = View.VISIBLE
                binding.empty.visibility = View.GONE
                loadFavourite(it)
                dataList = it
            } else {
                binding.empty.visibility = View.VISIBLE
                binding.recyclerViewFav.visibility = View.GONE
            }
        }
         */
        favouriteViewModel.getAlertDialogLiveData().observe(viewLifecycleOwner) {
            if (it != null) showAlarm(it.lat.toString(), it.lon.toString())
        }
        favouriteViewModel.getIntent().observe(viewLifecycleOwner) {
            val intent = Intent(activity, FavouriteDetails::class.java)
            intent.putExtra("lat", "${dataList[it].lat}")
            intent.putExtra("lon", "${dataList[it].lon}")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK
            startActivity(intent)
        }
        return binding.root
    }
    private fun loadFavourite(it: List<FavData>) {
        val lay : RecyclerView.LayoutManager= LinearLayoutManager(activity)
        binding.recyclerViewFav.layoutManager=lay
        adapter.models=it
        binding.recyclerViewFav.adapter=adapter
    }
    private fun showAlarm(lat : String,lon: String) {
        val alertDialogBuilder = AlertDialog.Builder(requireActivity())
        alertDialogBuilder.setTitle("Are you Sure")
        alertDialogBuilder.setMessage("you want to delete this city")
        alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
            CoroutineScope(Dispatchers.IO).launch {
                favouriteViewModel.deleteOneFav(lat,lon)
            }
        }
        alertDialogBuilder.setNegativeButton("No") { _, _ ->

        }
        alertDialogBuilder.show()
    }

}