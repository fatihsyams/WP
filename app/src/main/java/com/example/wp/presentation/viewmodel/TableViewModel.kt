package com.example.wp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wp.data.api.model.response.ResponseMenuWp
import com.example.wp.domain.menu.Category
import com.example.wp.domain.menu.Menu
import com.example.wp.domain.repository.MenuRepository
import com.example.wp.domain.repository.TableRepository
import com.example.wp.domain.table.Table
import com.example.wp.utils.Load
import kotlinx.coroutines.launch

class TableViewModel(val repository: TableRepository) : ViewModel() {
    private val _tablesLoad = MutableLiveData<Load<List<Table>>>()
    val tablesLoad = _tablesLoad as LiveData<Load<List<Table>>>

    init {
        _tablesLoad.value = Load.Loading
    }

    fun getTables() = viewModelScope.launch {
        val menus = repository.getTables()
        _tablesLoad.value = menus
    }

}