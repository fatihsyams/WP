package com.example.wp.presentation.listener

import com.example.wp.domain.menu.Menu

interface MenuListener{
    fun onSelectMenu(menu:Menu)
}

interface OpenMenuPageListener{
    fun onOpenMenuPage()
}

interface CalculateMenuListener{
    fun onDeleteClicked(menu: Menu, position:Int)
}

interface DeleteMenuListener{
    fun onDeleteClicked(menu: Menu, position:Int)
}
interface StockListener{
    fun onSeveClicked(menu : Menu)
}