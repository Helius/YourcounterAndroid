package com.ghelius.yourcounter

import android.app.Application
import com.ghelius.yourcounter.auth.AuthProvider
import com.ghelius.yourcounter.data.CategoryRepo
import com.ghelius.yourcounter.data.NotificationService
import com.ghelius.yourcounter.data.TransactionRepo
import com.ghelius.yourcounter.presentation.vm.TestViewModel
import com.ghelius.yourcounter.usecase.SmsReceiveUsecase
import com.ghelius.yourcounter.usecase.UserInputTextProcessUsecase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

class MyApplication : Application() {

    private val appModule = module {
        single {
            AuthProvider()
        }
        single {
            TransactionRepo()
        }
        single {
            CategoryRepo()
        }
        single {
            NotificationService(androidContext())
        }
        factory {
            UserInputTextProcessUsecase(get())
        }
        factory {
            SmsReceiveUsecase(get(), get(), get())
        }
        viewModel { TestViewModel(get()) }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(appModule)
        }
    }
}