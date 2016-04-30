package info.mizoguche.mytwitterclient.domain.service

import android.util.Log
import info.mizoguche.mytwitterclient.domain.entity.FollowingStatus
import info.mizoguche.mytwitterclient.domain.entity.User
import info.mizoguche.mytwitterclient.domain.entity.UserId
import info.mizoguche.mytwitterclient.infrastructure.TwitterApi
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.lang.kotlin.observable
import rx.schedulers.Schedulers
import twitter4j.Relationship

object RelationshipService {
    private val twitter = TwitterApi.twitter

    fun toggleFollowing(user: User) : Observable<Unit> {
        return observable<Unit> {
            val relationship = twitter.showFriendship(twitter.id, user.id.value)
            if((relationship as Relationship).isSourceFollowingTarget){
                twitter.destroyFriendship(user.id.value)
                user.isFollowee = FollowingStatus.NotFollowing
            }else{
                twitter.createFriendship(user.id.value)
                user.isFollowee = FollowingStatus.Following
            }
            it.onNext(Unit)
            it.onCompleted()
        }.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun isFollowing(userId: UserId) : Observable<FollowingStatus> {
        return observable<FollowingStatus> {
            val relationship = twitter.showFriendship(twitter.id, userId.value)
            it.onNext(FollowingStatus.get(relationship.isSourceFollowingTarget))
            it.onCompleted()
        }.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
        .onErrorReturn {
            Log.d(this.javaClass.simpleName, "", it)
            FollowingStatus.NotFetched
        }
    }
}

