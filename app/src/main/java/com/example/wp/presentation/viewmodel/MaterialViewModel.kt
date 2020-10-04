package com.example.wp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wp.domain.material.Material
import com.example.wp.domain.material.MaterialMenu
import com.example.wp.domain.repository.MaterialRepository
import com.example.wp.utils.Load
import kotlinx.coroutines.launch

class MaterialViewModel (private val repository: MaterialRepository): ViewModel() {

    private val _postMaterialLoad = MutableLiveData<Load<Boolean>>()
    val materialLoad = _postMaterialLoad as LiveData<Load<Boolean>>
    private val _getMaterialLoad = MutableLiveData<Load<List<Material>>>()
    val getMaterialLoad = _getMaterialLoad as LiveData<Load<List<Material>>>
    private val _editMaterialLoad = MutableLiveData<Load<Boolean>>()
    val editMaterialLoad = _editMaterialLoad as LiveData<Load<Boolean>>
    private val _postMaterialMenuLoad = MutableLiveData<Load<Boolean>>()
    val materiaMenulLoad = _postMaterialMenuLoad as LiveData<Load<Boolean>>
    private val _getMaterialMenuLoad = MutableLiveData<Load<List<MaterialMenu>>>()
    val getMaterialMenuLoad = _getMaterialMenuLoad as LiveData<Load<List<MaterialMenu>>>
    private val _editMaterialMenuLoad = MutableLiveData<Load<Boolean>>()
    val editMaterialMenuLoad = _editMaterialMenuLoad as LiveData<Load<Boolean>>


    fun postMaterial(material:Material) = viewModelScope.launch {
        _postMaterialLoad.value = Load.Loading
        val material = repository.postMaterial(material)
        _postMaterialLoad.value = material
    }

    fun getMaterials() = viewModelScope.launch {
        _getMaterialLoad.value = Load.Loading
        val material = repository.getMaterials()
        _getMaterialLoad.value = material
    }

    fun editMaterial(materialId:Int, material:Material) = viewModelScope.launch {
        _editMaterialLoad.value = Load.Loading
        val material = repository.editMaterial(materialId, material)
        _editMaterialLoad.value = material
    }

    fun postMaterialMenu(material:MaterialMenu) = viewModelScope.launch {
        _postMaterialMenuLoad.value = Load.Loading
        val material = repository.postMaterialMenu(material)
        _postMaterialMenuLoad.value = material
    }

    fun getMaterialMemnus(menuId:Int) = viewModelScope.launch {
        _getMaterialMenuLoad.value = Load.Loading
        val material = repository.getMaterialMenus(menuId)
        _getMaterialMenuLoad.value = material
    }

    fun editMaterialMenu(menuId:Int,stock:Int, materialId: Int) = viewModelScope.launch {
        _editMaterialMenuLoad.value = Load.Loading
        val material = repository.editMaterialMenu(menuId, stock, materialId)
        _editMaterialMenuLoad.value = material
    }


}