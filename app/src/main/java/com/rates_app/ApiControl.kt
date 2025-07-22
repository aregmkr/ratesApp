package com.rates_app

import retrofit2.http.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


interface ExchangeApiService {
    @GET("v6/{apiKey}/latest/{fromCurrency}")
    suspend fun getRates(
        @Path("apiKey") apiKey: String,
        @Path("fromCurrency") fromCurrency: String
    ): ExchangeResponse
}


object RetrofitClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://v6.exchangerate-api.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ExchangeApiService = retrofit.create(ExchangeApiService::class.java)
}
