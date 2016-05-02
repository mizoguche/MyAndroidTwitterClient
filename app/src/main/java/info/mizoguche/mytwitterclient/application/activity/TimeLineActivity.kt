package info.mizoguche.mytwitterclient.application.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import info.mizoguche.mytwitterclient.R
import info.mizoguche.mytwitterclient.application.adapter.TabPagerAdapter
import info.mizoguche.mytwitterclient.databinding.ActivityTimeLineBinding
import info.mizoguche.mytwitterclient.domain.repository.TabRepository
import info.mizoguche.mytwitterclient.infrastructure.TwitterApi
import jp.tsur.booksearch.ui.ScrollFABBehavior

class TimeLineActivity : AppCompatActivity() {
    lateinit var binding: ActivityTimeLineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_line)

        if(!TwitterApi.isOwnUserSaved()){
            finish()
            startActivity(OAuthCallbackActivity.createIntent(this))
            return
        }

        binding = DataBindingUtil.setContentView<ActivityTimeLineBinding>(this, R.layout.activity_time_line)
        updateTabs()
        binding.tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        binding.tabLayout.setOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabUnselected(tab: TabLayout.Tab?) { }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.viewPager.setCurrentItem(tab?.position as Int, true)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) { }

        })
        setSupportActionBar(binding.toolBar)

        val params = binding.floatingActionButton.layoutParams as CoordinatorLayout.LayoutParams
        params.behavior = ScrollFABBehavior()
        binding.floatingActionButton.layoutParams = params
        binding.floatingActionButton.setOnClickListener {
            startActivity(TweetingActivity.createIntent(this))
        }
    }

    private fun updateTabs() {
        val tabs = TabRepository.getTabs()
        binding.viewPager.adapter = TabPagerAdapter(tabs, this)
        binding.viewPager.adapter.notifyDataSetChanged()
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    override fun onResume() {
        super.onResume()
        if(TabRepository.isUpdated()){
            updateTabs()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.time_line_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.action_settings -> {
                startActivity(TabPreferencesActivity.createIntent(this))
            }
            R.id.action_licenses -> {
                startActivity(LicensesActivity.createIntent(this))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun createIntent (context: Context) : Intent {
            return Intent(context, TimeLineActivity::class.java)
        }
    }
}
