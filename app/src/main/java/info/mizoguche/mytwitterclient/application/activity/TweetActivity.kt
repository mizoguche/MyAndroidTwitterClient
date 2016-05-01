package info.mizoguche.mytwitterclient.application.activity

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import info.mizoguche.mytwitterclient.R
import info.mizoguche.mytwitterclient.application.view.picasso.PicassoUtil
import info.mizoguche.mytwitterclient.databinding.ActivityTweetBinding
import info.mizoguche.mytwitterclient.domain.entity.Tweet
import info.mizoguche.mytwitterclient.domain.service.TweetingService

private const val IntentKeyTweet = "tweet"

class TweetActivity : AppCompatActivity() {
    lateinit var binding: ActivityTweetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tweet)

        binding = DataBindingUtil.setContentView<ActivityTweetBinding>(this, R.layout.activity_tweet)
        val tweet = intent.getSerializableExtra(IntentKeyTweet) as Tweet
        binding.tweet = tweet
        setSupportActionBar(binding.toolBar)

        PicassoUtil.bindImage(this, binding.profileImage, tweet.tweetedBy.profileImageUrl.big)

        binding.btnLike.setOnClickListener {
            val progress = ProgressDialog(this)
            progress.show()
            TweetingService.like(tweet)
                    .subscribe {
                        progress.dismiss()
                    }
        }

        binding.btnRetweet.setOnClickListener {
            val progress = ProgressDialog(this)
            progress.show()
            TweetingService.retweet(tweet)
                    .subscribe {
                        progress.dismiss()
                    }
        }

        binding.btnReply.setOnClickListener {
            val intent = TweetingActivity.createIntent(this, tweet)
            startActivity(intent)
        }
    }

    companion object {
        fun createIntent (context: Context, tweet: Tweet) : Intent {
            val intent = Intent(context, TweetActivity::class.java)
            intent.putExtra(IntentKeyTweet, tweet)
            return intent
        }
    }
}
