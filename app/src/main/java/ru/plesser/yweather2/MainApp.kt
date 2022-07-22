package ru.plesser.yweather2

import android.app.Application
import ru.plesser.yweather2.data.WeatherRepository
import ru.plesser.yweather2.data.room.WeatherDatabase

class MainApp : Application(){

    override fun onCreate() {
        super.onCreate()
        WeatherRepository.initialize(this)
    }
}