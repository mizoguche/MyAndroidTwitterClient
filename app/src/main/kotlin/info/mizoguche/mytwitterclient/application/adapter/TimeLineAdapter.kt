package info.mizoguche.mytwitterclient.application.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import info.mizoguche.mytwitterclient.R
import info.mizoguche.mytwitterclient.databinding.ViewTweetBinding
import info.mizoguche.mytwitterclient.domain.collection.TimeLine


class TweetViewHolder(view: View, binding: ViewTweetBinding) : RecyclerView.ViewHolder(view) {
    val binding: ViewTweetBinding = binding
}

class TimeLineAdapter(context: Context, timeLine: TimeLine) : RecyclerView.Adapter<TweetViewHolder>() {
    val context: Context = context
    val timeLine: TimeLine = timeLine

    override fun onBindViewHolder(holder: TweetViewHolder?, position: Int) {
        if(holder != null){
            holder.binding.tweet = timeLine[position]
            holder.binding.executePendingBindings()
        }
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

