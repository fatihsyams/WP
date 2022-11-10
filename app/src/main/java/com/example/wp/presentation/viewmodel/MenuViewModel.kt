package com.example.wp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wp.domain.menu.Category
import com.example.wp.domain.menu.EndlessMenu
import com.example.wp.domain.menu.Menu
import com.example.wp.domain.repository.MenuRepository
import com.example.wp.utils.Load
import kotlinx.coroutines.launch

class MenuViewModel(val repository: MenuRepository) : ViewModel() {
    private val _menusLoad = MutableLiveData<Load<EndlessMenu>>()
    val menusLoad = _menusLoad as LiveData<Load<EndlessMenu>>

    private val _deleteMenu = MutableLiveData<Load<Boolean>>()
    val deleteMenu = _deleteMenu as LiveData<Load<Boolean>>

    private val _categoriesLoad = MutableLiveData<Load<List<Category>>>()
    val categoriesLoad = _categoriesLoad as LiveData<Load<List<Category>>>


    private val _searchMenuLoad = MutableLiveData<Load<List<Menu>>>()
    val searchMenuLoad = _searchMenuLoad as LiveData<Load<List<Menu>>>


    init {
        _menusLoad.value = Load.Loading
        _deleteMenu.value = Load.Loading
        _categoriesLoad.value = Load.Loading
        _searchMenuLoad.value = Load.Loading
    }

    fun getMenus(categoryId:Int= 0, menuId:Int=0, page:Int) = viewModelScope.launch {
        _menusLoad.value = Load.Loading
        val menus = repository.getMenus(categoryId, menuId, page)
        _menusLoad.value = menus
    }

    fun deleteMenus(id: Int) = viewModelScope.launch {
        val delete = repository.deleteMenus(id)
        _deleteMenu.value = delete
    }

    fun getCategories() = viewModelScope.launch {
        val categories = repository.getCategories()
        _categoriesLoad.value = categories
    }

    fun getMenus(query:String) = viewModelScope.launch {
        _searchMenuLoad.value = Load.Loading
        val menus = repository.getSearchMenuResult(query)
        _searchMenuLoad.value = menus
    }

}