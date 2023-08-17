package com.hatepoint.interviewer.models

import kotlinx.serialization.Serializable

@Serializable
data class Question(
    val questionID: Int,
    val questionText: String,
    val questionGroup: String,
    val idealAnswer: String
)