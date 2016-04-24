package info.mizoguche.mytwitterclient.domain.service

import info.mizoguche.mytwitterclient.domain.entity.TweetText
import info.mizoguche.mytwitterclient.infrastructure.TwitterApi
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.lang.kotlin.observable
import rx.schedulers.Schedulers

object TweetingService {
    private val twitter = TwitterApi.twitter

    fun tweet(text: TweetText) : Observable<Unit> {
        return observable<Unit> {
            twitter.updateStatus(text.value)
            it.onNext(Unit)
            it.onCompleted()
        }.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
    }
}

