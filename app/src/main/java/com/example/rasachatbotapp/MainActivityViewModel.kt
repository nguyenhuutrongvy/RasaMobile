package com.example.rasachatbotapp

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.rasachatbotapp.network.Message
import com.example.rasachatbotapp.network.QuotesApi
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MainActivityViewModel : ViewModel() {
    private val message_list: MutableList<Message> = mutableStateListOf()
    val messages: List<Message> = message_list

    private val connectivityState = mutableStateOf(true)
    val _connectivityState = connectivityState

    val username = "NHTV"

    fun addMessage(message: Message) {
        message_list.add(0, message)
    }

    fun sendMessagetoRasa(message: Message) {
        addMessage(message)
        // Create Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://ba98-2402-800-623c-110a-3420-2949-9948-fbda.ap.ngrok.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create Service
        val service = retrofit.create(QuotesApi::class.java)

        // Create JSON using JSONObject
        val jsonObject = JSONObject()
        jsonObject.put("sender", "nhtv")
        jsonObject.put("message", message.text)
//        jsonObject.put("age", "23")

        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()

        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
            // Do the POST request and get response
            val response = service.createEmployee(requestBody)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    // Convert raw JSON to pretty JSON using GSON library
                    /*val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string() // About this thread blocking annotation : https://github.com/square/retrofit/issues/3255
                        )
                    )*/

//                    val gson = Gson()
                    response.body()!!.forEach {
                        /*val mess = gson.fromJson(it.toString(), Message::class.java)
                        mess.time = Calendar.getInstance().time*/
                        it.time = Calendar.getInstance().time
                        addMessage(it)
                    }

//                    Log.d("Pretty Printed JSON :", prettyJson)

                } else {

                    addMessage(
                        Message(
                            "${response.code()} error occurred",
                            "bot",
                            Calendar.getInstance().time
                        )
                    )

                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }
        }

        /*addMessage(message)
        viewModelScope.launch {
            val response = rasaApiService.sendMessage(message)
            Log.e("Message", response.toString())
            if (response.code() == 200 && response.body() != null) {
                response.body()!!.forEach {
                    it.time = Calendar.getInstance().time
                    addMessage(it)
                }
            } else {
                addMessage(
                    Message(
                        "${response.code()} error occurred",
                        "bot",
                        Calendar.getInstance().time
                    )
                )
            }
        }*/
    }
}