package com.example.rasachatbotapp.network

import retrofit2.Response
import retrofit2.http.POST

interface QuotesApi {
    @POST("webhooks/rest/webhook")
    suspend fun getQuotes(): Response<Message>
}