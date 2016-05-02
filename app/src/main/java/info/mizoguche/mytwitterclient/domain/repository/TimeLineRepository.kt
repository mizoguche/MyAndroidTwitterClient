package info.mizoguche.mytwitterclient.domain.repository

import info.mizoguche.mytwitterclient.domain.collection.TimeLine
import info.mizoguche.mytwitterclient.domain.entity.UserId
import info.mizoguche.mytwitterclient.domain.entity.UserListId
import info.mizoguche.mytwitterclient.domain.factory.TweetFactory
import info.mizoguche.mytwitterclient.infrastructure.TwitterApi
import rx.Observable
import twitter4j.ResponseList
import twitter4j.Status

object  TimeLineRepository {
    fun fetchHomeTimeLine(): Observable<TimeLine> {
        return mapStatusListToTimeLine(TwitterApi.homeTimeLine())
    }

    fun fetchUserListTimeLine(userListId: UserListId): Observable<TimeLine> {
        return mapStatusListToTimeLine(TwitterApi.userListTimeLine(userListId))
    }

    fun fetchUserTimeLine(userId: UserId): Observable<TimeLine> {
        return mapStatusListToTimeLine(TwitterApi.userTimeLine(userId))
    }

    fun fetchMentionsTimeLine(): Observable<TimeLine> {
        return mapStatusListToTimeLine(TwitterApi.mentionsTimeLine())
    }

    private fun mapStatusListToTimeLine(responseList: Observable<ResponseList<Status>>) : Observable<TimeLine>{
        return responseList
                .map { it.map { TweetFactory.create(it) }.toList() }
                .map { TimeLine(it) }
    }
}
