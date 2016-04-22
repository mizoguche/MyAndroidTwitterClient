package info.mizoguche.mytwitterclient.application.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import info.mizoguche.mytwitterclient.R
import info.mizoguche.mytwitterclient.application.adapter.TabPagerAdapter
import info.mizoguche.mytwitterclient.databinding.ActivityTimeLineBinding
import info.mizoguche.mytwitterclient.domain.repository.TabRepository
import info.mizoguche.mytwitterclient.infrastructure.TwitterApi

class TimeLineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_line)

        if(!TwitterApi.isOwnUserSaved()){
            finish()
            startActivity(OAuthCallbackActivity.createIntent(this))
            return
        }

        val binding = DataBindingUtil.setContentView<ActivityTimeLineBinding>(this, R.layout.activity_time_line)
        val tabs = TabRepository.getTabs()
        binding.viewPager.adapter = TabPagerAdapter(tabs, this)
        binding.tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.tabLayout.setOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabUnselected(tab: TabLayout.Tab?) { }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.viewPager.setCurrentItem(tab?.position as Int, true)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) { }

        })
        setSupportActionBar(binding.toolBar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.time_line_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.action_settings){
            val intent = TabPreferencesActivity.createIntent(this)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun createIntent (context: Context) : Intent {
            return Intent(context, TimeLineActivity::class.java)
        }
    }
}
