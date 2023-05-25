package com.example.rasachatbotapp

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rasachatbotapp.network.Message
import com.example.rasachatbotapp.network.MessageSender
import com.example.rasachatbotapp.network.QuotesApi
import com.example.rasachatbotapp.network.RetrofitHelper
import com.example.rasachatbotapp.network.rasaApiService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MainActivityViewModel : ViewModel() {
    private val message_list: MutableList<Message> = mutableStateListOf()
    val messages: List<Message> = message_list

    private val connectivityState = mutableStateOf(true)
    val _connectivityState = connectivityState

    val username = "Dishant"

    fun addMessage(message: Message) {
        message_list.add(0, message)
    }

    fun sendMessagetoRasa(message: Message) {
        addMessage(message)

        val quotesApi = RetrofitHelper.getInstance().create(QuotesApi::class.java)
        // Launching a new coroutine
        /*GlobalScope.launch {
            val result = quotesApi.getQuotes()
            if (result != null)
            // Checking the results
                Log.d("nhtv: ", result.body().toString())
        }*/

        /*val okHttpClient = OkHttpClient()
        val retrofit = Retrofit.Builder().baseUrl("https://8c86-2402-800-623c-110a-5d64-5b2b-4c37-5b53.ap.ngrok.io/webhooks/rest/")
            .client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build()
        val messageSender = retrofit.create(MessageSender::class.java)
        val response = messageSender.messageSender(message)
        response.enqueue(object : Callback<ArrayList<Message>> {
            override fun onResponse(
                call: Call<ArrayList<Message>>,
                response: Response<ArrayList<Message>>
            ) {
                if (response.code() == 200 && response.body() != null) {
                    response.body()!!.forEach {
                        it.time = Calendar.getInstance().time
                        addMessage(it)
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<Message>>, t: Throwable) {
                addMessage(
                    Message(
                        "Error occurred!",
                        "bot",
                        Calendar.getInstance().time
                    )
                )
            }
        })*/

        viewModelScope.launch {
            val result = quotesApi.getQuotes()
            if (result != null)
            // Checking the results
                Log.d("nhtv: ", result.body().toString())

            /*val response = rasaApiService.sendMessage(message)
            Log.e("Message", response.toString())

            response.enqueue(object : Callback<ArrayList<Message>> {
                override fun onResponse(
                    call: Call<ArrayList<Message>>,
                    response: Response<ArrayList<Message>>
                ) {

                    if (response.code() == 200 && response.body() != null) {
                        response.body()!!.forEach {
                            it.time = Calendar.getInstance().time
                            addMessage(it)
                        }
                    } else {

                    }
                }

                override fun onFailure(call: Call<ArrayList<Message>>, t: Throwable) {
                    addMessage(
                        Message(
                            "Error occurred!",
                            "bot",
                            Calendar.getInstance().time
                        )
                    )
                }
            })*/
        }
    }
}