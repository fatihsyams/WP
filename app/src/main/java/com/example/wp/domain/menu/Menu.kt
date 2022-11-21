package com.example.wp.domain.menu

import android.os.Parcelable
import com.example.wp.domain.material.MaterialMenu
import com.example.wp.utils.emptyString
import kotlinx.android.parcel.Parcelize

data class EndlessMenu(
    val totalPage:Int,
    val menus:List<Menu>
)

@Parcelize
data class Menu(
    val images: String = emptyString(),
    var additionalInformation: String = emptyString(),
    val updatedAt: String = emptyString(),
    var price: Double = 0.0,
    val name: String = emptyString(),
    val description: String = emptyString(),
    val createdAt: String = emptyString(),
    val id: Int = 0,
    var stock: Double = 0.0,
    val category: String = emptyString(),
    var quantity: Int = 0,
    var goFoodPrice:Double = 0.0,
    var grabFoodPrice:Double = 0.0,
    var materialMenus:List<MaterialMenu> = listOf(),
    var isAvailable:Boolean = stock != 0.0,
    var stockRequired:Int = materialMenus.map { it.stockRequired }.sum(),
    var totalPrice:Double = price*quantity,
    val discount:Int = 0,
    val discountTakeAway:Int = 9,
    val discountGofood:Int = 9,
    val discountGrabfood:Int = 9,
    val menuPrice: List<MenuPrice> = listOf()
) : Parcelable
@Parcelize
data class MenuPrice(
    val menuId: Int,
    val categoryOrderId: Int,
    val discountMenu: String,
    val price: Int,
    val id: Int
): Parcelable

@Parcelize
data class MenuImage(
    val updatedAt: String = emptyString(),
    val imageUrl: String = emptyString(),
    val id: Int = 0,
    val menuId: Int = 0,
    val createdAt: String = emptyString()
) : Parcelable