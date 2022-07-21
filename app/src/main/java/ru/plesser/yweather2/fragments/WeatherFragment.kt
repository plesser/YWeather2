package ru.plesser.yweather2.fragments
import android.app.Application
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.squareup.picasso.Picasso
import ru.plesser.yweather2.R
import ru.plesser.yweather2.data.City
import ru.plesser.yweather2.data.Data
import ru.plesser.yweather2.data.RRequests
import ru.plesser.yweather2.data.template.weather.Weather
import ru.plesser.yweather2.databinding.FragmentWeatherBinding
import ru.plesser.yweather2.utils.Assets


private val TAG = "WeatherFragment"

private val CITY_ID: String = "city_id"

class WeatherFragment: Fragment(), RRequests.CallbackRequestWeather{

    private lateinit var binding: FragmentWeatherBinding
    private var citiesList: ArrayList<City> = Data.newInstance().cities
    private lateinit var weatherKey: String
    lateinit var weather: Weather
    private lateinit var viewModel: YWeatherViewModel

    var position: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherBinding.inflate(inflater)

        position = requireArguments().getInt(CITY_ID);


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(YWeatherViewModel::class.java)

        weatherKey = Assets.getKeyYWeather(requireActivity().applicationContext as Application)

        initViews()

        var weatherLiveData = viewModel.requestWeather(weatherKey, citiesList.get(position).lat, citiesList.get(position).lon, this@WeatherFragment)
        weatherLiveData.observe(viewLifecycleOwner){
            weather ->
            binding.realtempTextView.text = weather.fact.temp.toString()
                binding.feeltempTextView.text = weather.fact.feels_like.toString()
                binding.windImageView.setImageDrawable(
                    activity?.let {
                        ContextCompat.getDrawable(
                            it.applicationContext,
                            getDirWind(weather.fact.wind_dir)
                        )
                    })
                val icon: String =
                    "https://yastatic.net/weather/i/icons/funky/dark/${weather.fact.icon}.svg"
                Log.d(TAG, icon)
                Picasso.get().load("https://media.citroen.ru/design/frontend/images/logo.png")
                    .into(binding.citroenImageView);
                binding.weatherImageView.loadSvg(icon)
                binding.statusTextview.text = "online"
                binding.statusTextview.setTextColor(Color.parseColor("#00FF00"))


        }
    }

    private fun initViews() {
        binding.nameTextView.text = citiesList[position].name
        binding.latTextView.text = citiesList[position].lat.toString()
        binding.lonTextView.text = citiesList[position].lon.toString()
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
        fun newInstance(id: Int) : WeatherFragment{
            val weatherFragment = WeatherFragment()
            val args = Bundle()
            args.putInt(CITY_ID, id)
            weatherFragment.arguments = args
            return weatherFragment

        }
    }

    override fun setStatusRequestWeather(status: String) {
        Log.d(TAG, status)
        binding.statusTextview.text = "offline"
        binding.statusTextview.setTextColor(Color.parseColor("#FF0000"))
    }


}

