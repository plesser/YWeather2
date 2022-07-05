package ru.plesser.yweather2.utils

import android.app.Application
import android.util.Log


private val TAG = "Assets"

object Assets {
    fun getKeyYWeather(application: Application):String{
        val bufferReader = application.assets.open("yweather.key").bufferedReader()
        val key = bufferReader.use {
            it.readText()
        }
        return key

    }

    fun getKeyGeocoder(application: Application):String{
        val bufferReader = application.assets.open("geocoder.key").bufferedReader()
        val key = bufferReader.use {
            it.readText()
        }
        return key
    }
}