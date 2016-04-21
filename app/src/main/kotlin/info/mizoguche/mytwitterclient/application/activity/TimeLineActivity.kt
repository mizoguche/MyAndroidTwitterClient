package info.mizoguche.mytwitterclient.application.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import info.mizoguche.mytwitterclient.R
import info.mizoguche.mytwitterclient.application.adapter.TimeLineAdapter
import info.mizoguche.mytwitterclient.databinding.ActivityTimeLineBinding
import info.mizoguche.mytwitterclient.domain.repository.TimeLineRepository
import info.mizoguche.mytwitterclient.infrastructure.TwitterApi

class TimeLineActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_line)

        if(!TwitterApi.isOwnUserSaved()){
            finish()
            startActivity(OAuthCallbackActivity.createIntent(this))
            return
        }

        val binding = DataBindingUtil.setContentView<ActivityTimeLineBinding>(this, R.layout.activity_time_line)
        val recyclerView = binding.recyclerView
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        TimeLineRepository.fetchHomeTimeLine()
                .subscribe {
                    val adapter = TimeLineAdapter(this, it)
                    recyclerView.adapter = adapter
                }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.time_line_menu, menu)
        return true
    }

    override fun onMenuItemSelected(featureId: Int, item: MenuItem?): Boolean {
        if(item?.itemId == R.id.action_settings){
            val intent = TabPreferencesActivity.createIntent(this)
            startActivity(intent)
        }
        return super.onMenuItemSelected(featureId, item)
    }

    companion object {
        fun createIntent (context: Context) : Intent {
            return Intent(context, TimeLineActivity::class.java)
        }
    }
}
