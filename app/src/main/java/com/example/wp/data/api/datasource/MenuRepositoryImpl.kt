package com.example.wp.data.api.datasource

import com.example.wp.data.api.model.response.ResponseMenuWp
import com.example.wp.data.api.service.MenuService
import com.example.wp.data.mapper.CategoryMapper
import com.example.wp.data.mapper.MenuMapper
import com.example.wp.domain.menu.Category
import com.example.wp.domain.menu.Menu
import com.example.wp.domain.repository.MenuRepository
import com.example.wp.utils.Load
import com.example.wp.utils.handleApiError
import java.lang.Exception

class MenuRepositoryImpl(val service:MenuService):MenuRepository{
    override suspend fun getMenus(categoryId:Int): Load<List<Menu>> {
        return try {
            val response = service.getMenu(categoryId)
            if (response.isSuccessful){
                response.body()?.let {response->
                    MenuMapper.map(response)
                } ?: Load.Fail(Throwable(response.message()))
            }else{
                Load.Fail(Throwable(response.message()))
            }
        }catch (e:Exception){
            Load.Fail(e)
        }
    }

    override suspend fun getCategories(): Load<List<Category>> {
        return try {
            val response = service.getMenuCategories()
            if (response.isSuccessful){
                response.body()?.let {response->
                    CategoryMapper.map(response)
                } ?: Load.Fail(Throwable(response.message()))
            }else{
                Load.Fail(Throwable(response.message()))
            }
        }catch (e:Exception){
            Load.Fail(e)
        }
    }
}