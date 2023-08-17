package com.hatepoint.interviewer.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.hatepoint.interviewer.databinding.FragmentInterviewBinding
import com.hatepoint.interviewer.databinding.FragmentTopicSelectorBinding
import com.hatepoint.interviewer.models.Answer
import com.hatepoint.interviewer.models.Question
import kotlinx.coroutines.launch

class InterviewFragment : Fragment() {
    private val vm: MainViewModel by viewModels(ownerProducer = {requireActivity()})
    private var binding: FragmentInterviewBinding? = null
    private var questionList: List<Question> = listOf()
    private var questionIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInterviewBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.questionTextView?.text = vm.selectedTopic

        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.getQuestions().collect {
                    when (it) {
                        is QuestionState.Success -> {
                            questionList = it.questions
                            binding?.questionTextView?.text = questionList[0].questionText
                        }
                        is QuestionState.Error -> {
                            binding?.questionTextView?.text = "Error loading question: ${it.error}"
                        }
                        is QuestionState.Loading -> {
                            binding?.questionTextView?.text = "Loading question..."
                        }
                    }
                }
            }
        }

        binding?.sendButton?.setOnClickListener {
            if (questionIndex < questionList.size - 1) {
                var answer = binding?.answerEditText?.text
                println("Answer: $answer")
                viewLifecycleOwner.lifecycleScope.launch {
                    lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        vm.gradeAnswer(
                            Answer(
                                questionList[questionIndex].questionText,
                                answer.toString()
                            )
                        )
                    }
                }
                binding?.answerEditText?.text?.clear()
                questionIndex++
                binding?.questionTextView?.text = questionList[questionIndex].questionText
            } else {
                binding?.questionTextView?.text = vm.getSummary()
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}