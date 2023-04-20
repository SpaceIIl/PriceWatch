package com.example.pricewatch.watchlist

import com.example.pricewatch.database.Ticker
import com.example.pricewatch.model.CryptoPriceItem

sealed class WatchlistScreenState {
    data class Success(val data: List<CryptoPriceItem>, val tickers: List<Ticker>) : WatchlistScreenState()
    data class Error(val throwable: Throwable) : WatchlistScreenState()
    object Loading : WatchlistScreenState()
}
