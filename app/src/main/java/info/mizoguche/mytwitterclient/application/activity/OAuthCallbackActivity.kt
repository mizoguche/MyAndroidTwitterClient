package info.mizoguche.mytwitterclient.application.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import info.mizoguche.mytwitterclient.domain.service.LoginService
import info.mizoguche.mytwitterclient.infrastructure.TwitterApi
import rx.android.schedulers.AndroidSchedulers
import rx.lang.kotlin.observable
import rx.schedulers.Schedulers
import twitter4j.auth.AccessToken

class OAuthCallbackActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(TwitterApi.isOwnUserSaved()){
            finish()
            return
        }

        val uri : Uri? = intent.data
        if (uri != null && uri.toString().startsWith("mytwitterclient")) {
            fetchAccessToken(uri)
            return
        }

        LoginService.requestAccessToken(this)
    }

    private fun fetchAccessToken(uri: Uri) {
        val verifier: String = uri.getQueryParameter("oauth_verifier")
        observable<AccessToken> { observer ->
            observer.onNext(LoginService.fetchAccessToken(verifier))
            observer.onCompleted()
        }
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    TwitterApi.addOwnUser(it)
                    finish()
                    startActivity(TimeLineActivity.createIntent(this))
                })
    }

    companion object {
        fun createIntent (context: Context) : Intent {
            return Intent(context, OAuthCallbackActivity::class.java)
        }
    }
}
