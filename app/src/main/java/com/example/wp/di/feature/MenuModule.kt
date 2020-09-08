package com.example.wp.di.feature

import com.example.wp.data.api.datasource.MenuRepositoryImpl
import com.example.wp.domain.repository.MenuRepository
import com.example.wp.presentation.viewmodel.MenuViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val menuModule = module {
    single<MenuRepository>{
        MenuRepositoryImpl(
            get()
        )
    }

    viewModel { MenuViewModel(get()) }
}