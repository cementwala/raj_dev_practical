package com.app.practical.di

import com.app.practical.network.api.ApiService
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single(createdAtStart = true) { get<Retrofit>().create(ApiService::class.java) }
}