package com.example.wp.domain.repository

import com.example.wp.domain.material.Material
import com.example.wp.domain.material.MaterialMenu
import com.example.wp.domain.order.Order
import com.example.wp.domain.order.OrderResult
import com.example.wp.utils.Load

interface MaterialRepository{
    suspend fun postMaterial(material:Material): Load<Boolean>
    suspend fun getMaterials(): Load<List<Material>>
    suspend fun editMaterial(materialId:Int, material:Material): Load<Boolean>
    suspend fun postMaterialMenu(materialMenu:MaterialMenu): Load<Boolean>
    suspend fun getMaterialMenus(menuId:Int): Load<List<MaterialMenu>>
    suspend fun editMaterialMenu(menuId:Int, stock:Int, materialId: Int): Load<Boolean>
}