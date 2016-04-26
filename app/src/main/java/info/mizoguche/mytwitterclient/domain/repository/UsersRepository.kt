package info.mizoguche.mytwitterclient.domain.repository

import android.util.Log
import info.mizoguche.mytwitterclient.domain.collection.Users
import info.mizoguche.mytwitterclient.domain.entity.UserId
import info.mizoguche.mytwitterclient.domain.factory.UserFactory
import info.mizoguche.mytwitterclient.infrastructure.TwitterApi
import rx.Observable

object  UsersRepository {
    fun fetchFollowees(userId: UserId): Observable<Users> {
        return TwitterApi.followees(userId)
                .map { Log.d("MyTwitterClient", "${this.javaClass.canonicalName}: ${it.size}"); it.map { UserFactory.create(it) }.toList() }
                .map { Users(it) }
    }

    fun fetchFollowers(userId: UserId): Observable<Users> {
        return TwitterApi.followers(userId)
                .map { it.map { UserFactory.create(it) }.toList() }
                .map { Users(it) }
    }
}
