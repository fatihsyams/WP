package com.example.wp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wp.data.ResponseMenuWp
import com.example.wp.data.WarungPojokService
import kotlinx.coroutines.launch
import retrofit2.HttpException

class PesananViewModel : ViewModel() {
//    private val service = WarungPojokService.create(sm)
//
//    private val _pesananLoad = MutableLiveData<Load<ResponseMenuWp>>()
//    val pesananLoad = _pesananLoad as LiveData<Load<ResponseMenuWp>>
//
//    init {
//        _pesananLoad.value = Load.Uninitialized
//    }
//
//    fun getInfoMenus() = viewModelScope.launch {
//        _pesananLoad.value = Load.Loading
//
//        try {
//            val response = service.getMenu()
//            _pesananLoad.value = Load.Success(response)
//            Log.d("TAG", "DATA DAPAT CUY")
//
//        } catch (e: HttpException) {
//            _pesananLoad.value = Load.Fail(e)
//            Log.d("TAG", "DATA NYA GA ADA")
//        }
//    }

}