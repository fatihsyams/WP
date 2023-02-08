package com.example.wp.data.mapper

import com.example.wp.data.api.model.response.*
import com.example.wp.domain.menu.EndlessMenu
import com.example.wp.domain.menu.Menu
import com.example.wp.domain.menu.MenuImage
import com.example.wp.domain.menu.MenuPrice
import com.example.wp.presentation.adapter.MenusAdapter
import com.example.wp.utils.Load
import com.example.wp.utils.emptyString
import com.example.wp.utils.handleApiSuccess

object MenuMapper {

    fun mapSearchMenu(
        response: ResponseSearchMenu
    ): Load<List<Menu>> {
        return handleApiSuccess(data = response.menu.map { mapToMenu(it) })
    }

    fun map(
        response: ResponseMenuWp
    ): Load<List<Menu>> {
        return handleApiSuccess(data = response.data?.map {
            mapToMenu(it)
        }.orEmpty())
    }

    private fun mapToEndlessMenu(response: EndlessMenuApi?):EndlessMenu{
        return EndlessMenu(
            totalPage = response?.lastPage ?: 0,
            menus = response?.menu?.map { mapToMenu(it) }.orEmpty()
        )
    }

    fun mapToMenu(response: MenuApi,quantity:Int? = 0,information:String= emptyString(), menuPriceOrder:List<MenuPriceApi>? = null): Menu {
        return Menu(
            images = response.images.orEmpty(),
            additionalInformation = information,
            updatedAt = response.updatedAt.orEmpty(),
            price = getPrice(
                response.price ?: 0.0,
                response.discount
            ),
            goFoodPrice = getPrice(response.goFoodPrice ?: 0.0, response.discountGofood),
            grabFoodPrice = getPrice(response.grabFoodPrice ?: 0.0, response.discountGrabfood),
            name = response.name.orEmpty(),
            description = response.description.orEmpty(),
            createdAt = response.createdAt.orEmpty(),
            id = response.id ?: 0,
            stock = 0.0,
            category = response.category.orEmpty(),
            quantity = quantity ?: 0,
            materialMenus = emptyList(),
            stockRequired =0,
            discount = response.discount ?: 0,
            discountTakeAway = response.discountTakeAway ?: 0,
            discountGofood = response.discountGofood ?: 0,
            discountGrabfood = response.discountGrabfood ?: 0,
            menuPrice = menuPriceOrder?.map { mapToMenuPrice(it) }
                ?: response.menuPrice?.map { mapToMenuPrice(it) }.orEmpty()
        )
    }

    fun mapToMenuPrice(response: MenuPriceApi): MenuPrice {
        return MenuPrice(
            discountMenu = response.discountMenu ?: 0,
            menuId = response.menuId ?: 0,
            id = response.id ?: 0,
            categoryOrderId = response.categoryOrderId ?: 0,
            price = response.price ?: 0,
            orderCategory = OrderMapper.mapToKategoriOrder(response.categoryOrder),
            menu = mapToMenu(response.menu ?: MenuApi())
        )
    }

    private fun getPrice(price: Double, discount: Int?): Double {
        return if (discount != null) price - price.times(discount) / 100 else price
    }

}
