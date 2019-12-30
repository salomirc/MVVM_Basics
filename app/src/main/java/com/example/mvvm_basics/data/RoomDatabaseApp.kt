package com.example.mvvm_basics.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = [Quote::class],version = 1, exportSchema = false)
abstract class RoomDatabaseApp : RoomDatabase() {

    abstract fun quoteDao(): QuoteDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var instance: RoomDatabaseApp? = null

        fun getInstance(context: Context): RoomDatabaseApp {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    RoomDatabaseApp::class.java,
                    "app_database"
                ).build().also { instance = it }
            }
        }
    }
}