package com.example.wp.data.api.datasource

import com.example.wp.data.api.model.response.ResponseMenuWp
import com.example.wp.data.api.service.MenuService
import com.example.wp.data.mapper.MenuMapper
import com.example.wp.domain.menu.Menu
import com.example.wp.domain.repository.MenuRepository
import com.example.wp.utils.Load
import com.example.wp.utils.handleApiError
import java.lang.Exception

class MenuRepositoryImpl(val service:MenuService):MenuRepository{
    override suspend fun getMenus(): Load<List<Menu>> {
        return try {
            val response = service.getMenu()
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
}