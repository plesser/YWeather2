package ru.plesser.yweather2.fragments

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.fragment.app.activityViewModels
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import ru.plesser.yweather2.MainApp
import ru.plesser.yweather2.R
import ru.plesser.yweather2.data.City
import ru.plesser.yweather2.data.Data
import ru.plesser.yweather2.data.room.WeatherEntity
import ru.plesser.yweather2.data.template.weather.Weather
import ru.plesser.yweather2.databinding.FragmentWeatherBinding
import ru.plesser.yweather2.utils.Assets
import ru.plesser.yweather2.utils.Loader


private val TAG = "WeatherFragment"

class WeatherFragment: Fragment(){
//    private lateinit var viewModel: WeatherViewModel

    private val viewModel : WeatherViewModel by activityViewModels {
                    WeatherViewModel.WeatherViewModelFactory((context?.applicationContext as MainApp).database)
}
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
        //viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

        this.weatherKey = Assets.getKeyYWeather(requireActivity().applicationContext as Application)

        initViews()

        //getData()
        getDataData2()

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

    private fun getDataData2() {
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
            binding.windImageView.setImageDrawable(
                activity?.let { ContextCompat.getDrawable(it.applicationContext, getDirWind(weather.fact.wind_dir)) })


            viewModel.insertWeather(citiesList[position], weather)

            Log.d(TAG, "icon is " + weather.fact.icon)
            val icon: String =
                "https://yastatic.net/weather/i/icons/funky/dark/${weather.fact.icon}.svg"
            Log.d(TAG, icon)
            Picasso.get().load("https://media.citroen.ru/design/frontend/images/logo.png").into(binding.citroenImageView);
            binding.weatherImageView.loadSvg(icon)
        }

    }

    private fun getDirWind(windDir: String) =
        when (windDir){
            "e" -> R.drawable.ic_arrow_east
            "n" -> R.drawable.ic_arrow_north
            "ne" -> R.drawable.ic_arrow_north_east
            "nw" -> R.drawable.ic_arrow_north_west
            "s" -> R.drawable.ic_arrow_south
            "se" -> R.drawable.ic_arrow_east
            "sw" -> R.drawable.ic_arrow_north_west
            "w" -> R.drawable.ic_arrow_west
            else -> R.drawable.ic_silence
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

