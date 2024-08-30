package com.egemenbengi.weatherproject.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.egemenbengi.weatherproject.model.Weather
import com.egemenbengi.weatherproject.roomdb.WeatherDatabase
import com.egemenbengi.weatherproject.service.WeatherApiService
import com.egemenbengi.weatherproject.util.MySharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherListViewModel(application: Application) : AndroidViewModel(application) {
    val weatherList = MutableLiveData<List<Weather>>()
    val progressBar = MutableLiveData<Boolean>()

    private val apiService = WeatherApiService()
    private val mySharedPreferences = MySharedPreferences(getApplication())
    private val timer = 10 * 60 * 1000 * 1000 * 1000L

    fun refreshData(dataCity: String){
        val dataTime = mySharedPreferences.getTime()
        if (dataTime != null && dataTime != 0L && System.nanoTime() - dataTime < timer){
            getDataFromRoom()
        } else {
            getDataFromInternet(dataCity)
        }
    }

    fun refreshDataFromInternet(dataCity: String){
        getDataFromInternet(dataCity)
    }

    private fun getDataFromInternet(dataCity: String){
        progressBar.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val weatherResponse = apiService.getData(dataCity)
            withContext(Dispatchers.Main){
                progressBar.value = false
                showWeather(weatherResponse.result)
            }
        }
    }

    private fun getDataFromRoom(){
        progressBar.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val weathers = WeatherDatabase(getApplication()).weatherDao().getAllData()
            withContext(Dispatchers.Main){
                showWeather(weathers)
            }
        }
    }

    private fun saveToRoom(weathers: List<Weather>){
        viewModelScope.launch {
            val dao = WeatherDatabase(getApplication()).weatherDao()
            dao.deleteAll()
            val idList = dao.insertAll(*weathers.toTypedArray())
            var i = 0
            while (i < idList.size){
                weathers[i].uuid = idList[i].toInt()
                i++
            }
            showWeather(weathers)
        }
        mySharedPreferences.saveTime(System.nanoTime())
    }

    private fun showWeather(weathers: List<Weather>){
        weatherList.value = weathers
        progressBar.value = false
    }
}