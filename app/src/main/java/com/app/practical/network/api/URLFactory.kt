package com.app.practical.network.api

import okhttp3.HttpUrl


object URLFactory {

    lateinit var SCHEME: String
    lateinit var HOST: String

    private lateinit var API_PATH: String



    private val VERSION_URL = "/api/v2/"
    fun provideHttpUrl(): HttpUrl {
        SCHEME = "https"
        HOST = "demo1354773.mockable.io"

        return HttpUrl.Builder()
            .scheme(SCHEME)
            .host(HOST)
            .build()


    }


    const val GET_DELIVERIES = "/deliveries"




}
