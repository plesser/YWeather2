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
import ru.plesser.yweather2.api.WeatherAPI
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.stream.Collectors

private val TAG = "Loader"

object Loader {


    fun requestCities(application: Application, city: String): Geocoder{ //}, block:(geocoder:Geocoder)->Unit){
        val geocoderKey = Assets.getKeyGeocoder(application.getApplicationContext() as Application)
        return requestCities(geocoderKey, city)

    }

    fun requestCities(geocoderKey: String, city: String): Geocoder{ //}, block:(geocoder:Geocoder)->Unit){
        val urlstr = "https://geocode-maps.yandex.ru/1.x/?apikey=${geocoderKey}&geocode=${city}&format=json"
        Log.d(TAG, urlstr)
        val url = URL(urlstr)

        var myConnection = url.openConnection() as HttpURLConnection
        myConnection.readTimeout = 5000
        val reader = BufferedReader(InputStreamReader(myConnection.inputStream))
        val geocoder = Gson().fromJson(getLines(reader), Geocoder::class.java)
        //block(geocoder)
        return geocoder

    }


    fun requestWeather(application: Application, lat: Double,lon: Double): Weather{
        val weatherKey = Assets.getKeyYWeather(application.getApplicationContext() as Application)
        val uri = URL("https://api.weather.yandex.ru/v2/informers?lat=${lat}&lon=${lon}")

        val myConnection = uri.openConnection() as HttpURLConnection
        myConnection.readTimeout = 5000
        myConnection.addRequestProperty("X-Yandex-API-Key",weatherKey)
        val reader = BufferedReader(InputStreamReader(myConnection.inputStream))
        val weather = Gson().fromJson(getLines(reader), Weather::class.java)
        Log.d(TAG, weather.toString())
        Log.d(TAG, "lat=${lat}&lon=${lon}")
        Log.d(TAG, "fact temp " + weather.fact.temp)
        Log.d(TAG, "feel temp " + weather.fact.feels_like)

        return weather
    }

    fun requestWeatherRetrofit(application: Application, lat: Double,lon: Double): LiveData<String> {
        val weatherKey = Assets.getKeyYWeather(application.getApplicationContext() as Application)

        return requestWeatherRetrofit(weatherKey, lat, lon)

    }

    fun requestWeatherRetrofit(weatherKey: String, lat: Double,lon: Double): LiveData<String> {
        val responseLiveData: MutableLiveData<String> = MutableLiveData()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.weather.yandex.ru/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        val weatherAPI: WeatherAPI = retrofit.create(WeatherAPI::class.java)

        val weatherRequest: Call<String> = weatherAPI.getWeather(weatherKey, lat, lon)
        weatherRequest.enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d(TAG, "Failed to fetch weather", t)
            }
            override fun onResponse(
                call: Call<String>,
                response: Response<String>
            ) {
                Log.d(TAG, "Response received " + response.body())
                //weather = Gson().fromJson(response.body(), Weather::class.java)
                responseLiveData.value = response.body()

            }
        })

        return responseLiveData
    }


    fun getCities(geocoder: Geocoder) : ArrayList<City>{
        val members = geocoder.response.GeoObjectCollection.featureMember
        val cities: ArrayList<City> = ArrayList()
        for (member in members){
            val pos = member.GeoObject.Point.pos
            val lat = pos.split(" ")[1].toDouble()
            val lon = pos.split(" ")[0].toDouble()
            println("${lat} ${lon} ${member.GeoObject.metaDataProperty.GeocoderMetaData.text}")
            val city: City = City(member.GeoObject.metaDataProperty.GeocoderMetaData.text, lat, lon)
            cities.add(city)
        }
        return cities
    }

    fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }
}