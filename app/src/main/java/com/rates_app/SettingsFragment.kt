package com.rates_app

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.rates_app.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentMode = requireContext().getSharedPreferences("settings", MODE_PRIVATE)
            .getInt("theme_mode", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        when (currentMode) {
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> binding.radioAuto.isChecked = true
            AppCompatDelegate.MODE_NIGHT_YES -> binding.radioOn.isChecked = true
            AppCompatDelegate.MODE_NIGHT_NO -> binding.radioOff.isChecked = true
        }

        with(binding) {
            radioAuto.setOnClickListener {
                changeTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }

            radioOn.setOnClickListener {
                changeTheme(AppCompatDelegate.MODE_NIGHT_YES)
            }

            radioOff.setOnClickListener {
                changeTheme(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun changeTheme(mode: Int) {
        AppCompatDelegate.setDefaultNightMode(mode)

        requireContext().getSharedPreferences("settings", MODE_PRIVATE)
            .edit()
            .putInt("theme_mode", mode)
            .apply()

        MainActivity.lastSelectedFragmentId = R.id.nav_settings
        requireActivity().recreate()


    }
}