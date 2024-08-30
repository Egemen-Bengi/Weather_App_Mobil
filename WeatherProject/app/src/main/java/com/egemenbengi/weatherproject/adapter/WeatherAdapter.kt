package com.egemenbengi.weatherproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.egemenbengi.weatherproject.databinding.WeatherRowBinding
import com.egemenbengi.weatherproject.model.Weather

class WeatherAdapter(val weatherList: ArrayList<Weather>): RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {
    class WeatherViewHolder(val binding: WeatherRowBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding = WeatherRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }

    fun updateWeathers(newWeathers: List<Weather>){
        weatherList.clear()
        weatherList.addAll(newWeathers)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.binding.textRowID.text = "${weatherList[position].day} degree: ${weatherList[position].degree}°C ${weatherList[position].description} huminity: ${weatherList[position].humidity} g/m3"
    }
}