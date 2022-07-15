package ru.plesser.yweather2

import android.app.Application
import ru.plesser.yweather2.data.room.WeatherDatabase

class MainApp : Application(){
    val database by lazy {
        WeatherDatabase.getDataBase(this)
    }
}