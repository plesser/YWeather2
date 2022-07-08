package ru.plesser.yweather2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.plesser.yweather2.data.City
import ru.plesser.yweather2.data.Data
import ru.plesser.yweather2.data.template.weather.Weather
import ru.plesser.yweather2.databinding.FragmentCitiesBinding
import ru.plesser.yweather2.databinding.FragmentWeatherBinding
import ru.plesser.yweather2.utils.Loader

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
        initViews()

        getData()

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

    companion object {
        fun newInstance() = WeatherFragment()
    }

}

