package info.mizoguche.mytwitterclient.domain.repository

import info.mizoguche.mytwitterclient.domain.collection.TimeLine
import info.mizoguche.mytwitterclient.domain.factory.TweetFactory
import info.mizoguche.mytwitterclient.infrastructure.TwitterApi
import rx.Observable

object  TimeLineRepository {
    fun getHomeTimeLine(): Observable<TimeLine> {
        return TwitterApi.homeTimeLine()
                .map { it.map { TweetFactory.create(it) }.toList() }
                .map { TimeLine(it) }
    }
}
