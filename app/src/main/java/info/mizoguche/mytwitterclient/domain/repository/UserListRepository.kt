package info.mizoguche.mytwitterclient.domain.repository

import info.mizoguche.mytwitterclient.domain.collection.UserLists
import info.mizoguche.mytwitterclient.domain.factory.UserListFactory
import info.mizoguche.mytwitterclient.infrastructure.TwitterApi
import rx.Observable

object  UserListRepository {
    fun getUserLists(): Observable<UserLists> {
        return TwitterApi.ownLists()
                .map { it.map { UserListFactory.create(it) }.toList() }
                .map { UserLists(it) }
    }
}
