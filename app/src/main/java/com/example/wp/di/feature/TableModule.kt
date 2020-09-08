package com.example.wp.di.feature

import com.example.wp.data.api.datasource.TableRepositoryImpl
import com.example.wp.domain.repository.TableRepository
import com.example.wp.presentation.viewmodel.TableViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val tableModule = module {
    single<TableRepository>{
        TableRepositoryImpl(
            get()
        )
    }

    viewModel { TableViewModel(get()) }
}