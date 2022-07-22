package ru.plesser.yweather2.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.plesser.yweather2.data.City
import ru.plesser.yweather2.data.RRequests
import ru.plesser.yweather2.data.WeatherRepository
import ru.plesser.yweather2.data.room.CityEntity
import ru.plesser.yweather2.data.template.geocoder.Geocoder
import ru.plesser.yweather2.data.template.weather.Weather
import java.util.ArrayList

class YWeatherViewModel(): ViewModel() {

    private val weaherRepository = WeatherRepository.get()

    var geocoderLiveData: MutableLiveData<Geocoder> = MutableLiveData()
    var weatherLiveData: MutableLiveData<Weather> = MutableLiveData()

    fun requestCities(geocoderKey: String, city: String, callback: RRequests.CallbackRequestCities):MutableLiveData<Geocoder>{
        geocoderLiveData = RRequests().requestCitiesRetrofit(geocoderKey, city, callback)
        return geocoderLiveData
    }


    fun requestWeather(weatherKey: String, lat: Double, lon: Double, callback: RRequests.CallbackRequestWeather): MutableLiveData<Weather>{
        weatherLiveData = RRequests().requestWeatherRetrofit(weatherKey, lat, lon, callback)
        return weatherLiveData
    }

    fun insertCities(citiesList: ArrayList<City>) = viewModelScope.launch{
        weaherRepository.insertCity(citiesList)
    }


}