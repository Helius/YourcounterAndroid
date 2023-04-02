package com.ghelius.yourcounter

import android.app.Application
import com.ghelius.yourcounter.auth.AuthProvider
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

val appModule = module {
    single<AuthProvider> { get() }
}

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            // declare used Android context
            androidContext(applicationContext)
            modules(appModule)
        }
    }
}