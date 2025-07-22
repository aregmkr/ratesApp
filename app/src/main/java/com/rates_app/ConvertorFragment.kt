package com.rates_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.rates_app.databinding.FragmentConvertorBinding
import kotlinx.coroutines.launch

class ConvertorFragment : Fragment() {
    private var _binding: FragmentConvertorBinding? = null
    private val binding get() = _binding!!
    private val supportData: SupportData = SupportData()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentConvertorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, supportData.currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        with(binding) {
            sFirstCurrency.adapter = adapter
            sSecondCurrency.adapter = adapter
            sFirstCurrency.setSelection(0)
            sSecondCurrency.setSelection(1)

            sFirstCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    supportData.selectedFirst = parent.getItemAtPosition(position).toString()
                    convertCurrencies()
                    setImageForCurrencies()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }

            sSecondCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    supportData.selectedSecond = parent.getItemAtPosition(position).toString()
                    convertCurrencies()
                    setImageForCurrencies()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }

            btnConvert.setOnClickListener {
                convertCurrencies()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun convertCurrencies() {
        with(binding) {
            val amount = etMainCurrency.text.toString().toDoubleOrNull()
            if (amount == null || amount <= 0) {
                etSecondCurrency.setText("0")
            } else {
                lifecycleScope.launch {
                    val rate = supportData.fetchExchangeRate(supportData.selectedFirst, supportData.selectedSecond).toString()
                        .toDoubleOrNull()
                    if (rate != null) {
                        val converted = amount * rate
                        val result = String.format("%.2f", converted)
                        etSecondCurrency.setText(result)
                    } else {
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
                        etSecondCurrency.setText("0")
                    }
                }
            }
        }
    }

    private fun setImageForCurrencies() {
        with(binding) {
            val index1 = supportData.currencies.indexOf(supportData.selectedFirst)
            ivMain.setImageResource(supportData.imageList[index1])

            val index2 = supportData.currencies.indexOf(supportData.selectedSecond)
            ivSecond.setImageResource(supportData.imageList[index2])
        }
    }
}