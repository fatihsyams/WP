package com.example.wp.data.api.datasource

import com.example.wp.data.api.service.TableService
import com.example.wp.data.mapper.TableMapper
import com.example.wp.domain.repository.TableRepository
import com.example.wp.domain.table.Table
import com.example.wp.utils.Load

class TableRepositoryImpl (private val service:TableService):TableRepository{
    override suspend fun getTables(): Load<List<Table>> {
        return try {
            val response = service.getTables()
            if (response.isSuccessful){
                response.body()?.let {response->
                    TableMapper.map(response)
                } ?: Load.Fail(Throwable(response.message()))
            }else{
                Load.Fail(Throwable(response.message()))
            }
        }catch (e: Exception){
            Load.Fail(e)
        }
    }

}