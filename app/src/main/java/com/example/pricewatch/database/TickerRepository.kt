package com.example.pricewatch.database

import androidx.room.Room
import com.example.pricewatch.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

object TickerRepository {
    private val database: AppDatabase = Room.databaseBuilder(
        App.instance.applicationContext,
        AppDatabase::class.java,
        "app_database"
    ).build()

    private val tickerDao = database.tickerDao()

    suspend fun insertTicker(symbol: String) {
        withContext(Dispatchers.IO) {
            val ticker = Ticker(symbol = symbol)
            tickerDao.insertTicker(ticker)
        }
    }

    suspend fun deleteTicker(ticker: Ticker) {
        withContext(Dispatchers.IO) {
            tickerDao.deleteTicker(ticker)
        }
    }

    fun getAllTickers(): Flow<List<Ticker>> {
        return tickerDao.getAllTickers().flowOn(Dispatchers.IO)
    }

    suspend fun deleteSymbol(symbol: String) {
        withContext(Dispatchers.IO){
            tickerDao.deleteSymbol(symbol)
        }
    }
}

