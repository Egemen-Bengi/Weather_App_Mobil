package com.egemenbengi.weatherproject.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.egemenbengi.weatherproject.R
import com.egemenbengi.weatherproject.adapter.WeatherAdapter
import com.egemenbengi.weatherproject.databinding.FragmentWeatherListBinding
import com.egemenbengi.weatherproject.viewmodel.WeatherListViewModel

class WeatherList : Fragment() {

    private var _binding: FragmentWeatherListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: WeatherListViewModel
    private val adapter = WeatherAdapter(arrayListOf())
    private lateinit var dataCity: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWeatherListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[WeatherListViewModel::class.java]
        viewModel.refreshData("Canakkale")

        binding.recyclerViewID.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewID.adapter = adapter

        binding.swipeRefreshId.setOnRefreshListener {
            binding.progressBar.visibility = View.VISIBLE
            binding.recyclerViewID.visibility = View.GONE
            viewModel.refreshDataFromInternet("Canakkale")
            binding.swipeRefreshId.isRefreshing = false
        }
        binding.btnSearchID.setOnClickListener { search(it) }
        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.weatherList.observe(viewLifecycleOwner){ weatherList ->
            if (weatherList != null){
                adapter.updateWeathers(weatherList)
                binding.recyclerViewID.visibility = View.VISIBLE
            } else {
                Toast.makeText(requireContext(), "Yanlış şehir girildi", Toast.LENGTH_LONG).show()
            }
        }

        viewModel.progressBar.observe(viewLifecycleOwner){
            if (it){
                binding.progressBar.visibility = View.VISIBLE
                binding.recyclerViewID.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun search(view: View){
        try{
            dataCity = binding.textSearchCityID.text.toString()
            viewModel.refreshDataFromInternet(dataCity)
        } catch (exception: Exception){
            Toast.makeText(requireContext(), "Yanlış şehir girildi", Toast.LENGTH_LONG).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}