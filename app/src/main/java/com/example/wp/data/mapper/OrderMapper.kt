package com.example.wp.data.mapper

import com.example.wp.data.api.model.request.RequestOrderApi
import com.example.wp.data.api.model.response.*
import com.example.wp.domain.kategoriorder.KategoriOrder
import com.example.wp.domain.order.Customer
import com.example.wp.domain.order.Order
import com.example.wp.domain.order.OrderResult
import com.example.wp.domain.order.Wallet
import com.example.wp.domain.payment.Payment
import com.example.wp.domain.table.Table
import com.example.wp.utils.Load
import com.example.wp.utils.handleApiSuccess

object OrderMapper {

    fun map(
        responsePostOrder: ResponsePostOrder
    ): Load<OrderResult> {
        return handleApiSuccess(data = mapResponseOrder(responsePostOrder))
    }

    private fun mapResponseOrder(responsePostOrder: ResponsePostOrder): OrderResult {
        return OrderResult(
            menu = responsePostOrder.order?.orderMenuApi?.map { menuApi ->
                MenuMapper.mapToMenu(
                    menuApi.menu?.menu ?: MenuApi()
                )
            }.orEmpty(),
            order = mapToOrder(responsePostOrder.order ?: OrderApi())
        )
    }

    fun mapGetOrders(
        response: ResponseOrders
    ): Load<List<OrderResult>> {
        return handleApiSuccess(data = response.dataorder?.map { mapToOrderResult(it) }.orEmpty())
    }

    fun mapUpdateStatusOrder(
        response: ResponseUpdateStatusOrder
    ): Load<OrderResult> {
        return handleApiSuccess(data = response.order?.let { mapToOrderResult(it) } ?: OrderResult())
    }

    private fun mapToOrder(response: OrderApi): Order {
        return Order(
            updatedAt = response.updatedAt.orEmpty(),
            createdAt = response.createdAt.orEmpty(),
            id = response.id ?: 0,
            customerName = response.customer?.customer.orEmpty(),
            information = response.information.orEmpty(),
            orderCategory = KategoriOrder(),
            table = Table(id = response.tableId ?: 0),
            totalPayment = response.totalPayment ?: 0.0
        )
    }

    fun mapToRequestOrderApi(domain: OrderResult): RequestOrderApi {
        return RequestOrderApi(
            customerId = domain.order.customerId,
            information = domain.menu.joinToString { it.additionalInformation },
            orderCategory = domain.order.orderCategory.name,
            tableId = if (domain.order.table.id == 0) null else domain.order.table.id,
            menuIds = domain.menu.map { it.menuPrice.firstOrNull()?.id }.joinToString(),
            amounts = domain.menu.map { it.quantity }.joinToString(),
            discount = domain.order.discount,
            paymentId = domain.paymentMethod.id,
            totalPayment = domain.order.totalPayment,
            walletId = domain.order.wallet.id,
            totalPaymentBeforeDiscount = domain.order.totalPaymentBeforeDiscount
        )
    }

    private fun mapToOrderResult(api: OrderApi): OrderResult {
        return OrderResult(
            order = Order(
                createdAt = api.createdAt.orEmpty(),
                customerId = api.customerId ?: 0,
                customerName = api.customer?.customer.orEmpty(),
                id = api.id ?: 0,
                information = api.information.orEmpty(),
                table = TableMapper.mapToTable(api.table ?: TableApi()),
                totalPayment = api.totalPayment?.minus(api.discountOrder?.toInt() ?: 0) ?: 0.0,
                totalPaymentBeforeDiscount = api.totalPaymentBeforeDiscount ?: 0.0,
                updatedAt = api.updatedAt.orEmpty(),
                discount = api.discountOrder?.toIntOrNull() ?: 0,
                wallet = mapToListKas(api.wallet),
                orderCategory = mapToKategoriOrder(api.orderMenuApi?.firstOrNull()?.menu?.categoryOrder)
            ),
            menu = api.orderMenuApi?.map {
                MenuMapper.mapToMenu(
                    it.menu?.menu ?: MenuApi(),
                    it.amount,
                    it.additionalInformation.orEmpty(),
                    listOf(it.menu ?: MenuPriceApi())
                )
            }.orEmpty(),
            paymentMethod = mapToListPembayaran(api.paymentMethod),
            status = api.orderStatus.orEmpty()
        )
    }

    fun mapGetOrderKategori(
        response: ResponseKategoriOrder
    ): Load<List<KategoriOrder>> {
        return handleApiSuccess(data = response.categoryOrder?.map { mapToKategoriOrder(it) }
            .orEmpty())
    }

    fun mapToKategoriOrder(api: CategoryOrderApi?): KategoriOrder {
        return KategoriOrder(
            name = api?.categoryOrder.orEmpty(),
            id = api?.id ?: 0
        )
    }

    fun mapGetListPembayaran(response: ResponseListPembayaran): Load<List<Payment>> {
        return handleApiSuccess(data = response.payment?.map {
            mapToListPembayaran(it)
        }.orEmpty())
    }

    private fun mapToListPembayaran(api: PaymentApi?): Payment {
        return Payment(
            id = api?.id ?: 0,
            name = api?.payment.orEmpty()
        )

    }

    fun mapGetListKas(response: ResponseListKas): Load<List<Wallet>> {
        return handleApiSuccess(data = response.wallet?.map {
            mapToListKas(it)
        }.orEmpty())
    }

    private fun mapToListKas(api: WalletApi?): Wallet {
        return Wallet(
            id = api?.id ?: 0,
            name = api?.wallet.orEmpty()
        )
    }

    fun mapGetListCustomer(response: ResponsePelanggan): Load<List<Customer>> {
        return handleApiSuccess(data = response?.customer?.map {
            mapToListCustomer(it)
        }.orEmpty())
    }

    private fun mapToListCustomer(api: CustomerApi?): Customer {
        return Customer(
            id = api?.id ?: 0,
            codeCustomer = api?.categoryCustomerId ?: 0,
            naem = api?.customer.orEmpty(),
            discountCustomer = api?.discountCustomer ?: 0

        )
    }

}