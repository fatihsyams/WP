package com.example.wp.data.mapper

import com.example.wp.data.api.model.response.EndlessMenuApi
import com.example.wp.data.api.model.response.MenuApi
import com.example.wp.data.api.model.response.MenuImageApi
import com.example.wp.data.api.model.response.ResponseMenuWp
import com.example.wp.domain.menu.EndlessMenu
import com.example.wp.domain.menu.Menu
import com.example.wp.domain.menu.MenuImage
import com.example.wp.utils.Load
import com.example.wp.utils.handleApiSuccess

object MenuMapper {

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

    fun mapToMenu(response: MenuApi): Menu {
        return Menu(
            images = response.images.orEmpty(),
            additionalInformation = response.additionalInformation.orEmpty(),
            updatedAt = response.updatedAt.orEmpty(),
            price = getPrice(
                response.price ?: 0.0,
                response.discount
            ),
            goFoodPrice = getPrice(response.goFoodPrice ?: 0.0, response.discountTakeAway),
            grabFoodPrice = getPrice(response.grabFoodPrice ?: 0.0, response.discountTakeAway),
            name = response.name.orEmpty(),
            description = response.description.orEmpty(),
            createdAt = response.createdAt.orEmpty(),
            id = response.id ?: 0,
            stock = response.materialMenus?.map { materialMenu ->
                materialMenu.material?.stock ?: 0
            }?.sum() ?: 0,
            category = response.category.orEmpty(),
            quantity = response.quantity ?: 0,
            materialMenus = response.materialMenus?.map { MaterialMapper.mapToMaterialMenu(it) }
                .orEmpty(),
            stockRequired = response.materialMenus?.map { it.stockRequired ?: 0 }?.sum() ?: 0,
            discount = response.discount ?: 0,
            discountTakeAway = response.discountTakeAway ?: 0
        )
    }

    private fun getPrice(price: Double, discount: Int?): Double {
        return if (discount != null) price - price.times(discount) / 100 else price
    }

    private fun mapToMenuImage(data: MenuImageApi): MenuImage {
        return MenuImage(
            updatedAt = data.updatedAt.orEmpty(),
            imageUrl = data.imageUrl.orEmpty(),
            id = data.id ?: 0,
            menuId = data.menuId ?: 0,
            createdAt = data.createdAt.orEmpty()
        )
    }

}
