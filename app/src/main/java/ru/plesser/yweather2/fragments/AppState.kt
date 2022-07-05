package ru.plesser.yweather2.fragments

import ru.plesser.yweather2.data.City

open class AppState {
    data class Success(val weatherData: City) : AppState()
    data class Error(val message: String) : AppState()
    object Loading : AppState()
}
