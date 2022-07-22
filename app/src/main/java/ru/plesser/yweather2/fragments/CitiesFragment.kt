package ru.plesser.yweather2.fragments

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ru.plesser.yweather2.adapters.CitiesAdapter
import ru.plesser.yweather2.data.City
import ru.plesser.yweather2.data.Data
import ru.plesser.yweather2.data.RRequests
import ru.plesser.yweather2.databinding.FragmentCitiesBinding
import ru.plesser.yweather2.utils.Assets
import ru.plesser.yweather2.utils.Transform

private val TAG = "CityFragment"

class CitiesFragment : Fragment(), CitiesAdapter.Listener, RRequests.CallbackRequestCities {

    private lateinit var binding: FragmentCitiesBinding
    private lateinit var viewModel: YWeatherViewModel
    lateinit var listener: Listener

    private lateinit var adapter: CitiesAdapter
    private var citiesList: ArrayList<City> = Data.newInstance().cities

    private lateinit var geocoderKey: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCitiesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(YWeatherViewModel::class.java)

        geocoderKey = Assets.getKeyGeocoder(requireActivity().applicationContext as Application)

        binding.citiesRecyclerview.layoutManager = LinearLayoutManager(activity)
        adapter = CitiesAdapter(this.citiesList, activity, this@CitiesFragment)
        binding.citiesRecyclerview.adapter = adapter



        binding.searchButton.setOnClickListener{
            if (binding.cityEdittext.text.toString().length < 5){
                Toast.makeText(context, "Min length city is 6 symbols...", Toast.LENGTH_LONG).show()
            } else {
                var geocoderLiveData = viewModel.requestCities(geocoderKey, binding.cityEdittext.text.toString(), this@CitiesFragment)
                geocoderLiveData.observe(
                    viewLifecycleOwner,
                    Observer {
                            geocoder ->
                        Log.d(TAG, "------------------------------------------------------- 1")
                        Log.d(TAG, "viewModel.cityLiveData is " + geocoder.toString())
                        citiesList.clear()
                        citiesList.addAll(Transform.getCities(geocoder))
                        adapter.citiesList = citiesList
                        adapter.notifyDataSetChanged()
                        binding.statusTextview.text = "online"
                        binding.statusTextview.setTextColor(Color.parseColor("#00FF00"))

                        viewModel.insertCities(citiesList)

                    }
                )
            }
        }

        // TODO надо разобраться почему не работает location в эмуляторе
        //getLocation()
    }

    fun getLocation(){
        val locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            //val provider = locationManager.getProvider(LocationManager.GPS_PROVIDER)
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000L, 100F, object: LocationListener {
                override fun onLocationChanged(location: Location) {
                    Log.d(TAG, "location " + location)
                }

            })
        }
    }

    override fun onCityClick(id: Int) {
        Log.d(TAG, "position fragment is ${id}")
        listener.onCityClick(id)
    }


    interface Listener{
        fun onCityClick(id: Int)
    }


    companion object {
        fun newInstance() = CitiesFragment()
    }

    override fun setStatusRequestCities(status: String) {
        Log.d(TAG, status)
        binding.statusTextview.text = "offline"
        binding.statusTextview.setTextColor(Color.parseColor("#FF0000"))


    }

}