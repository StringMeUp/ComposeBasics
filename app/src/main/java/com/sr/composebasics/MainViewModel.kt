package com.sr.composebasics

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private var _total = MutableLiveData("")
    val total = _total

    private var _splitCount = mutableStateOf(0)
    var splitCount = _splitCount

    private var _percentage = mutableStateOf(0F)
    val percentage = _percentage

    var _tip = mutableStateOf(0.0)
    var tip = _tip

    fun setTotal(text: String) {
        viewModelScope.launch {
            _total.value = text
        }
    }

    fun addSplit() {
        _splitCount.value = _splitCount.value + 1
        calculatePercentage()
    }

    fun subSplit() {
        if (splitCount.value != 0) _splitCount.value = _splitCount.value - 1
        calculatePercentage()
    }

    fun setPercentage(percent: Float) {
        _percentage.value = percent
    }

    fun calculatePercentage() {
        val tValue = total.value?.toIntOrNull()
        val pValue = percentage.value.toDouble()
        if (tValue != null && pValue > 0.0) {
            _tip.value =
                if (splitCount.value > 0) ((pValue / 100) * tValue) / splitCount.value
                else ((pValue / 100) * tValue)
        } else {
            tip.value = 0.0
        }
    }
}