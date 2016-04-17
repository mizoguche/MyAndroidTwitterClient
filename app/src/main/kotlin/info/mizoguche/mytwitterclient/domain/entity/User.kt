package info.mizoguche.mytwitterclient.domain.entity

data class UserId(val value: Long)
data class ScreenName(val value: String)
data class UserName(val value: String)
data class ProfileImageUrl(val big: String, val medium: String, val small: String)

class UserBuilder(id: Long){
    val id: Long = id
    lateinit var screenName: String
    lateinit var userName: String
    lateinit var profileImageUrlBigger: String
    lateinit var profileImageUrlMedium: String
    lateinit var profileImageUrlSmall: String
}

class User(builder: UserBuilder) {
    val id: UserId = UserId(builder.id)
    val screenName: ScreenName = ScreenName(builder.screenName)
    val userName: UserName = UserName(builder.userName)
    val profileImageUrl: ProfileImageUrl = ProfileImageUrl(builder.profileImageUrlBigger, builder.profileImageUrlMedium, builder.profileImageUrlSmall)
}
