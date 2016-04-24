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
import info.mizoguche.mytwitterclient.R
import info.mizoguche.mytwitterclient.databinding.ActivityTweetingBinding
import info.mizoguche.mytwitterclient.domain.entity.TweetText
import info.mizoguche.mytwitterclient.domain.service.TweetingService

class TweetingActivity: AppCompatActivity() {
    lateinit var editText: AppCompatEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tweeting)

        val binding = DataBindingUtil.setContentView<ActivityTweetingBinding>(this, R.layout.activity_tweeting)
        editText = binding.editTextTweet

        setSupportActionBar(binding.toolBar)
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
            TweetingService.tweet(TweetText(editText.text.toString()))
                    .subscribe {
                        progressView.dismiss()
                        finish()
                    }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun createIntent (context: Context) : Intent {
            return Intent(context, TweetingActivity::class.java)
        }
    }
}
