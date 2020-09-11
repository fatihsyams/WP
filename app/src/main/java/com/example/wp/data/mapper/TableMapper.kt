package com.example.wp.data.mapper

import com.example.wp.data.api.model.response.*
import com.example.wp.domain.menu.Category
import com.example.wp.domain.menu.Menu
import com.example.wp.domain.menu.MenuImage
import com.example.wp.domain.table.Table
import com.example.wp.utils.Load
import com.example.wp.utils.handleApiSuccess

object TableMapper {

    fun map(
        response:ResponseTable
    ):Load<List<Table>>{
        return handleApiSuccess(data = response.table?.map { mapToTable(it) }.orEmpty())
    }

    private fun mapToTable(response: TableApi):Table{
        return Table(
          id = response.id ?: 0,
            number = response.number ?: 0
        )
    }

}