package info.mizoguche.mytwitterclient.domain.factory

import info.mizoguche.mytwitterclient.domain.entity.TweetPrivacy
import info.mizoguche.mytwitterclient.domain.entity.User
import info.mizoguche.mytwitterclient.domain.entity.UserBuilder

object  UserFactory {
    fun create(user: twitter4j.User): User{
        val builder = UserBuilder(user.id)
        builder.screenName = user.screenName
        builder.userName = user.name
        builder.profileImageUrlBigger = user.biggerProfileImageURL
        builder.profileImageUrlMedium = user.profileImageURL
        builder.profileImageUrlSmall = user.miniProfileImageURL
        if(user.profileBannerURL != null){
            builder.profileBannerUrlBigger = user.profileBannerURL
            builder.profileBannerUrlMedium = user.profileBannerIPadRetinaURL
            builder.profileBannerUrlSmall = user.profileBannerMobileRetinaURL
        }
        builder.description = user.description
        builder.tweetPrivacy = TweetPrivacy.get(user.isProtected)
        return User(builder)
    }
}
