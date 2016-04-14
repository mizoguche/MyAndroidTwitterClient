package info.mizoguche.mytwitterclient.domain.factory

import info.mizoguche.mytwitterclient.domain.entity.Tweet
import twitter4j.Status

object  TweetFactory {
    fun create(status: Status): Tweet {
        return Tweet(status.text, status.user.screenName)
    }
}
