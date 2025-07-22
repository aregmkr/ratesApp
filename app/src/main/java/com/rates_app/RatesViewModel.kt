package com.rates_app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RatesViewModel : ViewModel() {
    private val _rates = MutableLiveData<List<String>>()
    val rates: LiveData<List<String>> = _rates



}