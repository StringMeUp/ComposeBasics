package com.sr.composebasics

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private var _billAmount = MutableLiveData("")
    val billAmount = _billAmount

    private var _splitCount = mutableStateOf(1)
    var splitCount = _splitCount

    private var _percentage = mutableStateOf(0F)
    val percentage = _percentage

    private var _tip = mutableStateOf(0.0)
    val tip = _tip

    fun setTotal(text: String) {
        viewModelScope.launch {
            _billAmount.value = text
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
        val bill = billAmount.value?.toIntOrNull()
        val percentage = percentage.value.toDouble()
        if (bill != null && percentage > 0.0) {
            _tip.value =
                if (splitCount.value > 1) (bill * percentage / 100) / splitCount.value
                else ((percentage / 100) * bill)
        } else {
            tip.value = 0.0
        }
    }
}