package com.hatepoint.interviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.hatepoint.interviewer.databinding.ActivityMainBinding
import com.hatepoint.interviewer.ui.InterviewFragment
import com.hatepoint.interviewer.ui.MainViewModel
import com.hatepoint.interviewer.ui.TopicSelectorFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val vm by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, TopicSelectorFragment())
            .commit()

    }
}