package com.example.pricewatch

import com.example.pricewatch.model.CryptoPrice
import com.example.pricewatch.model.CryptoPriceItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/*interface MarketService {
    @GET("api/v3/ticker?symbols=[{symbol}]") //ticker format: "BTCUSDT","BNBBTC"
    suspend fun getCryptoPrice(
        @Query("symbol") symbol: String,
    ): Response<List<CryptoPriceItem>>
}*/

interface MarketService {
    @GET("api/v3/ticker")
    suspend fun getCryptoPrice(
        @Query("symbols") symbols: String = "",
        @Query("symbol") symbol: String = "",
    ): Response<List<CryptoPriceItem>>

    @GET("api/v3/ticker")
    suspend fun getCryptoPriceSingle(
        @Query("symbol") symbol: String = "",
    ): Response<CryptoPriceItem>
}
