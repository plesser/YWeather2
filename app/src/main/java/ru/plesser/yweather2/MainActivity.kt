package ru.plesser.yweather2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ru.plesser.yweather2.fragments.CitiesFragment
import ru.plesser.yweather2.fragments.WeatherFragment

private val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), CitiesFragment.Listener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null){
            val fragment = CitiesFragment.newInstance()
            //val fragment = CityFragment()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
            fragment.listener = this@MainActivity

        }
    }

    override fun onCityClick(id: Int) {
        Log.d(TAG, "position activity is ${id}")
        val fragment = WeatherFragment.newInstance(id)
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragment_container, fragment)
            .commit()
        //fragment.position = id
    }
}