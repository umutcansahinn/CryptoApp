package com.umutcansahin.cryptoapp3.service

import com.umutcansahin.cryptoapp3.model.Crypto
import retrofit2.Response
import retrofit2.http.GET

interface CryptoAPI {

    // https://raw.githubusercontent.com/atilsamancioglu/IA32-CryptoComposeData/main/cryptolist.json

    @GET("atilsamancioglu/IA32-CryptoComposeData/main/cryptolist.json")
    suspend fun getCryptos(): Response<List<Crypto>>
}