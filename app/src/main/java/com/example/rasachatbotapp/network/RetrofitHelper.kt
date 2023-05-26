package com.example.rasachatbotapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    val baseUrl = "https://94fc-27-79-118-149.ap.ngrok.io/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            // Add converter factory to
            // Convert JSON object to Java object
            .build()
    }
}