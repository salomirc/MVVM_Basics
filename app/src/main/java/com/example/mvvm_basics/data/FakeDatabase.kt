package com.example.mvvm_basics.data

class FakeDatabase private constructor(){

    val quoteDao = FakeQuoteDao()

    companion object {
        @Volatile private var instance: FakeDatabase? = null

        fun getInstance(): FakeDatabase {
            return instance ?: synchronized(this) {
                instance ?: FakeDatabase().also { instance = it }
            }
        }
    }
}