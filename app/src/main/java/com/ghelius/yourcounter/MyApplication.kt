package com.ghelius.yourcounter

import android.app.Application
import com.ghelius.yourcounter.auth.AuthProvider
import com.ghelius.yourcounter.data.CategoryRepo
import com.ghelius.yourcounter.data.NotificationService
import com.ghelius.yourcounter.data.TransactionRepo
import com.ghelius.yourcounter.usecase.SmsReceiveUsecase
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

class MyApplication : Application() {

    private val appModule = module {
        single<AuthProvider> { get() }
        single<TransactionRepo> { get() }
        single<CategoryRepo> { get() }
        single<NotificationService> { get() }
        factory {
            SmsReceiveUsecase(get(), get(), get())
        }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin{
            // declare used Android context
            androidContext(applicationContext)
            modules(appModule)
        }
    }
}