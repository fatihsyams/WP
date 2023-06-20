package com.example.wp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wp.data.api.model.response.ResponseListPembayaran
import com.example.wp.domain.kategoriorder.KategoriOrder
import com.example.wp.domain.order.Customer
import com.example.wp.domain.order.OrderResult
import com.example.wp.domain.order.Wallet
import com.example.wp.domain.payment.Payment
import com.example.wp.domain.repository.OrderRepository
import com.example.wp.utils.Load
import kotlinx.coroutines.launch

class OrderViewModel(private val repository: OrderRepository) : ViewModel() {

    private val _orderLoad = MutableLiveData<Load<OrderResult>>()
    val orderLoad = _orderLoad as LiveData<Load<OrderResult>>

    private val _updateOrderStatus = MutableLiveData<Load<OrderResult>>()
    val updateOrderStatus = _updateOrderStatus as LiveData<Load<OrderResult>>

    private val _ordersLoad = MutableLiveData<Load<List<OrderResult>>>()
    val ordersLoad = _ordersLoad as LiveData<Load<List<OrderResult>>>

    private val _editOrderLoad = MutableLiveData<Load<Boolean>>()
    val editOrderLoad = _editOrderLoad as LiveData<Load<Boolean>>

    private val _kategoriOrderLoad = MutableLiveData<Load<List<KategoriOrder>>>()
    val kategoriOrder = _kategoriOrderLoad as LiveData<Load<List<KategoriOrder>>>

    private val _listPembayaranLoad = MutableLiveData<Load<List<Payment>>> ()
    val listPembayaran = _listPembayaranLoad as LiveData<Load<List<Payment>>>

    private val _listKas = MutableLiveData<Load<List<Wallet>>>()
    val listKas = _listKas as LiveData<Load<List<Wallet>>>

    private val _listPelangganLoad = MutableLiveData<Load<List<Customer>>>()
    val listPelangganLoad = _listPelangganLoad as LiveData<Load<List<Customer>>>

    fun postOrder(orderResult: OrderResult) = viewModelScope.launch {
        _orderLoad.value = Load.Loading
        val order = repository.postOrder(orderResult)
        _orderLoad.value = order
    }

    fun editOrder(orderResult: OrderResult) = viewModelScope.launch {
        _editOrderLoad.value = Load.Loading
        val orderId = orderResult.order.id
        val order = repository.editOrder(orderResult,orderId)
        _editOrderLoad.value = order
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

    fun getKategoriOrder() = viewModelScope.launch {
        _kategoriOrderLoad.value = Load.Loading
        val kategoriOrder = repository.getKategoriOrder()
        _kategoriOrderLoad.value = kategoriOrder
    }


    fun getListKas() = viewModelScope.launch {
        _listKas.value = Load.Loading
        val listKas = repository.getListKas()
        _listKas.value = listKas
    }

    fun getListPelanggan() = viewModelScope.launch {
        _listPelangganLoad.value = Load.Loading
        val listPelanggan = repository.getListCustomer()
        _listPelangganLoad.value = listPelanggan
    }



}