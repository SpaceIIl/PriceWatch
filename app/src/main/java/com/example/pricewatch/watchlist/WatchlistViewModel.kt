package com.example.pricewatch.watchlist

import androidx.lifecycle.*
import com.example.pricewatch.MarketDataSource
import com.example.pricewatch.MarketDataSource.getCryptoPrice
import com.example.pricewatch.MarketDataSource.getCryptoPriceList
import com.example.pricewatch.database.TickerRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class WatchlistViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    //private val ticker = PoolDetailFragmentArgs.fromSavedStateHandle(savedStateHandle).ticker
    //private val symbols = "[\"BTCUSDT\",\"ETHUSDT\",\"BNBUSDT\"]"
    private val _screenState = MutableLiveData<WatchlistScreenState>()
    val screenState: LiveData<WatchlistScreenState> = _screenState

    init {
        loadData()
    }

    private fun loadData() {
        _screenState.value = WatchlistScreenState.Loading

        viewModelScope.launch {
            MarketDataSource.getCryptoPriceListFlow()
                .catch {  _screenState.postValue(WatchlistScreenState.Error(it)) }
                .collect{
                    if (it.isNotEmpty()) {
                        _screenState.postValue(WatchlistScreenState.Success(it, emptyList()))
                    } else {
                        _screenState.postValue(WatchlistScreenState.Error(Throwable("Request failed")))
                    }
                }
        }
    }

    /*    fun insertTicker(symbol: String) {
            viewModelScope.launch {
                TickerRepository.insertTicker(symbol)
            }
        }

        fun deleteTicker(ticker: Ticker) {
            viewModelScope.launch {
                TickerRepository.deleteTicker(ticker)
            }
        }

        fun getAllTickers() {
            viewModelScope.launch {
                val tickers = TickerRepository.getAllTickers()
                // Update UI or LiveData with the list of tickers
            }
        }*/
    fun retryLoadingData() {
        loadData()
    }
}