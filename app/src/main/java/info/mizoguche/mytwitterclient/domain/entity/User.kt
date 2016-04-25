package info.mizoguche.mytwitterclient.domain.entity

import java.io.Serializable

data class UserId(val value: Long): Serializable
data class ScreenName(val value: String): Serializable
data class UserName(val value: String): Serializable
data class ProfileImageUrl(val big: String, val medium: String, val small: String): Serializable
data class ProfileBannerUrl(val big: String, val medium: String, val small: String): Serializable {
    fun canLoad(): Boolean { return medium.length > 0 }
}

class User(builder: UserBuilder) : Serializable {
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
}
