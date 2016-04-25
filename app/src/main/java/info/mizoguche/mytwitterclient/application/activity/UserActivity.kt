package info.mizoguche.mytwitterclient.application.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import com.squareup.picasso.Picasso
import info.mizoguche.mytwitterclient.R
import info.mizoguche.mytwitterclient.application.adapter.UserTabPagerAdapter
import info.mizoguche.mytwitterclient.databinding.ActivityUserBinding
import info.mizoguche.mytwitterclient.domain.entity.User

private const val IntentKeyUser = "user"

class UserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val binding = DataBindingUtil.setContentView<ActivityUserBinding>(this, R.layout.activity_user)
        val user = intent.getSerializableExtra(IntentKeyUser) as User
        if(user.profileBannerUrl.canLoad()) {
            Picasso.with(this)
                    .load(user.profileBannerUrl.big)
                    .into(binding.imageViewProfileBanner)
        }

        binding.viewPager.adapter = UserTabPagerAdapter(user, this)
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.tabLayout.setOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabUnselected(tab: TabLayout.Tab?) { }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.viewPager.setCurrentItem(tab?.position as Int, true)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) { }

        })
        setSupportActionBar(binding.toolBar)

//        val params = binding.floatingActionButton.layoutParams as CoordinatorLayout.LayoutParams
//        params.behavior = ScrollFABBehavior()
//        binding.floatingActionButton.layoutParams = params
//        binding.floatingActionButton.setOnClickListener {
//            startActivity(TweetingActivity.createIntent(this))
//        }
    }

    companion object {
        fun createIntent (context: Context, user: User) : Intent {
            val intent = Intent(context, UserActivity::class.java)
            intent.putExtra(IntentKeyUser, user)
            return intent
        }
    }
}