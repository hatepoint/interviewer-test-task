package com.hatepoint.interviewer.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType

object RetrofitClient {
    private val BASE_URL = "http://10.0.2.2:8080/"

    @OptIn(ExperimentalSerializationApi::class)
    val retrofitClient = retrofit2.Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(InterviewerAPI::class.java)
}