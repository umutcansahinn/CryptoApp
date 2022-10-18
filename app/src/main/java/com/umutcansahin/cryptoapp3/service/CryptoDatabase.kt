package com.umutcansahin.cryptoapp3.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.umutcansahin.cryptoapp3.model.Crypto


@Database(entities = arrayOf(Crypto::class), version = 1)
abstract class CryptoDatabase: RoomDatabase() {

    abstract fun cryptoDao(): CryptoDao

    companion object {

        @Volatile private var INSTANCE : CryptoDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(LOCK) {
            INSTANCE ?: makeDatabase(context).also {
                INSTANCE = it
            }
        }


        private fun makeDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            CryptoDatabase :: class.java,
            "cryptodatabase").build()
    }
}
