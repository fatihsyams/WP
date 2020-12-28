package com.example.wp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wp.domain.order.OrderResult
import com.example.wp.domain.repository.OrderRepository
import com.example.wp.utils.Load
import kotlinx.coroutines.launch

class OrderViewModel(private val repository: OrderRepository) : ViewModel() {

    private val _orderLoad = MutableLiveData<Load<OrderResult>>()
    val orderLoad = _orderLoad as LiveData<Load<OrderResult>>

    private val _updateOrderStatus = MutableLiveData<Load<Boolean>>()
    val updateOrderStatus = _updateOrderStatus as LiveData<Load<Boolean>>

    private val _ordersLoad = MutableLiveData<Load<List<OrderResult>>>()
    val ordersLoad = _ordersLoad as LiveData<Load<List<OrderResult>>>

    fun postOrder(orderResult: OrderResult) = viewModelScope.launch {
        _orderLoad.value = Load.Loading
        val order = repository.postOrder(orderResult)
        _orderLoad.value = order
    }

    fun updateOrderStatus(orderId: String, status: String) = viewModelScope.launch {
        _updateOrderStatus.value = Load.Loading
        val order = repository.updateOrder(orderId, status)
        _updateOrderStatus.value = order
    }

    fun getOrders() = viewModelScope.launch {
        _ordersLoad.value = Load.Loading
        val order = repository.getOrders()
        _ordersLoad.value = order
    }
}