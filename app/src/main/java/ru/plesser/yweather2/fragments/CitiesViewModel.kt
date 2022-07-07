package ru.plesser.yweather2.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.plesser.yweather2.data.City
import ru.plesser.yweather2.data.template.geocoder.Geocoder
import ru.plesser.yweather2.utils.Loader

class CitiesViewModel: ViewModel() {

    val jsonLiveData = MutableLiveData<Geocoder>()

    fun fetchCity(geocoderKey: String, city: String) {
        viewModelScope.launch {
            // Dispatchers.IO (main-safety block)
            withContext(Dispatchers.IO) {
                fetchAsync(geocoderKey, city)
            }
        }
    }

    private suspend fun fetchAsync(geocoderKey: String, city: String){
        jsonLiveData.postValue(Loader.requestCities(geocoderKey, city))
    }
}