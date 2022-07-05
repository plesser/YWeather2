package ru.plesser.yweather2.fragments

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.plesser.yweather2.adapters.CitiesAdapter
import ru.plesser.yweather2.data.City
import ru.plesser.yweather2.databinding.FragmentCitiesBinding
import ru.plesser.yweather2.utils.Assets

private val TAG = "CityFragment"

class CitiesFragment : Fragment() {
    private lateinit var binding: FragmentCitiesBinding
    private lateinit var viewModel: CitiesViewModel

    private lateinit var adapter: CitiesAdapter
    private var citiesList: List<City> = ArrayList<City>()

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

        this.geocoderKey = Assets.getKeyGeocoder(requireActivity().getApplicationContext() as Application)

        binding.citiesRecyclerview.layoutManager = LinearLayoutManager(activity)
        adapter = CitiesAdapter(this.citiesList, activity)
        binding.citiesRecyclerview.adapter = adapter

        binding.searchButton.setOnClickListener{
            if (binding.cityEdittext.text.toString().length < 5){
                Toast.makeText(context, "Min length city is 6 symbols...", Toast.LENGTH_LONG).show()
            } else {
                viewModel.requestCity(binding.cityEdittext.text.toString())
            }
        }
    }

    companion object {
        fun newInstance() = CitiesFragment()
    }

}