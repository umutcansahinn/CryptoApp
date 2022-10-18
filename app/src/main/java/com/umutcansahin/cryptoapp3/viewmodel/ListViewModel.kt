package com.umutcansahin.cryptoapp3.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

import com.umutcansahin.cryptoapp3.model.Crypto
import com.umutcansahin.cryptoapp3.service.CryptoAPIService
import com.umutcansahin.cryptoapp3.service.CryptoDatabase
import com.umutcansahin.cryptoapp3.util.CustomSharedPreferences
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ListViewModel(application: Application) : AndroidViewModel(application),CoroutineScope{

    private val cryptoAPIService = CryptoAPIService()

    private val job = Job()
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("error: ${throwable.localizedMessage}")
    }

    private val customPreferences = CustomSharedPreferences(getApplication())
    private val refreshTime = 10 * 60 * 1000 * 1000 * 1000L

    val cryptos = MutableLiveData<List<Crypto>>()
    val cryptoError = MutableLiveData<Boolean>()
    val cryptoLoading = MutableLiveData<Boolean>()

    fun getData() {
        val updateTime = customPreferences.getTime()
        if (updateTime != null && updateTime != 0L && ((System.nanoTime() - updateTime) < refreshTime)) {
            getDataFromRoom()
        }else {
            getDataFromAPI()
        }
    }

    private fun getDataFromAPI() {
        cryptoLoading.value = true

         CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val responce = cryptoAPIService.getData()

            withContext(Dispatchers.Main) {
                if (responce.isSuccessful) {
                    responce.body()?.let {
                        Toast.makeText(getApplication(), "The data is coming from API", Toast.LENGTH_SHORT).show()
                        saveDataToRoom(it)
                    }
                }else {
                    cryptoLoading.value = false
                    cryptoError.value = true
                }
            }
        }
    }

    private fun saveDataToRoom(cryptoList: List<Crypto>) {
        CoroutineScope(Dispatchers.IO).launch {
            val dao = CryptoDatabase(getApplication()).cryptoDao()
            dao.deleteAllCryptos()
            val listLong = dao.insertAll(*cryptoList.toTypedArray())

            withContext(Dispatchers.Main) {
                var i = 0
                while (i < cryptoList.size) {
                    cryptoList[i].id = listLong[i].toInt()
                    i = i + 1

                    showCrypto(cryptoList)
            }

            }

        }
        customPreferences.saveTime(System.nanoTime())
    }

    private fun getDataFromRoom() {

        cryptoLoading.value = true
        launch {
            val dao = CryptoDatabase(getApplication()).cryptoDao()
            val allCrypto = dao.getAllCryptos()
            showCrypto(allCrypto)
        }
        Toast.makeText(getApplication(), "The data is coming from Room", Toast.LENGTH_SHORT).show()
    }

    private fun showCrypto(cryptoList: List<Crypto>) {
        cryptos.value = cryptoList
        cryptoLoading.value = false
        cryptoError.value = false
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}