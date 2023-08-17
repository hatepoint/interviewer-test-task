package com.hatepoint.interviewer.ui

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hatepoint.interviewer.R
import com.hatepoint.interviewer.models.Answer
import com.hatepoint.interviewer.models.Question
import com.hatepoint.interviewer.retrofit.RetrofitClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainViewModel() : ViewModel() {
    private val retrofitClient = RetrofitClient.retrofitClient
    private val _uiState = MutableStateFlow<MainState>(MainState.Loading)
    val uiState: StateFlow<MainState> = _uiState
    private val _questionUiState = MutableStateFlow<QuestionState>(QuestionState.Loading)
    val questionUiState: StateFlow<QuestionState> = _questionUiState
    var selectedTopic = ""
    var gradesMap = mutableMapOf<Answer, Int>()

    init {
        println("VM created")
        viewModelScope.launch {
            _uiState.value = MainState.Loading
            getTopics().collect {
                _uiState.value = it
            }
        }
    }

    private fun getTopics(): Flow<MainState> = flow {
        val response = retrofitClient.getTopics()
        if (response.isSuccessful) {
            emit(MainState.Success(response.body()!!))
        } else {
            emit(MainState.Error(response.message()))
        }
    }

    fun clickedTopic(topic: String) {
        selectedTopic = topic
        println("Clicked topic: $selectedTopic")
    }

    fun getQuestions(): Flow<QuestionState> = flow {
        val response = retrofitClient.getQuestions(selectedTopic)
        if (response.isSuccessful) {
            emit(QuestionState.Success(response.body()!!))
            _questionUiState.value = QuestionState.Success(response.body()!!)
        } else {
            emit(QuestionState.Error(response.message()))
            _questionUiState.value = QuestionState.Error(response.message())
        }
    }

    suspend fun gradeAnswer(answer: Answer): String {
        val request = retrofitClient.gradeQuestion(answer)
        if (request.isSuccessful) {
            println("Response: ${request.body()!!}")
            gradesMap[answer] = request.body()!!
        } else {
            println("Error")
        }
        return request.body().toString()
    }

    fun getSummary(): String {
        val score = gradesMap.values.sum()/gradesMap.size
        return "Average score: $score"
    }

}

sealed class MainState {
    object Loading : MainState()
    class Success(val topics: List<String>) : MainState()
    class Error(val error: String) : MainState()
}

sealed class QuestionState {
    object Loading : QuestionState()
    class Success(val questions: List<Question>) : QuestionState()
    class Error(val error: String) : QuestionState()
}