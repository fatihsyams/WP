package com.example.wp.domain.repository

import com.example.wp.data.api.model.response.ResponseMenuWp
import com.example.wp.domain.menu.Menu
import com.example.wp.utils.Load

interface MenuRepository {
    suspend fun getMenus():Load<List<Menu>>
}