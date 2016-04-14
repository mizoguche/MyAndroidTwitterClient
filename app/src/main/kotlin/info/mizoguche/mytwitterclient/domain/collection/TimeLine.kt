package info.mizoguche.mytwitterclient.domain.collection

import info.mizoguche.mytwitterclient.domain.entity.Tweet

class TimeLine(tweets: List<Tweet>) : List<Tweet> by tweets {
    val tweets: List<Tweet> = tweets
}
