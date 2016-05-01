package info.mizoguche.mytwitterclient.application.activity

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatEditText
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import info.mizoguche.mytwitterclient.R
import info.mizoguche.mytwitterclient.databinding.ActivityTweetingBinding
import info.mizoguche.mytwitterclient.domain.entity.Tweet
import info.mizoguche.mytwitterclient.domain.entity.TweetText
import info.mizoguche.mytwitterclient.domain.service.TweetingService

private const val IntentKeyReplyTo: String = "replyTo"

class TweetingActivity: AppCompatActivity() {
    lateinit var editText: AppCompatEditText
    var replyTo: Tweet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tweeting)

        replyTo = intent.getSerializableExtra(IntentKeyReplyTo) as Tweet?

        val binding = DataBindingUtil.setContentView<ActivityTweetingBinding>(this, R.layout.activity_tweeting)
        editText = binding.editTextTweet

        setSupportActionBar(binding.toolBar)

        if(replyTo != null){
            editText.setText("@${(replyTo as Tweet).tweetedBy.screenName.value} ")
            editText.setSelection(editText.text.length)
        }

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tweeting_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.action_send){
            val progressView = ProgressDialog(this)
            progressView.setMessage("Tweeting...")
            progressView.show()
            if(replyTo == null) {
                TweetingService.tweet(TweetText(editText.text.toString()))
                        .subscribe {
                            progressView.dismiss()
                            finish()
                        }
            } else {
                TweetingService.reply(TweetText(editText.text.toString()), replyTo as Tweet)
                        .subscribe {
                            progressView.dismiss()
                            finish()
                        }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun createIntent (context: Context, replyTo: Tweet? = null) : Intent {
            val intent = Intent(context, TweetingActivity::class.java)
            intent.putExtra(IntentKeyReplyTo, replyTo)
            return intent
        }
    }
}
