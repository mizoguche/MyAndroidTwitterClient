package info.mizoguche.mytwitterclient.domain.collection

import info.mizoguche.mytwitterclient.domain.entity.Tweet

class TimeLine : Iterable<Tweet> {
    val tweets: List<Tweet>
        get

    constructor(tweets: List<Tweet>){
        this.tweets = tweets
    }

    override fun iterator(): Iterator<Tweet> {
        return tweets.iterator()
    }
}
