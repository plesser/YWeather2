package ru.plesser.yweather2.fragments

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import ru.plesser.yweather2.data.City
import ru.plesser.yweather2.data.Data
import ru.plesser.yweather2.data.template.weather.Weather
import ru.plesser.yweather2.databinding.FragmentWeatherBinding
import ru.plesser.yweather2.utils.Assets
import ru.plesser.yweather2.utils.Loader


private val TAG = "WeatherFragment"

class WeatherFragment: Fragment(){
    private lateinit var viewModel: WeatherViewModel
    private lateinit var binding: FragmentWeatherBinding
    private var citiesList: ArrayList<City> = Data.newInstance().cities
    lateinit var weather: Weather


    var position: Int = 0

    private lateinit var weatherKey: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

        this.weatherKey = Assets.getKeyYWeather(requireActivity().applicationContext as Application)

        initViews()

        //getData()
        getDataRetrofit()

    }


    private fun initViews() {
        binding.nameTextView.text = citiesList[position].name
        binding.latTextView.text = citiesList[position].lat.toString()
        binding.lonTextView.text = citiesList[position].lon.toString()
    }

    private fun getData() {
        Thread {
            weather = Loader.requestWeather(
                requireActivity().application,
                citiesList[position].lat,
                citiesList[position].lon
            )
            activity?.runOnUiThread {
                binding.realtempTextView.text = weather.fact.temp.toString()
                binding.feeltempTextView.text = weather.fact.feels_like.toString()

            }
        }.start()
    }

    private fun getDataRetrofit() {
        val weatherLiveData:LiveData<String> = viewModel.requestWeatherRetrofit(
            requireActivity().application,
            citiesList[position].lat,
            citiesList[position].lon)

        weatherLiveData.observe(
            viewLifecycleOwner
        ) { responseString ->
            Log.d(TAG, "Response received: $responseString")
            weather = Gson().fromJson(responseString, Weather::class.java)
            binding.realtempTextView.text = weather.fact.temp.toString()
            binding.feeltempTextView.text = weather.fact.feels_like.toString()
            Log.d(TAG, "icon is " + weather.fact.icon)
            val icon: String =
                "https://yastatic.net/weather/i/icons/funky/dark/${weather.fact.icon}.svg"
            Log.d(TAG, icon)

            Picasso.get().load("https://media.citroen.ru/design/frontend/images/logo.png").into(binding.citroenImageView);
            binding.weatherImageView.loadSvg(icon)
        }

    }

    fun ImageView.loadSvg(url: String) {
        val imageLoader = ImageLoader.Builder(this.context)
            .componentRegistry { add(SvgDecoder(this@loadSvg.context)) }
            .build()

        val request = ImageRequest.Builder(this.context)
            .crossfade(true)
            .crossfade(500)
            .data(url)
            .target(this)
            .build()

        imageLoader.enqueue(request)
    }


    companion object {
        fun newInstance() = WeatherFragment()
    }

}

