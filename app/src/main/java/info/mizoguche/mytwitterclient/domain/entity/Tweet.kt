package info.mizoguche.mytwitterclient.domain.entity

import android.databinding.BaseObservable
import info.mizoguche.mytwitterclient.domain.factory.UserFactory
import java.io.Serializable
import java.util.*

data class TweetId(val value: Long): Serializable
data class TweetText(val value: String): Serializable
data class TweetDate(val value: Date): Serializable
public enum class TweetType {
    Tweet, Retweet
}
public enum class RetweetStatus {
    None, Retweeted
}
public enum class LikeStatus {
    None, Liked
}

class Tweet(builder: TweetBuilder) : BaseObservable(), Serializable {
    val id: TweetId = TweetId(builder.id)
    val text: TweetText = TweetText(builder.text)
    val tweetedBy: User = UserFactory.create(builder.tweetedBy)
    val createdAt: TweetDate = TweetDate(builder.createdAt)
    val type: TweetType = builder.type
    val retweetedBy: User? = if(builder.retweetedBy != null) UserFactory.create(builder.retweetedBy as twitter4j.User) else null
    val retweetStatus: RetweetStatus = if(builder.isRetweeted) RetweetStatus.Retweeted else RetweetStatus.None
    val likeStatus: LikeStatus = if(builder.isLiked) LikeStatus.Liked else LikeStatus.None
}

class TweetBuilder(id: Long) {
    val id: Long = id
    lateinit  var text: String
    lateinit  var tweetedBy: twitter4j.User
    lateinit  var createdAt: Date
    lateinit  var type: TweetType
    var retweetedBy: twitter4j.User? = null
    var isRetweeted: Boolean = false
    var isLiked: Boolean = false

    fun build(): Tweet {
        return Tweet(this)
    }
}
