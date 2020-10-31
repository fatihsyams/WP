package com.example.wp.domain.material

import android.os.Parcelable
import com.example.wp.domain.menu.Category
import com.example.wp.utils.emptyString
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Material(
    val id:Int = 0,
    val material:String = emptyString(),
    var stock:Int = 0,
    var decreasedQuantity :Int= 0,
    var increasedQuantity :Int= 0,
    var isEdited:Boolean = false
) : Parcelable {

    fun toCategoryOption():Category{
        return Category(
            id = id,
            name = material
        )
    }
}