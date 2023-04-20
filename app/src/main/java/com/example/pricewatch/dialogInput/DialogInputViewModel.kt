package com.example.pricewatch.dialogInput

import androidx.lifecycle.*
import com.example.pricewatch.database.TickerRepository
import com.example.pricewatch.database.Ticker
import com.example.pricewatch.watchlist.WatchlistScreenState
import kotlinx.coroutines.launch

class DialogInputViewModel : ViewModel() {

    fun insertTicker(symbol: String) {
        viewModelScope.launch {
            TickerRepository.insertTicker(symbol)
        }
    }
}