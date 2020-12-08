package com.example.wp.di.feature

import com.example.wp.data.api.datasource.PaymentRepositoryImpl
import com.example.wp.domain.repository.PaymentRepository
import com.example.wp.presentation.viewmodel.PaymentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val paymentModule = module {
    single<PaymentRepository>{
        PaymentRepositoryImpl()
    }

    viewModel { PaymentViewModel(get()) }
}