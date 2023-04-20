package com.example.pricewatch

import com.example.pricewatch.database.TickerRepository
import com.example.pricewatch.model.CryptoPrice
import com.example.pricewatch.model.CryptoPriceItem
import com.squareup.moshi.Moshi
import com.squareup.moshi.addAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object MarketDataSource {
    private val marketService by lazy {
        val moshi = Moshi.Builder()
            .add(BigDecimalAdapter)
            .addLast(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.binance.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        retrofit.create(MarketService::class.java)
    }

    suspend fun getCryptoPrice(symbols: String): Response<List<CryptoPriceItem>> {
        return marketService.getCryptoPrice(symbols)
    }

    suspend fun getCryptoPriceList(symbols: List<String>): List<CryptoPriceItem>{
        return symbols.mapNotNull {
            try {
                val response =marketService.getCryptoPriceSingle(symbol = it.uppercase()).body()

                response
            }catch (exception: Exception){
                // ignored
                null
            }
        }
    }

    fun getCryptoPriceListFlow(): Flow<List<CryptoPriceItem>> {
        return TickerRepository.getAllTickers()
            .map { tickers ->
                tickers.map { it.symbol }.mapNotNull {
                    try {
                        val response = marketService.getCryptoPriceSingle(symbol = it.uppercase()).body()

                        if(response == null){
                            TickerRepository.deleteSymbol(it)
                        }
                        response
                    }catch (exception: Exception){
                        // ignored
                        null
                    }
                }
            }
    }
}