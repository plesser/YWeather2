package ru.plesser.yweather2.fragments

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import ru.plesser.yweather2.api.WeatherAPI
import ru.plesser.yweather2.data.City
import ru.plesser.yweather2.utils.Assets

private val TAG = "WeatherViewModel"

class WeatherViewModel: ViewModel() {

    fun requestWeatherRetrofit(application: Application, lat: Double, lon: Double): LiveData<String> {
        val responseLiveData: MutableLiveData<String> = MutableLiveData()
        val weatherKey = Assets.getKeyYWeather(application.getApplicationContext() as Application)

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
}