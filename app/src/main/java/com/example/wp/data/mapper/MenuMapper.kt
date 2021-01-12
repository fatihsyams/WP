package com.example.wp.data.mapper

import com.example.wp.data.api.model.response.*
import com.example.wp.domain.menu.EndlessMenu
import com.example.wp.domain.menu.Menu
import com.example.wp.domain.menu.MenuImage
import com.example.wp.utils.Load
import com.example.wp.utils.handleApiSuccess

object MenuMapper {

    fun mapSearchMenu(
        response: ResponseSearchMenu
    ): Load<List<Menu>> {
        return handleApiSuccess(data = response.menu.map { mapToMenu(it) })
    }

    fun map(
        response: ResponseMenuWp
    ): Load<EndlessMenu> {
        return handleApiSuccess(data = mapToEndlessMenu(response.data))
    }

    private fun mapToEndlessMenu(response: EndlessMenuApi?):EndlessMenu{
        return EndlessMenu(
            totalPage = response?.lastPage ?: 0,
            menus = response?.menu?.map { mapToMenu(it) }.orEmpty()
        )
    }

    fun mapToMenu(response: MenuApi,quantity:Int? = 0): Menu {
        return Menu(
            images = response.images.orEmpty(),
            additionalInformation = response.additionalInformation.orEmpty(),
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
            stock = response.materialMenus?.map { materialMenu ->
                materialMenu.material?.stock ?: 0.0
            }?.sum() ?: 0.0,
            category = response.category.orEmpty(),
            quantity = quantity ?: 0,
            materialMenus = response.materialMenus?.map { MaterialMapper.mapToMaterialMenu(it) }
                .orEmpty(),
            stockRequired = response.materialMenus?.map { it.stockRequired ?: 0 }?.sum() ?: 0,
            discount = response.discount ?: 0,
            discountTakeAway = response.discountTakeAway ?: 0,
            discountGofood = response.discountGofood ?: 0,
            discountGrabfood = response.discountGrabfood ?: 0
        )
    }

    private fun getPrice(price: Double, discount: Int?): Double {
        return if (discount != null) price - price.times(discount) / 100 else price
    }

}
