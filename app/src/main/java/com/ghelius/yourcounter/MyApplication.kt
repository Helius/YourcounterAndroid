package com.ghelius.yourcounter

import android.app.Application
import com.ghelius.yourcounter.auth.AuthProvider
import com.ghelius.yourcounter.data.*
import com.ghelius.yourcounter.presentation.vm.NewViewModel
import com.ghelius.yourcounter.presentation.vm.SettingsViewModel
import com.ghelius.yourcounter.presentation.vm.TestViewModel
import com.ghelius.yourcounter.usecase.BindingReceiversUsecase
import com.ghelius.yourcounter.usecase.SmsReceiveUsecase
import com.ghelius.yourcounter.usecase.UserInputTextProcessUsecase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
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
        single {
            TransactionCandidateRepo()
        }
        single {
            CategoryBindingRepo()
        }
        factory {
            UserInputTextProcessUsecase(get())
        }
        factory {
            BindingReceiversUsecase(get())
        }
        factory {
            SmsReceiveUsecase(get(), get(), get(), get())
        }
        viewModel { TestViewModel(get()) }
        viewModel { SettingsViewModel(get(), get()) }
        viewModel { NewViewModel(get(), get(), get(), get(), get()) }
    }

    override fun onCreate() {
        super.onCreate()
        Firebase.database.setPersistenceEnabled(true)
        startKoin {
            androidContext(applicationContext)
            modules(appModule)
        }
    }
}