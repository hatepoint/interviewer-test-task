package com.hatepoint.interviewer.models
import kotlinx.serialization.Serializable

@Serializable
data class Answer(
    val question: String,
    val answer: String,
    val idealAnswer: String = ""
)
