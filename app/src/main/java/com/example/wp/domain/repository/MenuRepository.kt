package com.example.wp.domain.repository

import com.example.wp.domain.menu.Category
import com.example.wp.domain.menu.EndlessMenu
import com.example.wp.domain.menu.Menu
import com.example.wp.utils.Load

interface MenuRepository {
    suspend fun deleteMenus(id: Int): Load<Boolean>
    suspend fun getMenus(categoryId:Int,menuId: Int, page:Int):Load<List<Menu>>
    suspend fun getCategories():Load<List<Category>>
    suspend fun getSearchMenuResult(query:String, categoryId: Int):Load<List<Menu>>
}