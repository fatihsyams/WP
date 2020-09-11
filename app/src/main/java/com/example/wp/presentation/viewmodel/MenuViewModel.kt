package com.example.wp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wp.domain.menu.Menu
import com.example.wp.domain.repository.MenuRepository
import com.example.wp.utils.Load
import kotlinx.coroutines.launch

class MenuViewModel(val repository: MenuRepository) : ViewModel() {
    private val _menusLoad = MutableLiveData<Load<List<Menu>>>()
    val menusLoad = _menusLoad as LiveData<Load<List<Menu>>>

    private val _deleteMenu = MutableLiveData<Load<Boolean>>()
    val deleteMenu = _deleteMenu as LiveData<Load<Boolean>>

    init {
        _menusLoad.value = Load.Loading
        _deleteMenu.value = Load.Loading
    }

    fun getMenus() = viewModelScope.launch {
        val menus = repository.getMenus()
        _menusLoad.value = menus
    }

    fun deleteMenus(id: Int) = viewModelScope.launch {
        val delete = repository.deleteMenus(id)
        _deleteMenu.value = delete
    }

}