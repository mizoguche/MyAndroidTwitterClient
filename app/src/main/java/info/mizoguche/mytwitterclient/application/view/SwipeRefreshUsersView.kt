package info.mizoguche.mytwitterclient.application.view

import android.content.Context
import android.util.AttributeSet
import info.mizoguche.mytwitterclient.application.adapter.UsersAdapter
import info.mizoguche.mytwitterclient.domain.collection.Users
import info.mizoguche.mytwitterclient.domain.entity.User
import info.mizoguche.mytwitterclient.domain.entity.UserId
import info.mizoguche.mytwitterclient.domain.repository.UsersRepository
import rx.Observable

enum class UsersType {
    Follower {
        override fun fetchUsers(userId: UserId): Observable<Users> {
            return UsersRepository.fetchFollowers(userId)
        }
    },
    Followee {
        override fun fetchUsers(userId: UserId): Observable<Users> {
            return UsersRepository.fetchFollowees(userId)
        }
    };

    abstract fun fetchUsers(userId: UserId): Observable<Users>
}
data class UsersTab(val user: User, val usersType: UsersType){
    fun fetchUsers(): Observable<Users>{
        return usersType.fetchUsers(user.id)
    }
}

class SwipeRefreshUsersView(context: Context, attrs: AttributeSet): SwipeRefreshCollectionView(context, attrs){
    lateinit var usersTab: UsersTab
    override fun fetchCollection() {
        usersTab.fetchUsers()
                .subscribe {
                    val adapter = UsersAdapter(context, it)
                    recyclerView.adapter = adapter
                    isRefreshing = false
                }
    }

    override fun getTitle(): String {
        return usersTab.usersType.name
    }
}
