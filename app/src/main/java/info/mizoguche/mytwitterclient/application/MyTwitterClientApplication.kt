package info.mizoguche.mytwitterclient.application

import android.app.Application
import com.orhanobut.hawk.Hawk
import com.orhanobut.hawk.HawkBuilder
import com.orhanobut.hawk.LogLevel
import info.mizoguche.mytwitterclient.infrastructure.TwitterApi

class MyTwitterClientApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Hawk.init(this)
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.MEDIUM)
                .setStorage(HawkBuilder.newSharedPrefStorage(this))
                .setLogLevel(LogLevel.FULL)
                .buildRx()
                .subscribe { TwitterApi.init() }
    }
}

