package com.example.wp.domain.repository


import com.example.wp.domain.menu.Menu
import com.example.wp.utils.Load

interface MenuRepository {
    suspend fun getMenus(): Load<List<Menu>>
    suspend fun deleteMenus(id: Int): Load<Boolean>
}