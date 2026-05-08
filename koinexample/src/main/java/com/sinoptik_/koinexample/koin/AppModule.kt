package com.sinoptik_.koinexample.koin

import com.sinoptik_.koinexample.MainActivity
import com.sinoptik_.koinexample.MyService
import com.sinoptik_.koinexample.utils.ActivityTracker
import com.sinoptik_.koinexample.utils.AnyApi
import com.sinoptik_.koinexample.utils.AnyApi2
import com.sinoptik_.koinexample.utils.FormValidator
import com.sinoptik_.koinexample.utils.ServiceLogger
import com.sinoptik_.koinexample.utils.TestMessagePrinter
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    // single — значит объект будет создан один раз (синглтон)
    // androidContext() — это встроенная магия Koin, он сам подтянет Application
    single { TestMessagePrinter(androidContext()) }
    factory { FormValidator() }

    single { "https://api.github.com" }
    // Второй экземпляр String
    single { "https://auth.github.com" }


}


val scopedModule = module {

    single { AnyApi() }
    single { AnyApi2() }


    scope<MainActivity> {
        scoped { ActivityTracker() }
    }


    scope<MyService> {
        scoped { params ->
            ServiceLogger(
                api = get(),
                data = params.get()

            )
        }
    }
}