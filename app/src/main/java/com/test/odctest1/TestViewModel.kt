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

    //liveData ->
    /*
    * Hot Stream - Live Cycle awareness
    * can't survive against configuration change
    * behavior subscriber (send last emitted data to new subscribers)
    */
    private val _testLiveData=MutableLiveData<String>()
    val testLiveData:LiveData<String>
        get() = _testLiveData

    //StateFlow ->
    /*
    * Hot Stream - can be modified to Live Cycle awareness
    * can't survive against configuration change
    * behavior subscriber (send last emitted data to new subscribers)
    * check if subscriber has the last value or not each time
    */

    private val _testStateFlow= MutableStateFlow<String>("State flow")
    val testStatedFlow:StateFlow<String>
        get() = _testStateFlow

    //SharedFlow ->
    /*
    * Hot Stream - can be modified to Live Cycle awareness
    * can't survive against configuration change
    * publisher subscriber (doesn't send data to new subscribers)
    */

    private val _testSharedFlow= MutableSharedFlow<String>()
    val testSharedFlow: SharedFlow<String>
        get() = _testSharedFlow


    fun changeTextWithLiveData(value:String){
        viewModelScope.launch {
            _testLiveData.value=value
        }
    }

    //Flow ->
    /*
    * Cold Stream - can be modified to Live Cycle awareness
    * can't survive against configuration change
    * publisher subscriber (doesn't send data to new subscribers)
    */

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