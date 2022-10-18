package com.umutcansahin.cryptoapp3.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.umutcansahin.cryptoapp3.model.Crypto

@Dao
interface CryptoDao {

    @Insert
    suspend fun insertAll(vararg cryptos: Crypto): List<Long>

    @Query("SELECT * FROM crypto_table")
    suspend fun getAllCryptos(): List<Crypto>

    @Query("SELECT * FROM crypto_table WHERE id = :cryptoId")
    suspend fun getCrypto(cryptoId: Int): Crypto

    @Query("DELETE FROM crypto_table")
    suspend fun deleteAllCryptos()
}