package com.example.wp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.wp.domain.repository.MaterialRepository

class MaterialViewModel (private val repository: MaterialRepository): ViewModel() {

//    private val _orderLoad = MutableLiveData<Load<OrderResult>>()
//    val orderLoad = _orderLoad as LiveData<Load<OrderResult>>
//
//    fun postOrder(orderResult: OrderResult) = viewModelScope.launch {
//        _orderLoad.value = Load.Loading
//        val order = repository.postOrder(orderResult)
//        _orderLoad.value = order
//    }


}