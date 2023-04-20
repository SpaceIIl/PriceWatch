package com.example.pricewatch.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ticker")
data class Ticker(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val symbol: String
)

