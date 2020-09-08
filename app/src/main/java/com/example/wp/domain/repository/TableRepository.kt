package com.example.wp.domain.repository

import com.example.wp.domain.table.Table
import com.example.wp.utils.Load

interface TableRepository{
    suspend fun getTables(): Load<List<Table>>
}