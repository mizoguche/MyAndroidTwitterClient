package info.mizoguche.mytwitterclient.domain.service

import info.mizoguche.mytwitterclient.domain.entity.Tweet
import info.mizoguche.mytwitterclient.domain.entity.TweetText
import info.mizoguche.mytwitterclient.infrastructure.TwitterApi
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.lang.kotlin.observable
import rx.schedulers.Schedulers
import twitter4j.StatusUpdate

object TweetingService {
    private val twitter = TwitterApi.twitter

    fun tweet(text: TweetText) : Observable<Unit> {
        return request { twitter.updateStatus(text.value) }
    }

    fun reply(text: TweetText, replyTo: Tweet) : Observable<Unit> {
        return request {
            val statusUpdate = StatusUpdate(text.value)
            statusUpdate.inReplyToStatusId(replyTo.id.value)
            twitter.updateStatus(statusUpdate)
        }
    }

    fun like(tweet: Tweet): Observable<Unit> {
        return request { twitter.createFavorite(tweet.id.value) }
    }

    fun retweet(tweet: Tweet): Observable<Unit> {
        return request { twitter.retweetStatus(tweet.id.value) }
    }

    private fun request(action: () -> Unit): Observable<Unit> {
        return observable<Unit> {
            action()
            it.onNext(Unit)
            it.onCompleted()
        }.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
    }
}

