package com.test.odctest1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class TestViewModel:ViewModel() {
    private val _testLiveData=MutableLiveData<String>()
    val testLiveData:LiveData<String>
        get() = _testLiveData

    private val _testStateFlow= MutableStateFlow<String>("State flow")
    val testStatedFlow:StateFlow<String>
        get() = _testStateFlow

    private val _testSharedFlow= MutableSharedFlow<String>()
    val testSharedFlow: SharedFlow<String>
        get() = _testSharedFlow


    fun changeTextWithLiveData(value:String){
        viewModelScope.launch {
            _testLiveData.value=value
        }
    }

    fun changeTextWithFlow(value:String)=
        flow{
                emit(value)
        }

    fun changeTextWithStateFlow(value:String){
        viewModelScope.launch {
            _testStateFlow.value=value
        }
    }

    fun changeTextWithSharedFlow(value:String){
        viewModelScope.launch {
            _testSharedFlow.emit(value)
        }
    }

}