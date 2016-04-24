package info.mizoguche.mytwitterclient.domain.service

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log
import info.mizoguche.mytwitterclient.infrastructure.TwitterApi
import rx.android.schedulers.AndroidSchedulers
import rx.lang.kotlin.observable
import rx.schedulers.Schedulers
import twitter4j.auth.AccessToken
import twitter4j.auth.RequestToken

object LoginService {
    val twitter = TwitterApi.twitter
    var requestToken: RequestToken? = null

    fun requestAccessToken(activity: Activity) {
        observable<RequestToken> { subscriber ->
            subscriber.onNext(twitter.getOAuthRequestToken("mytwitterclient:/oauth"))
            subscriber.onCompleted()
        }
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    requestToken = it
                    val url = it.authenticationURL
                    activity.startActivityForResult(Intent(Intent.ACTION_VIEW, Uri.parse(url)), 0)
                }, { Log.e("MyTwitterClient", "", it)})
    }

    fun fetchAccessToken(verifier: String) : AccessToken {
        return twitter.getOAuthAccessToken(requestToken, verifier)
    }
}

