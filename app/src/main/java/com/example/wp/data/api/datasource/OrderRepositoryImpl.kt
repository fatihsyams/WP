package com.example.wp.data.api.datasource

import com.example.wp.data.api.model.request.RequestUpdateOrderApi
import com.example.wp.data.api.service.OrderService
import com.example.wp.data.mapper.OrderMapper
import com.example.wp.domain.kategoriorder.KategoriOrder
import com.example.wp.domain.order.Customer
import com.example.wp.domain.order.OrderResult
import com.example.wp.domain.order.Wallet
import com.example.wp.domain.payment.Payment
import com.example.wp.domain.repository.OrderRepository
import com.example.wp.utils.Load
import com.example.wp.utils.handleApiSuccess

class OrderRepositoryImpl(private val service: OrderService) : OrderRepository {
    override suspend fun postOrder(order: OrderResult): Load<OrderResult> {
        return try {
            val request = OrderMapper.mapToRequestOrderApi(order)
            val response = service.postOrder(request)
            if (response.isSuccessful) {
                response.body()?.let { response ->
                    OrderMapper.map(response)
                } ?: Load.Fail(Throwable(response.message()))
            } else {
                Load.Fail(Throwable(response.message()))
            }
        } catch (e: Exception) {
            Load.Fail(e)
        }
    }

    override suspend fun editOrder(
        order: OrderResult,
        orderId: Int
    ): Load<Boolean> {
        return try {
            val request = OrderMapper.mapToRequestOrderApi(order)
            val response = service.editOrder(orderId, request)
            if (response.isSuccessful) {
                handleApiSuccess(true)
            } else {
                Load.Fail(Throwable(response.message()))
            }
        } catch (e: Exception) {
            Load.Fail(e)
        }
    }

    override suspend fun getOrders(): Load<List<OrderResult>> {
        return try {
            val response = service.getOrders()
            if (response.isSuccessful) {
                response.body()?.let { response ->
                    OrderMapper.mapGetOrders(response)
                } ?: Load.Fail(Throwable(response.message()))
            } else {
                Load.Fail(Throwable(response.message()))
            }
        } catch (e: Exception) {
            Load.Fail(e)
        }
    }

    override suspend fun getKategoriOrder(): Load<List<KategoriOrder>> {
        return try {
            val response = service.getKategoriOrder()
            if (response.isSuccessful) {
                response.body()?.let { response ->
                    OrderMapper.mapGetOrderKategori(response)
                } ?: Load.Fail(Throwable(response.message()))
            } else {
                Load.Fail(Throwable(response.message()))
            }
        } catch (e: Exception) {
            Load.Fail(e)
        }
    }

    override suspend fun getListPembayaran(): Load<List<Payment>> {
        return try {
            val response = service.getListPembayaran()
            if (response.isSuccessful) {
                response.body()?.let { response ->
                    OrderMapper.mapGetListPembayaran(response)
                } ?: Load.Fail(Throwable(response.message()))
            } else {
                Load.Fail(Throwable(response.message()))
            }
        } catch (e: Exception) {
            Load.Fail(e)
        }
    }

    override suspend fun getListKas(): Load<List<Wallet>> {
        return try {
            val response = service.getListKas()
            if (response.isSuccessful) {
                response.body()?.let { response ->
                    OrderMapper.mapGetListKas(response)
                } ?: Load.Fail(Throwable(response.message()))
            } else {
                Load.Fail(Throwable(response.message()))
            }
        } catch (e: Exception) {
            Load.Fail(e)
        }
    }

    override suspend fun getListCustomer(): Load<List<Customer>> {
        return try {
            val response = service.getPelanggan()
            if (response.isSuccessful) {
                response.body()?.let { response ->
                    OrderMapper.mapGetListCustomer(response)
                } ?: Load.Fail(Throwable(response.message()))
            } else {
                Load.Fail(Throwable(response.message()))
            }
        } catch (e: Exception) {
            Load.Fail(e)
        }
    }

    override suspend fun updateOrder(orderId: String, status: String): Load<Boolean> {
        return try {
            val response =
                service.postUpdateOrderStatus(orderId.toInt(), RequestUpdateOrderApi(status))
            if (response.isSuccessful) {
                handleApiSuccess(true)
            } else {
                Load.Fail(Throwable(response.message()))
            }
        } catch (e: Exception) {
            Load.Fail(e)
        }
    }
}