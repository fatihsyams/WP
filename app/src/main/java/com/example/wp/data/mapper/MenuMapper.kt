package com.example.wp.data.mapper

import com.example.wp.data.api.model.response.MenuApi
import com.example.wp.data.api.model.response.MenuImageApi
import com.example.wp.data.api.model.response.ResponseMenuWp
import com.example.wp.domain.menu.Menu
import com.example.wp.domain.menu.MenuImage
import com.example.wp.utils.Load
import com.example.wp.utils.handleApiSuccess

object MenuMapper {

    fun map(
        response:ResponseMenuWp
    ):Load<List<Menu>>{
        return handleApiSuccess(data = response.data?.map { mapToMenu(it) }.orEmpty())
    }

    private fun mapToMenu(response: MenuApi):Menu{
        return Menu(
            images = response.images?.map { mapToMenuImage(it) }.orEmpty(),
            additionalInformation = response.additionalInformation.orEmpty(),
            updatedAt = response.updatedAt.orEmpty(),
            price = response.price ?: 0,
            name = response.name.orEmpty(),
            description = response.description.orEmpty(),
            createdAt = response.createdAt.orEmpty(),
            id = response.id ?: 0,
            stock = response.stock ?: 0,
            category = response.category.orEmpty(),
            quantity = response.quantity ?: 0
        )
    }

    private fun mapToMenuImage(data:MenuImageApi):MenuImage{
        return MenuImage(
            updatedAt = data.updatedAt.orEmpty(),
            imageUrl = data.imageUrl.orEmpty(),
            id = data.id ?: 0,
            menuId = data.menuId ?: 0,
            createdAt = data.createdAt.orEmpty()
        )
    }

}