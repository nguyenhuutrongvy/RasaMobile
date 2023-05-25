package com.example.rasachatbotapp.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface MessageSender {
    @POST("webhook")
    fun messageSender(@Body userMessage: Message): Call<ArrayList<Message>>
}