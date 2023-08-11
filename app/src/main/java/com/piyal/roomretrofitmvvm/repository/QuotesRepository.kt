package com.piyal.roomretrofitmvvm.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.piyal.roomretrofitmvvm.api.QuoteService
import com.piyal.roomretrofitmvvm.db.QuoteDatabase
import com.piyal.roomretrofitmvvm.models.QuoteList
import com.piyal.roomretrofitmvvm.utils.NetworkUtils


class QuotesRepository(
    private val quoteService: QuoteService,
    private val quoteDatabase: QuoteDatabase,
    private val applicationContext: Context
) {

    private val quotesLiveData = MutableLiveData<QuoteList>()

    val quotes: LiveData<QuoteList>
        get() = quotesLiveData

    suspend fun getQuotes(page: Int){

        if (NetworkUtils.isInternetAvailable(applicationContext)){

            val result = quoteService.getQuotes(page)
            if (result?.body()!=null){
                quoteDatabase.quoteDao().addQuotes(result.body()!!.results)
                quotesLiveData.postValue(result.body())
            }
        }else{

            val quotes = quoteDatabase.quoteDao().getQuotes()
            val quotesList = QuoteList(1,1,1,quotes,1,1)
            quotesLiveData.postValue(quotesList)
        }


    }
}