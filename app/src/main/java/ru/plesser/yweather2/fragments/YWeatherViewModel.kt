package ru.plesser.yweather2.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.plesser.yweather2.data.RRequests
import ru.plesser.yweather2.data.template.geocoder.Geocoder
import ru.plesser.yweather2.data.template.weather.Weather

class YWeatherViewModel: ViewModel() {



    fun requestCities(geocoderKey: String, city: String):LiveData<Geocoder>{
        var geocoderLiveData = RRequests().requestCitiesRetrofit(geocoderKey, city)
        return geocoderLiveData
    }


    fun requestWeather(weatherKey: String, lat: Double, lon: Double): LiveData<Weather>{
        var weatherLiveData = RRequests().requestWeatherRetrofit(weatherKey, lat, lon)
        return weatherLiveData
    }


}