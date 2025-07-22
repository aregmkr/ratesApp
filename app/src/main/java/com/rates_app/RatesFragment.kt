package com.rates_app

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.rates_app.databinding.FragmentRatesBinding
import kotlinx.coroutines.launch

class RatesFragment : Fragment() {
    private val apiKey = "api key"
    private var _binding: FragmentRatesBinding? = null
    private val binding get() = _binding!!
    private lateinit var listForAdapter: ArrayList<CurrencyDataClass>
    private val currencies = listOf(
        "USD",
        "EUR",
        "AMD",
        "CZK",
        "RUB",
        "CAD",
        "DKK",
        "GEL",
        "KZT",
        "UAH",
        "UZS",
    )
    private var mainCurrencie = "USD"
    private var selectedFirst = currencies[0]
    private var selectedSecond = currencies[2]
    private lateinit var rateList: Array<String>
    private var imageList: Array<Int> = arrayOf(
        R.drawable.united_states_,
        R.drawable.european_union,
        R.drawable.flag_armenia,
        R.drawable.czech_republic,
        R.drawable.russia,
        R.drawable.canada,
        R.drawable.denmark,
        R.drawable.georgia,
        R.drawable.kazakhstan,
        R.drawable.ukraine,
        R.drawable.uzbekistan,
    )


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRatesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rateList = Array(currencies.size) { "" }
        listForAdapter = ArrayList()

        lifecycleScope.launch {
            if (requireContext().isWifiConnected()) {
                getRates(mainCurrencie)
            } else {
                Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_LONG)
                    .show()
            }
            getData()
        }

        with(binding) {
            swipeRefreshLayout.setOnRefreshListener {
                if (requireContext().isWifiConnected()) {
                    reloadRates()
                } else {
                    Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_LONG).show()
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
        }

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun Context.isWifiConnected(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
    }

    private suspend fun getRates(mainCurrency: String) {
        val response = RetrofitClient.apiService.getRates(apiKey, mainCurrency)
        if (response.result == "success") {
            println(response)
            for (i in currencies.indices) {
                val rawRate = response.conversion_rates[currencies[i]]
                rateList[i] = if (rawRate != null) {
                    String.format("%.2f", rawRate)
                } else {
                    "N/A"
                }
            }
        }
    }

    private fun getData() {
        listForAdapter.clear()
        for (i in currencies.indices) {
            val currencyData = CurrencyDataClass(imageList[i], currencies[i], rateList[i])
            listForAdapter.add(currencyData)
        }
        binding.recyclerView.adapter?.notifyDataSetChanged()
        with(binding) {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = CurrencyAdapterClass(listForAdapter)
        }
    }

    private fun reloadRates() {
        listForAdapter.clear()
        binding.recyclerView.adapter?.notifyDataSetChanged()

        lifecycleScope.launch {
            getRates(mainCurrencie)
            getData()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

}