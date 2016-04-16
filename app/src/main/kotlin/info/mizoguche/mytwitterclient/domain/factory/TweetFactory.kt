package info.mizoguche.mytwitterclient.domain.factory

import info.mizoguche.mytwitterclient.domain.entity.Tweet
import info.mizoguche.mytwitterclient.domain.entity.TweetBuilder
import info.mizoguche.mytwitterclient.domain.entity.TweetType
import twitter4j.Status

object  TweetFactory {
    fun create(status: Status): Tweet {
        val builder = TweetBuilder()
        if(status.isRetweet){
            builder.type = TweetType.Retweet
            builder.retweetedBy = status.user
            assignToBuilder(builder, status.retweetedStatus)
            return builder.build()
        }

        builder.type = TweetType.Tweet
        assignToBuilder(builder, status)
        return builder.build()
    }

    private fun assignToBuilder(builder: TweetBuilder, status: Status) {
        builder.id = status.id
        builder.text = status.text
        builder.tweetedBy = status.user
        builder.createdAt = status.createdAt
    }
}
