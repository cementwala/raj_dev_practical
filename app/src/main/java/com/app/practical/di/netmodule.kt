package com.app.practical.di

import android.app.Application
import androidx.databinding.ktx.BuildConfig
import com.app.practical.network.api.URLFactory
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val netModule = module {
    fun provideCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    fun provideHttpClient(cache: Cache): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .cache(cache)
        return okHttpClientBuilder.build()
    }

    fun provideGson(): Gson {
        return GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .serializeNulls()
            .create()
    }


    fun provideRetrofit(factory: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder().client(
            client.newBuilder()
                .addInterceptor(headerInterceptor())
                .addInterceptor(HttpLoggingInterceptor().apply {
                    if (BuildConfig.DEBUG) {
                        level = HttpLoggingInterceptor.Level.BASIC
                    }
                })
                .addNetworkInterceptor(networkInterceptor())
                .build()
        )
            .baseUrl(URLFactory.provideHttpUrl())
            .addConverterFactory(GsonConverterFactory.create(factory))
            .build()
    }

    single { provideCache(androidApplication()) }
    single { provideHttpClient(get()) }
    single { provideGson() }
    single { provideRetrofit(get(), get()) }


}

private fun networkInterceptor(): Interceptor {
    return Interceptor.invoke { chain ->
        val response = chain.proceed(chain.request())


        response
    }
}

private fun headerInterceptor(): Interceptor {
    return Interceptor { chain ->
        chain.proceed(chain.request().newBuilder().apply {

        }.build())
    }
}