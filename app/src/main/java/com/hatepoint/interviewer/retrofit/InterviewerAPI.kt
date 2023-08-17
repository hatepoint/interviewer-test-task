package com.hatepoint.interviewer.retrofit

import com.hatepoint.interviewer.models.Answer
import com.hatepoint.interviewer.models.Question
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface InterviewerAPI {

    @GET("topics")
    suspend fun getTopics(): Response<List<String>>

    @GET("questions/{topic}")
    suspend fun getQuestions(@Path("topic") topic: String): Response<List<Question>>

    @POST("grade")
    suspend fun gradeQuestion(@Body answer: Answer): Response<Int>
}
