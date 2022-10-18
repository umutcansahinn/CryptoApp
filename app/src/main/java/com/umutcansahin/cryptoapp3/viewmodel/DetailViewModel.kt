package com.umutcansahin.cryptoapp3.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.umutcansahin.cryptoapp3.model.Crypto
import com.umutcansahin.cryptoapp3.service.CryptoDatabase
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class DetailViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    val crypto = MutableLiveData<Crypto>()

    private val job = Job()

    fun getCrypto(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val dao = CryptoDatabase(getApplication()).cryptoDao()
            val getCrypto = dao.getCrypto(id)
            withContext(Dispatchers.Main) {
                crypto.value = getCrypto
            }

        }

    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
}