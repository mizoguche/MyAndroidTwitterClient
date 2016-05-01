package info.mizoguche.mytwitterclient.application.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import info.mizoguche.mytwitterclient.R
import info.mizoguche.mytwitterclient.application.activity.TweetActivity
import info.mizoguche.mytwitterclient.application.activity.UserActivity
import info.mizoguche.mytwitterclient.application.view.picasso.PicassoUtil
import info.mizoguche.mytwitterclient.databinding.ViewTweetBinding
import info.mizoguche.mytwitterclient.domain.collection.TimeLine
import info.mizoguche.mytwitterclient.domain.entity.Tweet
import info.mizoguche.mytwitterclient.domain.entity.TweetType


class TweetViewHolder(view: View, binding: ViewTweetBinding) : RecyclerView.ViewHolder(view) {
    val context: Context = view.context
    val binding: ViewTweetBinding = binding

    fun bind(tweet: Tweet){
        binding.tweet = tweet
        PicassoUtil.bindImage(context, binding.profileImage, tweet.tweetedBy.profileImageUrl.big)
        binding.profileImage.setOnClickListener {
            context.startActivity(UserActivity.createIntent(context, tweet.tweetedBy))
        }
        if(tweet.type == TweetType.Retweet){
            PicassoUtil.bindImage(context, binding.profileImageRetweetedBy, tweet.retweetedBy?.profileImageUrl?.medium as String)
        }
        binding.executePendingBindings()
        binding.tweetContainer.setOnClickListener {
            val intent = TweetActivity.createIntent(context, tweet)
            context.startActivity(intent)
        }
    }
}

class TimeLineAdapter(context: Context, timeLine: TimeLine) : RecyclerView.Adapter<TweetViewHolder>() {
    val context: Context = context
    val timeLine: TimeLine = timeLine

    override fun onBindViewHolder(holder: TweetViewHolder?, position: Int) {
        holder?.bind(timeLine[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TweetViewHolder? {
        val inflater = LayoutInflater.from(context)
        val binding = DataBindingUtil.inflate<ViewTweetBinding>(inflater, R.layout.view_tweet, parent, false)
        val viewHolder = TweetViewHolder(binding.root, binding)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return timeLine.count()
    }
}

