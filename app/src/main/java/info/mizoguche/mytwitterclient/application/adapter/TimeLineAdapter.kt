package info.mizoguche.mytwitterclient.application.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import info.mizoguche.mytwitterclient.R
import info.mizoguche.mytwitterclient.application.activity.ImagePreviewActivity
import info.mizoguche.mytwitterclient.application.activity.TweetActivity
import info.mizoguche.mytwitterclient.application.activity.UserActivity
import info.mizoguche.mytwitterclient.application.view.ViewHelper
import info.mizoguche.mytwitterclient.application.view.picasso.PicassoUtil
import info.mizoguche.mytwitterclient.databinding.ViewTweetBinding
import info.mizoguche.mytwitterclient.domain.collection.TimeLine
import info.mizoguche.mytwitterclient.domain.entity.MediaType
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

        binding.imageContainer.removeAllViews()
        tweet.mediaEntities.forEach {
            if(it.type == MediaType.Photo) {
                val media = it
                val imageView = View.inflate(context, R.layout.view_image_thumbnail, null) as ImageView
                ViewCompat.setTransitionName(imageView, "image")
                val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewHelper.calculatePixelFromDp(48, context))
                val margin = ViewHelper.calculatePixelFromDp(4, context)
                params.setMargins(0, margin, 0, margin)

                imageView.layoutParams = params
                imageView.setOnClickListener {
                    context.startActivity(ImagePreviewActivity.createIntent(context, tweet.mediaEntities, media))
                }
                binding.imageContainer.addView(imageView)
                PicassoUtil.bindImage(context, imageView, media.url.value)
                Log.d(this.javaClass.simpleName, "${media.url.value}")
            }
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

