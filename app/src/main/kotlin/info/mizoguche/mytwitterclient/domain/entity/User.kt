package info.mizoguche.mytwitterclient.domain.entity

data class UserId(val value: Long)
data class ScreenName(val value: String)
data class UserName(val value: String)

class User(id: UserId, screenName: ScreenName, userName: UserName) {
    val id: UserId = id
    val screenName: ScreenName = screenName
    val userName: UserName = userName
}
