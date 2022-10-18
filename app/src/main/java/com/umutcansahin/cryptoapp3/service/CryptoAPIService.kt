package com.umutcansahin.cryptoapp3.service

import com.umutcansahin.cryptoapp3.model.Crypto
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CryptoAPIService {

    // https://raw.githubusercontent.com/atilsamancioglu/IA32-CryptoComposeData/main/cryptolist.json

    private val BASE_URL = "https://raw.githubusercontent.com/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CryptoAPI::class.java)

    suspend fun getData(): Response<List<Crypto>> {
        return api.getCryptos()
    }
}