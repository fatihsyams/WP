package com.example.wp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wp.domain.payment.Payment
import com.example.wp.domain.repository.PaymentRepository
import com.example.wp.utils.Load
import kotlinx.coroutines.launch

class PaymentViewModel(val repository: PaymentRepository) : ViewModel() {
    private val _paymentLoad = MutableLiveData<Load<List<Payment>>>()
    val tablesLoad = _paymentLoad as LiveData<Load<List<Payment>>>

    init {
        _paymentLoad.value = Load.Loading
    }

    fun getPayments() = viewModelScope.launch {
        val payments = repository.getPayments()
        _paymentLoad.value = payments
    }

}