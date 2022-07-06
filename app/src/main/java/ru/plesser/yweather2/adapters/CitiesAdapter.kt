package ru.plesser.yweather2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import ru.plesser.yweather2.R
import ru.plesser.yweather2.data.City
import ru.plesser.yweather2.databinding.ItemCityBinding

class CitiesAdapter(var citiesList: ArrayList<City>, val activity: FragmentActivity?): RecyclerView.Adapter<CitiesAdapter.CityHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        val layoutInflater = LayoutInflater.from(activity)
        val view = layoutInflater.inflate(R.layout.item_city, parent, false)
        return CityHolder(view)
    }

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        holder.bind(citiesList.get(position))
    }

    override fun getItemCount(): Int {
        return citiesList.size
    }


    class CityHolder(view: View):RecyclerView.ViewHolder(view){
        private val binding = ItemCityBinding.bind(view)
        private lateinit var city: City

        fun bind(city: City){
            this.city = city
            binding.cityTextview.text = city.name
            binding.latTextView.text = city.lat.toString()
            binding.lonTextview.text = city.lon.toString()


        }

    }

}
