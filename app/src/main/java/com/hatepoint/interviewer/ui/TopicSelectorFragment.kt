package com.hatepoint.interviewer.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.hatepoint.interviewer.R
import com.hatepoint.interviewer.databinding.FragmentTopicSelectorBinding
import kotlinx.coroutines.launch

class TopicSelectorFragment : Fragment() {
    private val vm: MainViewModel by viewModels(ownerProducer = {requireActivity()})
    private var binding: FragmentTopicSelectorBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTopicSelectorBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fun fragmentTransaction() {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, InterviewFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }
        binding?.recyclerView?.apply {
            adapter = TopicAdapter(onTopicClick = {vm.clickedTopic(it); fragmentTransaction()})
            layoutManager = LinearLayoutManager(context)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.uiState.collect {
                    when (it) {
                        is MainState.Success -> {
                            binding?.textView?.text = "Choose topic:"
                            (binding?.recyclerView?.adapter as TopicAdapter).items = it.topics
                        }
                        is MainState.Error -> {
                            binding?.textView?.text = "Error: ${it.error}"
                        }
                        is MainState.Loading -> {
                            binding?.textView?.text = "Loading..."
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}