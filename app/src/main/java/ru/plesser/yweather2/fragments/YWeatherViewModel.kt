package ru.plesser.yweather2.fragments

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.plesser.yweather2.data.WeatherRepository
import ru.plesser.yweather2.data.template.geocoder.Geocoder

class YWeatherViewModel: ViewModel() {


    private var repositoty: WeatherRepository = WeatherRepository.get()


    val cityLiveData = MutableLiveData<Geocoder?>()

    fun fetchCities(geocoderKey: String, city: String) {
        viewModelScope.launch {
                fetchAsync(geocoderKey, city)
        }
    }

    private suspend fun fetchAsync(geocoderKey: String, city: String) {
        val geocoder: Geocoder? = repositoty.getCities(geocoderKey, city).value
        cityLiveData.postValue(geocoder)
    }
}