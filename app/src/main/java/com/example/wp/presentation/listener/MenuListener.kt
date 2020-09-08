package com.example.wp.presentation.listener

import com.example.wp.domain.menu.Category
import com.example.wp.domain.menu.Menu

interface MenuListener{
    fun onSelectMenu(menu:Menu)
}

interface OpenMenuPageListener{
    fun onOpenMenuPage()
}

interface CalculateMenuListener{
    fun onDeleteClicked(menu: Menu, position:Int)
    fun onPlusClicked(menu: Menu, position: Int)
    fun onMinuslicked(menu: Menu, position: Int)
}

interface MenuCategoryListener{
    fun onCategoryClicked(data:Category)
}