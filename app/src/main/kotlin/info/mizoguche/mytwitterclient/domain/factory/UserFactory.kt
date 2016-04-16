package info.mizoguche.mytwitterclient.domain.factory

import info.mizoguche.mytwitterclient.domain.entity.ScreenName
import info.mizoguche.mytwitterclient.domain.entity.User
import info.mizoguche.mytwitterclient.domain.entity.UserId
import info.mizoguche.mytwitterclient.domain.entity.UserName

object  UserFactory {
    fun create(user: twitter4j.User): User{
        return User(UserId(user.id),
                ScreenName(user.screenName),
                UserName(user.name))
    }
}
