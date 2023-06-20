package com.example.wp.domain.menu

import com.example.wp.domain.material.Material
import com.example.wp.domain.material.MaterialMenu

data class Category(
    val id:Int = 0,
    val name:String
){

    fun toMaterial():Material{
        return Material(
            id = id,
            material = name
        )
    }
}