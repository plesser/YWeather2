package ru.plesser.yweather2.utils

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import ru.plesser.yweather2.data.City
import ru.plesser.yweather2.data.template.geocoder.Geocoder
import ru.plesser.yweather2.data.template.weather.Weather
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import ru.plesser.yweather2.api.CitiesAPI
import ru.plesser.yweather2.api.WeatherAPI
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.stream.Collectors

private val TAG = "Loader"

object Loader {

    fun getCities(geocoder: Geocoder) : ArrayList<City>{
        val members = geocoder.response.GeoObjectCollection.featureMember
        val cities: ArrayList<City> = ArrayList()
        for (member in members){
            val pos = member.GeoObject.Point.pos
            val lat = pos.split(" ")[1].toDouble()
            val lon = pos.split(" ")[0].toDouble()
            //println("${lat} ${lon} ${member.GeoObject.metaDataProperty.GeocoderMetaData.text}")
            Log.d(TAG, "${lat} ${lon} ${member.GeoObject.metaDataProperty.GeocoderMetaData.text}")
            val city: City = City(member.GeoObject.metaDataProperty.GeocoderMetaData.text, lat, lon)
            cities.add(city)
        }
        return cities
    }

    fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }
}