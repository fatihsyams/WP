package com.example.wp.data.api.model.request

import com.google.gson.annotations.SerializedName

data class RequestMaterialMenuApi(
    @SerializedName("material_id")
    val materialId: Int,
    @SerializedName("menu_id")
    val menuId: Int,
    @SerializedName("stock_required")
    val stockRequired: Double
)