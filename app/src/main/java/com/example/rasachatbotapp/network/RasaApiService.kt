package com.example.rasachatbotapp.network

import androidx.compose.runtime.snapshots.SnapshotStateList
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST


private val retrofit = Retrofit.Builder()
    .baseUrl("https://136a-2402-800-623c-110a-5d64-5b2b-4c37-5b53.ap.ngrok.io")
    .client(OkHttpClient())
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val rasaApiService = retrofit.create(RasaApiService::class.java)

interface RasaApiService {
    @POST("webhooks/rest/webhook")
    suspend fun sendMessage(
        @Body message: Message
    ): Call<ArrayList<Message>>
//    ): Response<SnapshotStateList<Message>>
}