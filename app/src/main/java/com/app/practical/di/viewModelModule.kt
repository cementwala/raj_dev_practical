package com.app.practical.di

import com.app.practical.business_logic.delivery_details.DeliveryDetailViewModel
import com.app.practical.business_logic.list_screen.DeliveryListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val  viewModelModule= module{


    viewModel {DeliveryListViewModel(get()) }
    viewModel { DeliveryDetailViewModel() }

}