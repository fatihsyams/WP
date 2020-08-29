package com.example.wp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wp.data.api.model.response.ResponseMenuWp
import com.example.wp.domain.menu.Menu
import com.example.wp.domain.repository.MenuRepository
import com.example.wp.utils.Load
import kotlinx.coroutines.launch

class MenuViewModel(val repository: MenuRepository) : ViewModel() {
    private val _menusLoad = MutableLiveData<Load<List<Menu>>>()
    val menusLoad = _menusLoad as LiveData<Load<List<Menu>>>

    init {
        _menusLoad.value = Load.Loading
    }

    fun getMenus() = viewModelScope.launch {
        val menus = repository.getMenus()
        _menusLoad.value = menus
    }

}