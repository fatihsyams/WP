package com.example.wp.di.feature

import com.example.wp.data.api.datasource.MaterialRepositoryImpl
import com.example.wp.data.api.datasource.OrderRepositoryImpl
import com.example.wp.domain.repository.MaterialRepository
import com.example.wp.domain.repository.OrderRepository
import com.example.wp.presentation.viewmodel.MaterialViewModel
import com.example.wp.presentation.viewmodel.OrderViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val materialModule = module {
    single<MaterialRepository>{
        MaterialRepositoryImpl(
            get()
        )
    }

    viewModel { MaterialViewModel(get()) }
}