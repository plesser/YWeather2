package ru.plesser.yweather2.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import ru.plesser.yweather2.api.CitiesAPI
import ru.plesser.yweather2.api.WeatherAPI
import ru.plesser.yweather2.data.template.geocoder.Geocoder
import ru.plesser.yweather2.data.template.weather.Weather

private const val TAG = "RRequests"

class RRequests(): ViewModel() {

    private val citiesApi: CitiesAPI
    private val weatherApi: WeatherAPI


    init {
        val retrofitCities = Retrofit.Builder()
            .baseUrl("https://geocode-maps.yandex.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        citiesApi = retrofitCities.create(CitiesAPI::class.java)

        val retrofitWeather = Retrofit.Builder()
            .baseUrl("https://api.weather.yandex.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        weatherApi = retrofitWeather.create(WeatherAPI::class.java)
    }


    fun requestCitiesRetrofit(geocoderKey: String, city: String, callback: CallbackRequestCities): MutableLiveData<Geocoder> {
        Log.d(TAG, "RRequests.requestCitiesRetrofit")
        val responseLiveData: MutableLiveData<Geocoder> = MutableLiveData()

        //val citiesAPI: CitiesAPI = retrofit.create(CitiesAPI::class.java)
        val citiesRequest: Call<Geocoder> = citiesApi.getCity(geocoderKey, city, "json")

        citiesRequest.enqueue(object : Callback<Geocoder> {
            override fun onFailure(call: Call<Geocoder>, t: Throwable) {
                Log.d(TAG, "Failed to fetch cities", t)
                callback.setStatusRequestCities("error")
            }
            override fun onResponse(
                call: Call<Geocoder>,
                response: Response<Geocoder>
            ) {
                Log.d(TAG, "Response received " + response.body())
                //val geocoder = Gson().fromJson(response.body(), Geocoder::class.java)
                responseLiveData.value = response.body()

            }
        })

        return responseLiveData

    }

    fun requestWeatherRetrofit(weatherKey: String, lat: Double, lon: Double, callback: CallbackRequestWeather): MutableLiveData<Weather> {
        val responseLiveData: MutableLiveData<Weather> = MutableLiveData()

        val weatherRequest: Call<Weather> = weatherApi.getWeather(weatherKey, lat, lon)

        weatherRequest.enqueue(object : Callback<Weather> {
            override fun onFailure(call: Call<Weather>, t: Throwable) {
                Log.d(TAG, "Failed to fetch weather", t)
                callback.setStatusRequestWeather("error")
            }
            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                Log.d(TAG, "Response received " + response.body())
                responseLiveData.value = response.body()
            }

        })

        return responseLiveData
    }


    interface CallbackRequestCities{
        fun setStatusRequestCities(status: String)
    }

    interface CallbackRequestWeather{
        fun setStatusRequestWeather(status: String)
    }
}