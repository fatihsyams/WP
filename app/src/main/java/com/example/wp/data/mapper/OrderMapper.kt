package com.example.wp.data.mapper

import com.example.wp.data.api.model.request.RequestOrderApi
import com.example.wp.data.api.model.response.OrderApi
import com.example.wp.data.api.model.response.ResponseOrder
import com.example.wp.domain.order.Order
import com.example.wp.domain.order.OrderResult
import com.example.wp.utils.Load
import com.example.wp.utils.handleApiSuccess

object OrderMapper {

    fun map(
        response: ResponseOrder
    ): Load<OrderResult> {
        return handleApiSuccess(data = mapResponseOrder(response))
    }

    private fun mapResponseOrder(response: ResponseOrder):OrderResult{
        return OrderResult(
            menu = response.menu?.map { menuApi -> MenuMapper.mapToMenu(menuApi) }.orEmpty(),
            order = mapToOrder(response.order ?: OrderApi())
        )
    }

    private fun mapToOrder(response: OrderApi): Order {
        return Order(
            updatedAt = response.updatedAt.orEmpty(),
            createdAt = response.createdAt.orEmpty(),
            id = response.id ?: 0,
            customerName = response.customerName.orEmpty(),
            information = response.information.orEmpty(),
            orderCategory = response.orderCategory.orEmpty(),
            tableId = response.tableId.orEmpty(),
            totalPayment = response.totalPayment ?: 0
        )
    }

    fun mapToRequestOrderApi(domain:OrderResult):RequestOrderApi{
        return RequestOrderApi(
            customerName = domain.order.customerName,
            information = domain.order.information,
            orderCategory = domain.order.orderCategory,
            tableId = domain.order.tableId,
            menuIds = domain.menu.map { it.id }.joinToString(),
            amounts = domain.menu.map { it.quantity }.joinToString()
        )
    }

}