package ar.scacchipa.twittercloneapp.component

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin

class TwitterCloneApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            fragmentFactory()
            androidContext(this@TwitterCloneApp)
            modules(appModule)
        }
    }
}