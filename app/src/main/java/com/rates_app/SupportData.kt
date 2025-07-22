package com.rates_app

class SupportData {
    val apiKey = "api key"
    var imageList: Array<Int> = arrayOf(
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
    val currencies = listOf(
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

    var selectedFirst = currencies[0]
    var selectedSecond = currencies[2]

    var mainCurrencie = "USD"

    suspend fun fetchExchangeRate(fromCurrency: String, toCurrency: String): String? {
        return try {
            val response = RetrofitClient.apiService.getRates(apiKey, fromCurrency)
            if (response.result == "success") {
                response.conversion_rates[toCurrency]?.toString()
            } else {
                null // API error
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null // Network error
        }
    }
}