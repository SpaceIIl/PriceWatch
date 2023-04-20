package com.example.pricewatch.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Ticker::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tickerDao(): TickerDao
}