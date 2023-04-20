package com.example.pricewatch.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.math.BigDecimal

@JsonClass(generateAdapter = true)
data class CryptoPriceItem(
    @Json(name = "closeTime")
    val closeTime: Long,
    @Json(name = "count")
    val count: Int,
    @Json(name = "firstId")
    val firstId: Long,
    @Json(name = "highPrice")
    val highPrice: String,
    @Json(name = "lastId")
    val lastId: Long,
    @Json(name = "lastPrice")
    val lastPrice: BigDecimal,
    @Json(name = "lowPrice")
    val lowPrice: BigDecimal,
    @Json(name = "openPrice")
    val openPrice: String,
    @Json(name = "openTime")
    val openTime: Long,
    @Json(name = "priceChange")
    val priceChange: BigDecimal,
    @Json(name = "priceChangePercent")
    val priceChangePercent: BigDecimal,
    @Json(name = "quoteVolume")
    val quoteVolume: BigDecimal,
    @Json(name = "symbol")
    val symbol: String,
    @Json(name = "volume")
    val volume: BigDecimal,
    @Json(name = "weightedAvgPrice")
    val weightedAvgPrice: String
)