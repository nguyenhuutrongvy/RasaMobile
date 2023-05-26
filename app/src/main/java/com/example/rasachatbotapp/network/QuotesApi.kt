package com.example.rasachatbotapp.network

import androidx.compose.runtime.snapshots.SnapshotStateList
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface QuotesApi {
    @POST("webhooks/rest/webhook")
    suspend fun createEmployee(@Body requestBody: RequestBody): Response<SnapshotStateList<Message>>
}