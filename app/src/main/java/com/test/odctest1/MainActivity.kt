package com.test.odctest1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT
import com.google.android.material.snackbar.Snackbar
import com.test.odctest1.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val  testViewModel:TestViewModel by lazy { ViewModelProvider(this).get(TestViewModel::class.java) }
    private val  binding: ActivityMainBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_main) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeToObservers()
        binding.btnFlow.setOnClickListener {
            lifecycleScope.launch {
                testViewModel.changeTextWithFlow().collect{
                    Log.i("test info","fired F")
                    Snackbar.make(binding.btnFlow,it,LENGTH_SHORT).show()
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

    private fun subscribeToObservers() {
        testViewModel.testLiveData.observe(this){
            Log.i("test info","fired L")
            Snackbar.make(binding.btnLiveData,it,LENGTH_SHORT).show()
            binding.txtLiveData.text=it
        }

        lifecycleScope.launch {
            testViewModel.testStatedFlow.collect{
            Log.i("test info","fired S")
                Snackbar.make(binding.btnStateFlow,it,LENGTH_SHORT).show()
                binding.txtStateFlow.text=it
            }
        }

        lifecycleScope.launch {
            testViewModel.testSharedFlow.collect{
                Log.i("test info","fired SH")
                Snackbar.make(binding.btnSharedFlow,it,LENGTH_SHORT).show()
                binding.txtSharedFlow.text=it
            }
        }
    }
}