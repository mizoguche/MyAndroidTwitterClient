package info.mizoguche.mytwitterclient.domain.entity

import android.databinding.BaseObservable
import info.mizoguche.mytwitterclient.domain.service.RelationshipService
import java.io.Serializable

data class UserId(val value: Long): Serializable
data class ScreenName(val value: String): Serializable
data class UserName(val value: String): Serializable
data class ProfileImageUrl(val big: String, val medium: String, val small: String): Serializable
data class ProfileBannerUrl(val big: String, val medium: String, val small: String): Serializable {
    fun canLoad(): Boolean { return medium.length > 0 }
}
data class UserDescription(val value: String): Serializable
enum class FollowingStatus {
    NotFetched, Following, NotFollowing;
    companion object{
        fun get(isFollowing: Boolean): FollowingStatus {
            return if(isFollowing) Following  else NotFollowing
        }
    }
}

class User(builder: UserBuilder) : BaseObservable(), Serializable {
    val id: UserId = UserId(builder.id)
    val screenName: ScreenName = ScreenName(builder.screenName)
    val userName: UserName = UserName(builder.userName)
    val profileImageUrl: ProfileImageUrl = ProfileImageUrl(builder.profileImageUrlBigger, builder.profileImageUrlMedium, builder.profileImageUrlSmall)
    val profileBannerUrl: ProfileBannerUrl
    init {
        if(builder.profileBannerUrlMedium != null) {
            profileBannerUrl = ProfileBannerUrl(builder.profileBannerUrlBigger as String, builder.profileBannerUrlMedium as String, builder.profileBannerUrlSmall as String)
        }else {
            profileBannerUrl = ProfileBannerUrl("", "", "")
        }
    }
    val description: UserDescription = UserDescription(builder.description)

    private var _isFollowee: FollowingStatus = FollowingStatus.NotFetched
    var isFollowee: FollowingStatus
        get() = _isFollowee
        set(value) {
            _isFollowee = value
            notifyChange()
        }

    fun fetchFollowingStatus() {
        RelationshipService.isFollowing(id)
                .subscribe { isFollowee = it }
    }
}

class UserBuilder(id: Long){
    val id: Long = id
    lateinit var screenName: String
    lateinit var userName: String
    lateinit var profileImageUrlBigger: String
    lateinit var profileImageUrlMedium: String
    lateinit var profileImageUrlSmall: String
    var profileBannerUrlBigger: String? = null
    var profileBannerUrlMedium: String? = null
    var profileBannerUrlSmall: String? = null
    lateinit var description: String
}
