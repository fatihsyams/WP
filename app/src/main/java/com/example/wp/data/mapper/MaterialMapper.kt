package com.example.wp.data.mapper

import com.example.wp.data.api.model.request.RequestMaterialApi
import com.example.wp.data.api.model.request.RequestMaterialMenuApi
import com.example.wp.data.api.model.response.*
import com.example.wp.domain.material.Material
import com.example.wp.domain.material.MaterialMenu
import com.example.wp.utils.Load
import com.example.wp.utils.handleApiSuccess

object MaterialMapper {

    fun map(
        response: ResponseMaterials
    ): Load<List<Material>> {
        return handleApiSuccess(data = response.material?.map { mapToMaterial(it) }.orEmpty())
    }

    private fun mapToMaterial(response: MaterialApi): Material {
        return Material(
            material = response.material.orEmpty(),
            stock = response.stock ?: 0
        )
    }

    fun mapToRequestMaterialApi(domain:Material): RequestMaterialApi {
        return RequestMaterialApi(
            material = domain.material,
            stock = domain.stock
        )
    }

    fun mapMaterialMenu(
        response: ResponseMaterialsMenu
    ): Load<List<MaterialMenu>> {
        return handleApiSuccess(data = response.materialMenu?.map { mapToMaterialMenu(it) }.orEmpty())
    }

    private fun mapToMaterialMenu(response: MaterialMenuApi): MaterialMenu {
        return MaterialMenu(
            materialId = response.materialId ?: 0,
            stockRequired = response.stockRequired ?: 0,
            menuId = response.menuId ?: 0
        )
    }

    fun mapToRequestMaterialMenuApi(domain:MaterialMenu): RequestMaterialMenuApi {
        return RequestMaterialMenuApi(
            materialId = domain.materialId,
            stockRequired = domain.stockRequired,
            menuId = domain.menuId
        )
    }

}