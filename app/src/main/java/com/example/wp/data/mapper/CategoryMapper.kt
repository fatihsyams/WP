package com.example.wp.data.mapper

import com.example.wp.data.api.model.response.CategoryMenuApi
import com.example.wp.data.api.model.response.MenuImageApi
import com.example.wp.data.api.model.response.ResponseCategory
import com.example.wp.data.api.model.response.ResponseMenuWp
import com.example.wp.domain.menu.Category
import com.example.wp.domain.menu.Menu
import com.example.wp.domain.menu.MenuImage
import com.example.wp.utils.Load
import com.example.wp.utils.handleApiSuccess

object CategoryMapper {

    fun map(
        response:ResponseCategory
    ):Load<List<Category>>{
        return handleApiSuccess(data = response.categoryMenu?.map { mapToCategory(it) }.orEmpty())
    }

    private fun mapToCategory(response: CategoryMenuApi):Category{
        return Category(
          id = response.id ?: 0,
            name = response.category.orEmpty()
        )
    }

}