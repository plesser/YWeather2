package ru.plesser.yweather2.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CitiesViewModel(private val liveData: MutableLiveData<AppState> = MutableLiveData<AppState>()): ViewModel() {
    fun requestCity(cities: String) {
        liveData.value = AppState.Loading

    }

}