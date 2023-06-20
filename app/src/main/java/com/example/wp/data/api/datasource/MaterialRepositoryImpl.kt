package com.example.wp.data.api.datasource

import com.example.wp.data.api.model.request.RequestQuantityMaterialApi
import com.example.wp.data.api.model.response.MaterialApi
import com.example.wp.data.api.service.MaterialService
import com.example.wp.data.mapper.MaterialMapper
import com.example.wp.domain.material.Material
import com.example.wp.domain.material.MaterialMenu
import com.example.wp.domain.repository.MaterialRepository
import com.example.wp.utils.Load

class MaterialRepositoryImpl(private val service: MaterialService) : MaterialRepository {
    override suspend fun postMaterial(material: Material): Load<Boolean> {
        return try {
            val response = service.postMaterial(MaterialMapper.mapToRequestMaterialApi(material))
            if (response.isSuccessful) {
                response.body()?.let { response ->
                    Load.Success(response.status == "OK")
                } ?: Load.Fail(Throwable(response.message()))
            } else {
                Load.Fail(Throwable(response.message()))
            }
        } catch (e: Exception) {
            Load.Fail(e)
        }
    }

    override suspend fun getMaterials(): Load<List<Material>> {
        return try {
            val response = service.getMaterials()
            if (response.isSuccessful) {
                response.body()?.let { response ->
                    MaterialMapper.map(response)
                } ?: Load.Fail(Throwable(response.message()))
            } else {
                Load.Fail(Throwable(response.message()))
            }
        } catch (e: Exception) {
            Load.Fail(e)
        }
    }

    override suspend fun getMaterial(materialId:Int): Load<Material> {
        return try {
            val response = service.getMaterial(materialId)
            if (response.isSuccessful) {
                response.body()?.let { response ->
                    MaterialMapper.mapMaterial(response)
                } ?: Load.Fail(Throwable(response.message()))
            } else {
                Load.Fail(Throwable(response.message()))
            }
        } catch (e: Exception) {
            Load.Fail(e)
        }
    }

    override suspend fun editMaterial(materialId: Int, material: Material): Load<Boolean> {
        return try {
            val response = service.editMaterial(materialId,MaterialMapper.mapToRequestMaterialApi(material))
            if (response.isSuccessful) {
                response.body()?.let { response ->
                    Load.Success(response.status == "OK")
                } ?: Load.Fail(Throwable(response.message()))
            } else {
                Load.Fail(Throwable(response.message()))
            }
        } catch (e: Exception) {
            Load.Fail(e)
        }
    }

    override suspend fun updateMaterial(
        materialId: Int,
        stock: Double,
        type: String,
        reason: String
    ): Load<Boolean> {
        return try {
            val materialQuantity = RequestQuantityMaterialApi(
                stock,type, reason
            )
            val response = service.updateMaterial(materialId,materialQuantity)
            if (response.isSuccessful) {
                response.body()?.let { response ->
                    Load.Success(response.status == "OK")
                } ?: Load.Fail(Throwable(response.message()))
            } else {
                Load.Fail(Throwable(response.message()))
            }
        } catch (e: Exception) {
            Load.Fail(e)
        }
    }

    override suspend fun postMaterialMenu(materialMenu: MaterialMenu): Load<Boolean> {
        return try {
            val response = service.postMaterialMenu(MaterialMapper.mapToRequestMaterialMenuApi(materialMenu))
            if (response.isSuccessful) {
                response.body()?.let { response ->
                    Load.Success(response.status == "OK")
                } ?: Load.Fail(Throwable(response.message()))
            } else {
                Load.Fail(Throwable(response.message()))
            }
        } catch (e: Exception) {
            Load.Fail(e)
        }
    }

    override suspend fun getMaterialMenus(menuId: Int): Load<List<MaterialMenu>> {
        return try {
            val response = service.getMaterialMenu(menuId)
            if (response.isSuccessful) {
                response.body()?.let { response ->
                    MaterialMapper.mapMaterialMenu(response)
                } ?: Load.Fail(Throwable(response.message()))
            } else {
                Load.Fail(Throwable(response.message()))
            }
        } catch (e: Exception) {
            Load.Fail(e)
        }
    }

    override suspend fun editMaterialMenu(menuId: Int, stock: Int, materialId: Int): Load<Boolean> {
        return try {
            val params = hashMapOf<String,Int>()
            params["stock_required"] = stock
            params["material_id"] = materialId

            val response = service.editMaterialMenu(menuId,params)
            if (response.isSuccessful) {
                response.body()?.let { response ->
                    Load.Success(response.status == "OK")
                } ?: Load.Fail(Throwable(response.message()))
            } else {
                Load.Fail(Throwable(response.message()))
            }
        } catch (e: Exception) {
            Load.Fail(e)
        }
    }
}