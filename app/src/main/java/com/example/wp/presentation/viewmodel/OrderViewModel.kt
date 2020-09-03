package com.example.wp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wp.domain.order.OrderResult
import com.example.wp.domain.repository.OrderRepository
import com.example.wp.utils.Load
import kotlinx.coroutines.launch

class OrderViewModel (private val repository: OrderRepository): ViewModel() {

    private val _orderLoad = MutableLiveData<Load<OrderResult>>()
    val orderLoad = _orderLoad as LiveData<Load<OrderResult>>

    init {
        _orderLoad.value = Load.Loading
    }

    fun postOrder(orderResult: OrderResult) = viewModelScope.launch {
        val order = repository.postOrder(orderResult)
        _orderLoad.value = order
    }


}