package com.example.wp.domain.material

import com.example.wp.domain.menu.Category

data class Material(
    val id:Int = 0,
    val material:String,
    var stock:Int
){

    fun toCategoryOption():Category{
        return Category(
            id = id,
            name = material
        )
    }
}