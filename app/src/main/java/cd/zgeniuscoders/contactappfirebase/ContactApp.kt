package cd.zgeniuscoders.contactappfirebase

import android.app.Application
import cd.zgeniuscoders.contactappfirebase.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ContactApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(applicationContext)
            modules(
                appModule
            )
        }
    }

}