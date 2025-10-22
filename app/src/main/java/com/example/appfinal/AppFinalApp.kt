package com.example.aapfinal

import android.app.Application
import com.example.aapfinal.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppFinalApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            // Log para depuración
            android.util.Log.d("KoinInit", "Iniciando Koin...")

            androidContext(this@AppFinalApp)
            modules(appModule)

            android.util.Log.d("KoinInit", "Koin iniciado con éxito.")
        }
    }
}
