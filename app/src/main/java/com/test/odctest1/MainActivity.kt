package com.test.odctest1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.test.odctest1.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var  testViewModel:TestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main)
        testViewModel=ViewModelProvider(this).get(TestViewModel::class.java)
        subscribeToObservers(binding)
        binding.btnFlow.setOnClickListener {
            lifecycleScope.launch {
                testViewModel.changeTextWithFlow("flow changed").collect{
                    Log.i("test info","fired F")
                    binding.txtFlow.text=it
                }
            }
        }

        binding.btnLiveData.setOnClickListener {
            testViewModel.changeTextWithLiveData("live data changed")
        }
        binding.btnSharedFlow.setOnClickListener {
            testViewModel.changeTextWithSharedFlow("shared flow changed")
        }
        binding.btnStateFlow.setOnClickListener {
            testViewModel.changeTextWithStateFlow("state flow changed")
        }

    }

    private fun subscribeToObservers(binding: ActivityMainBinding) {
        testViewModel.testLiveData.observe(this){
            Log.i("test info","fired L")
            binding.txtLiveData.text=it
        }

        lifecycleScope.launch {
            testViewModel.testStatedFlow.collect{
            Log.i("test info","fired S")
                binding.txtStateFlow.text=it
            }
        }

        lifecycleScope.launch {
            testViewModel.testSharedFlow.collect{
            Log.i("test info","fired SH")
                binding.txtSharedFlow.text=it
            }
        }
    }
}