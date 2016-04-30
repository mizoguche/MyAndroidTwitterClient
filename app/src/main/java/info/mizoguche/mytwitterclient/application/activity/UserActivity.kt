package info.mizoguche.mytwitterclient.application.activity

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.graphics.Palette
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.gordonwong.materialsheetfab.MaterialSheetFab
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import info.mizoguche.mytwitterclient.R
import info.mizoguche.mytwitterclient.application.adapter.UserTabPagerAdapter
import info.mizoguche.mytwitterclient.application.view.AnimatedFloatingActionButton
import info.mizoguche.mytwitterclient.databinding.ActivityUserBinding
import info.mizoguche.mytwitterclient.domain.entity.User
import info.mizoguche.mytwitterclient.domain.service.RelationshipService

private const val IntentKeyUser = "user"

class UserActivity : AppCompatActivity() {
    lateinit var binding: ActivityUserBinding
    lateinit var materialSheetFab: MaterialSheetFab<AnimatedFloatingActionButton>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        binding = DataBindingUtil.setContentView<ActivityUserBinding>(this, R.layout.activity_user)
        val user = intent.getSerializableExtra(IntentKeyUser) as User
        user.fetchFollowingStatus()

        binding.user = user
        setColor(resources.getColor(R.color.primary, theme), resources.getColor(R.color.textOnPrimary, theme), resources.getColor(R.color.textWeakOnPrimary, theme))
        if(user.profileBannerUrl.canLoad()) {
            Picasso.with(this)
                    .load(user.profileImageUrl.big)
                    .into(binding.profileImage, object: Callback {
                        override fun onSuccess() {
                            val bitmap = (binding.profileImage.drawable as BitmapDrawable).bitmap
                            Palette.from(bitmap).generate {
                                if(it.swatches.size > 0){
                                    val swatch = it.swatches[0]
                                    setColor(swatch.rgb, swatch.titleTextColor, swatch.bodyTextColor)
                                }
                            }
                        }

                        override fun onError() { }
                    })
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

        val offsetThreshold = -300
        binding.appBarLayout.addOnOffsetChangedListener { appBarLayout, i ->
            if(i <= offsetThreshold && binding.floatingActionButton.visibility == View.VISIBLE){
                binding.floatingActionButton.hide()
            }else if(i > offsetThreshold && binding.floatingActionButton.visibility != View.VISIBLE){
                binding.floatingActionButton.show()
            }
        }


        materialSheetFab = MaterialSheetFab<AnimatedFloatingActionButton>(binding.floatingActionButton,
                binding.fabSheet, binding.overlay, resources.getColor(R.color.bg, theme), resources.getColor(R.color.accent, theme))

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, resources.getStringArray(R.array.user_activity_actions))
        binding.listView.adapter = adapter
        binding.listView.setOnItemClickListener { adapterView, view, i, l ->
            val progress = ProgressDialog(this)
            when(i){
                0 -> {
                    progress.show()
                    RelationshipService.toggleFollowing(user)
                            .subscribe {
                                progress.dismiss()
                            }
                }
                1-> {
                    Toast.makeText(this, "Not implemented yet", Toast.LENGTH_SHORT).show()
                }
            }
            materialSheetFab.hideSheet()
        }
    }

    private fun setColor(bgColor: Int, textColor: Int, weakTextColor: Int){
        binding.collapsingToolbarLayout.setCollapsedTitleTextColor(textColor)
        binding.collapsingToolbarLayout.setContentScrimColor(bgColor)
        binding.tabLayout.setBackgroundColor(bgColor)
        binding.tabLayout.setTabTextColors(weakTextColor, textColor)
        binding.tabLayout.setSelectedTabIndicatorColor(textColor)
        binding.profileContainer.setBackgroundColor(bgColor)
        binding.userName.setTextColor(textColor)
        binding.screenName.setTextColor(weakTextColor)
        binding.isFollowee.setTextColor(weakTextColor)
        binding.userDescription.setTextColor(textColor)
    }

    companion object {
        fun createIntent (context: Context, user: User) : Intent {
            val intent = Intent(context, UserActivity::class.java)
            intent.putExtra(IntentKeyUser, user)
            return intent
        }
    }
}
