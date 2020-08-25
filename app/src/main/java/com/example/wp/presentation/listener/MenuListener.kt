package com.example.wp.presentation.listener

import com.example.wp.data.api.model.response.DataItem

interface MenuListener{
    fun onSelectMenu(menu:DataItem)
}