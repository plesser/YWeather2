package ru.plesser.yweather2.fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import ru.plesser.yweather2.R
import ru.plesser.yweather2.databinding.FragmentWeatherBinding


private val TAG = "WeatherFragment"

class WeatherFragment: Fragment()
    {
    private lateinit var binding: FragmentWeatherBinding

    var position: Int = 0

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

