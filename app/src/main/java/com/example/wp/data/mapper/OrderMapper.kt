package com.example.wp.data.mapper

import com.example.wp.data.api.model.request.RequestOrderApi
import com.example.wp.data.api.model.response.MenuApi
import com.example.wp.data.api.model.response.OrderApi
import com.example.wp.data.api.model.response.ResponseOrders
import com.example.wp.data.api.model.response.ResponsePostOrder
import com.example.wp.domain.order.Order
import com.example.wp.domain.order.OrderResult
import com.example.wp.utils.Load
import com.example.wp.utils.enum.OrderNameTypeEnum
import com.example.wp.utils.enum.OrderTypeEnum
import com.example.wp.utils.handleApiSuccess

object OrderMapper {

    fun map(
        responsePostOrder: ResponsePostOrder
    ): Load<OrderResult> {
        return handleApiSuccess(data = mapResponseOrder(responsePostOrder))
    }

    private fun mapResponseOrder(responsePostOrder: ResponsePostOrder):OrderResult{
        return OrderResult(
            menu = responsePostOrder.menu?.map { menuApi -> MenuMapper.mapToMenu(menuApi) }.orEmpty(),
            order = mapToOrder(responsePostOrder.order ?: OrderApi())
        )
    }

    fun mapGetOrders(
        response: ResponseOrders
    ): Load<List<OrderResult>> {
        return handleApiSuccess(data = response.dataorder?.map { mapToOrderResult(it) }.orEmpty())
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
            totalPayment = response.totalPayment ?: 0.0
        )
    }

    fun mapToRequestOrderApi(domain:OrderResult):RequestOrderApi{
        return RequestOrderApi(
            customerName = domain.order.customerName,
            information = domain.order.information,
            orderCategory = domain.order.orderCategory,
            tableId = domain.order.tableId,
            menuIds = domain.menu.map { it.id }.joinToString(),
            amounts = domain.menu.map { it.quantity }.joinToString(),
            discount = domain.order.discount,
            paymentMethod = domain.paymentMethod
        )
    }

    private fun mapToOrderResult(api:OrderApi):OrderResult{
        return OrderResult(
            order = Order(
                createdAt = api.createdAt.orEmpty(),
                customerName = api.customerName.orEmpty(),
                id = api.id ?: 0,
                information = api.information,
                orderCategory = api.orderCategory.orEmpty(),
                tableId = api.tableId.orEmpty(),
                totalPayment = api.totalPayment?.minus(api.discountOrder?.toInt() ?: 0) ?: 0.0,
                totalPaymentBeforeDiscount = api.totalPayment ?: 0.0,
                updatedAt = api.updatedAt.orEmpty(),
                discount = api.discountOrder?.toIntOrNull() ?: 0
            ),
            menu = api.orderMenuApis?.map { MenuMapper.mapToMenu(it.menu ?: MenuApi(),it.amount) }.orEmpty(),
            paymentMethod = api.pembayaran.orEmpty(),
            type = when(api.orderCategory){
                OrderNameTypeEnum.DINE_IN.type -> OrderTypeEnum.DINE_IN.type
                OrderNameTypeEnum.PRE_ORDER.type -> OrderTypeEnum.PRE_ORDER.type
                OrderNameTypeEnum.TAKE_AWAY.type,OrderNameTypeEnum.TAKE_AWAY_GOFOOD.type,OrderNameTypeEnum.TAKE_AWAY_GRABFOOD.type -> OrderTypeEnum.TAKE_AWAY.type
                else -> OrderTypeEnum.DINE_IN.type
            }
        )
    }

}