package com.piyal.roomretrofitmvvm

import android.app.Application
import com.piyal.roomretrofitmvvm.api.QuoteService
import com.piyal.roomretrofitmvvm.api.RetrofitHelper
import com.piyal.roomretrofitmvvm.db.QuoteDatabase
import com.piyal.roomretrofitmvvm.repository.QuotesRepository

class QuoteApplication: Application() {

    lateinit var quotesRepository: QuotesRepository

    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {

        val quoteService = RetrofitHelper.getInstance().create(QuoteService::class.java)
        val database = QuoteDatabase.getDatabase(applicationContext)
        val quotesRepository = QuotesRepository(quoteService,database,applicationContext)
    }
}