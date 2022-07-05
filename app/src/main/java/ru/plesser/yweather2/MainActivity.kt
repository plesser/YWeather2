package ru.plesser.yweather2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.plesser.yweather2.fragments.CitiesFragment

class MainActivity : AppCompatActivity() {
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

        }
    }
}