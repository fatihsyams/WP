package com.example.wp.domain.material

import android.os.Parcelable
import com.example.wp.domain.menu.Category
import com.example.wp.utils.emptyString
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Material(
    val id:Int = 0,
    val material:String = emptyString(),
    var stock:Double = 0.0,
    var decreasedQuantity :Double= 0.0,
    var increasedQuantity :Double= 0.0,
    var isEdited:Boolean = false
) : Parcelable {

    fun toCategoryOption():Category{
        return Category(
            id = id,
            name = material
        )
    }
}