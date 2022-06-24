package com.app.practical

import android.app.Application
import com.app.practical.di.apiModule
import com.app.practical.di.netModule
import com.app.practical.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication :Application(){
    companion object {
        var mInstance: MyApplication? = null

        @Synchronized
        fun getInstance(): MyApplication? {
            return mInstance
        }
    }

    override fun onCreate() {
        super.onCreate()


        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(
                    viewModelModule,
                    netModule,
                    apiModule,
            )

        }
    }

}