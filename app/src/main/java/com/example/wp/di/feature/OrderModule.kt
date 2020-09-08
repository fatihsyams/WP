package com.example.wp.di.feature

import com.example.wp.data.api.datasource.OrderRepositoryImpl
import com.example.wp.domain.repository.OrderRepository
import com.example.wp.presentation.viewmodel.OrderViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val orderModule = module {
    single<OrderRepository>{
        OrderRepositoryImpl(
            get()
        )
    }

    viewModel { OrderViewModel(get()) }
}