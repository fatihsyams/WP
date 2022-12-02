package com.example.wp.presentation.listener

import com.example.wp.domain.material.Material
import com.example.wp.domain.menu.Category
import com.example.wp.domain.menu.Menu
import com.example.wp.domain.order.OrderResult

interface MenuListener{
    fun onSelectMenu(menu:Menu)
}

interface OpenMenuPageListener{
    fun onOpenMenuPage(menus:List<Menu>, orderResult: OrderResult?=null)
}

interface CalculateMenuListener{
    fun onDeleteClicked(menu: Menu, position:Int)
    fun onPlusClicked(menu: Menu, position: Int)
    fun onMinuslicked(menu: Menu, position: Int)
}

interface DeleteMenuListener{
    fun onDeleteClicked(menu: Menu, position:Int)
}

interface StockListener{
    fun onSaveStockClicked(position: Int,material: Material)
    fun onStockCreated()
    fun onIncreaseStockClicked(isIncrease:Boolean,increasedQuantity:Double)
    fun onDecreaseStockClicked(isDecrease:Boolean,decreasedQuantity:Double)
}

interface MenuCategoryListener{
    fun onCategoryClicked(data:Category)
}

interface CreateMenuListener{
    fun onMenuCreated()
}