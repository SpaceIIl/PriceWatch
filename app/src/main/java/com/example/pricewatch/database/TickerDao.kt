package com.example.pricewatch.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TickerDao {
    @Insert
    suspend fun insertTicker(ticker: Ticker)

    @Delete
    suspend fun deleteTicker(ticker: Ticker)

    @Query("SELECT * FROM ticker")
    fun getAllTickers(): Flow<List<Ticker>>

    @Query("DELETE FROM ticker WHERE symbol=:symbol")
    suspend fun deleteSymbol(symbol: String)
}

