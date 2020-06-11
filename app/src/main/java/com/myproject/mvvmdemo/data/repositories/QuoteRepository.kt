package com.myproject.mvvmdemo.data.repositories

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.myproject.mvvmdemo.data.db.AppDatabase
import com.myproject.mvvmdemo.data.entities.Quote
import com.myproject.mvvmdemo.data.network.MyApi
import com.myproject.mvvmdemo.data.network.SafeApiRequest
import com.myproject.mvvmdemo.data.preferences.PreferenceProvider
import com.myproject.mvvmdemo.util.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

const val MINIMUM_INTERVAL = 6
@SuppressLint("NewApi")
class QuoteRepository(
    private val api: MyApi,
    private val db: AppDatabase,
    private val preference:PreferenceProvider)
    : SafeApiRequest() {

    private val quotes = MutableLiveData<List<Quote>>()

    init {

        quotes.observeForever{
            saveQuotes(it)}

    }


    suspend fun getQuotes(): LiveData<List<Quote>>{

        return withContext(Dispatchers.IO){
            fetchQuotes()
            db.getQuoteDao().getQuotes()
        }


    }



    private suspend fun fetchQuotes() {

        /* here we are not fetching data from server every time. we are fetching it after every 6 hours.
        so that's why we are checking time stamp every time
         */

        val lastSavedAt = preference.getLastSavedAt()
        if(lastSavedAt == null || isFetchNeeded(LocalDateTime.parse(lastSavedAt))) {  // lastSavedAt == null for checking we are fetching data for the first time or it or already fetched before
            val response = apiRequest { api.getQuotes() }
            quotes.postValue(response.quotes)
        }
    }


    private fun isFetchNeeded(savedAt: LocalDateTime): Boolean{
        return ChronoUnit.HOURS.between(savedAt,LocalDateTime.now()) > MINIMUM_INTERVAL // checking 6 hours interval
    }




    private fun saveQuotes(quotes: List<Quote>){
        Coroutines.io {
            preference.saveLastSavedTime(LocalDateTime.now().toString())// before saving the data to local we save time stamp here
            db.getQuoteDao().saveAllQuotes(quotes)
        }
    }

}