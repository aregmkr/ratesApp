package com.rates_app

import android.os.Bundle
import com.rates_app.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


class MainActivity : AppCompatActivity() {

    companion object {
        var lastSelectedFragmentId: Int = R.id.nav_rates
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var listForAdapter: ArrayList<CurrencyDataClass>
    private lateinit var rateList: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        when (lastSelectedFragmentId) {
            R.id.nav_rates -> replaceFragment(RatesFragment())
            else -> replaceFragment(SettingsFragment())
        }

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_rates -> {
                    lastSelectedFragmentId = R.id.nav_rates
                    replaceFragment(RatesFragment())
                }
                R.id.nav_settings -> {
                    lastSelectedFragmentId = R.id.nav_settings
                    replaceFragment(SettingsFragment())
                }
                R.id.nav_convertor -> {
                    lastSelectedFragmentId = R.id.nav_convertor
                    replaceFragment(ConvertorFragment())
                }
            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }

}