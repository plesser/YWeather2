package ru.plesser.yweather2.utils

import android.app.Application
import com.google.gson.Gson
import ru.plesser.yweather2.data.template.geocoder.Geocoder
import ru.plesser.yweather2.data.template.weather.Weather
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.stream.Collectors

object Loader {

    fun requestCities(application: Application, city: String, block:(geocoder:Geocoder)->Unit){
        val geocoderKey = Assets.getKeyGeocoder(application.getApplicationContext() as Application)
        val urlstr = "https://geocode-maps.yandex.ru/1.x/?apikey=${geocoderKey}&geocode=${city}&format=json"
        val url = URL(urlstr)

        var myConnection = url.openConnection() as HttpURLConnection
        myConnection.readTimeout = 5000
        Thread{
            val reader = BufferedReader(InputStreamReader(myConnection.inputStream))
            val geocoder = Gson().fromJson(getLines(reader), Geocoder::class.java)
            block(geocoder)
        }.start()

    }

    fun requestWeather(application: Application, lat: Double,lon: Double,block:(weather: Weather)->Unit){
        val weatherKey = Assets.getKeyYWeather(application.getApplicationContext() as Application)
        val uri = URL("https://api.weather.yandex.ru/v2/informers?lat=${lat}&lon=${lon}")

        var myConnection = uri.openConnection() as HttpURLConnection
        myConnection.readTimeout = 5000
        myConnection.addRequestProperty("X-Yandex-API-Key",weatherKey)
        Thread{
            val reader = BufferedReader(InputStreamReader(myConnection.inputStream))
            val weather = Gson().fromJson(getLines(reader), Weather::class.java)
            block(weather)
        }.start()
    }

    fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }
}