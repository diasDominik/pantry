package de.dias.dominik.application

import android.app.Application
import de.dias.dominik.di.platformModule
import de.dias.dominik.di.sharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PantryApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PantryApplication)
            modules(sharedModule, platformModule)
        }
    }
}
