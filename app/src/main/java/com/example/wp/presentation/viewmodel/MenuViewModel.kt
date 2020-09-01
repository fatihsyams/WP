package com.example.wp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wp.data.api.model.response.ResponseMenuWp
import com.example.wp.domain.menu.Category
import com.example.wp.domain.menu.Menu
import com.example.wp.domain.repository.MenuRepository
import com.example.wp.utils.Load
import kotlinx.coroutines.launch

class MenuViewModel(val repository: MenuRepository) : ViewModel() {
    private val _menusLoad = MutableLiveData<Load<List<Menu>>>()
    val menusLoad = _menusLoad as LiveData<Load<List<Menu>>>

    private val _categoriesLoad = MutableLiveData<Load<List<Category>>>()
    val categoriesLoad = _categoriesLoad as LiveData<Load<List<Category>>>

    init {
        _menusLoad.value = Load.Loading
        _categoriesLoad.value = Load.Loading
    }

    fun getMenus(categoryId:Int = 0) = viewModelScope.launch {
        val menus = repository.getMenus(categoryId)
        _menusLoad.value = menus
    }

    fun getCategories() = viewModelScope.launch {
        val categories = repository.getCategories()
        _categoriesLoad.value = categories
    }

}