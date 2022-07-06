package ru.plesser.yweather2.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.plesser.yweather2.data.City

class CitiesViewModel: ViewModel() {

    val urlLiveData = MutableLiveData<City>()

    fun requestCity(cities: String) {
        //liveData.value = AppState.Loading
        Thread{

        }.start()
    }

}