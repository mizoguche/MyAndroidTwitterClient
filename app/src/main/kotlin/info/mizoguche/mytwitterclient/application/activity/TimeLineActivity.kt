package info.mizoguche.mytwitterclient.application.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import info.mizoguche.mytwitterclient.domain.repository.TimeLineRepository
import info.mizoguche.mytwitterclient.infrastructure.TwitterApi

class TimeLineActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(!TwitterApi.isOwnUserSaved()){
            finish()
            startActivity(OAuthCallbackActivity.createIntent(this))
            return
        }

        Log.d("MyTwitterClient", "Fetching home time line")

        TimeLineRepository.getHomeTimeLine()
                .subscribe {
                    it.forEach {
                        Log.d("MyTwitterClient", "${it.username}: ${it.text}")
                    }
                }
    }

    companion object {
        fun createIntent (context: Context) : Intent {
            return Intent(context, TimeLineActivity::class.java)
        }
    }
}
