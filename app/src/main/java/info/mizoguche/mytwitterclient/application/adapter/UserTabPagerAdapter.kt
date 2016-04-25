package info.mizoguche.mytwitterclient.application.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import info.mizoguche.mytwitterclient.R
import info.mizoguche.mytwitterclient.application.view.SwipeRefreshTimeLineView
import info.mizoguche.mytwitterclient.domain.entity.User
import info.mizoguche.mytwitterclient.domain.value.Tab
import info.mizoguche.mytwitterclient.domain.value.TabDetail
import info.mizoguche.mytwitterclient.domain.value.TabDetailUserTimeLine
import info.mizoguche.mytwitterclient.domain.value.TabName
import java.util.*

class UserTabPagerAdapter(val user: User, val context: Context): PagerAdapter(){
    val tabViews: MutableList<SwipeRefreshTimeLineView> = ArrayList<SwipeRefreshTimeLineView>()
    init {
        val timeLineView = LayoutInflater.from(context)
                .inflate(R.layout.view_time_line, null) as SwipeRefreshTimeLineView
        timeLineView.tab = Tab(TabName("Tweets"), TabDetail(TabDetailUserTimeLine, user.id.value))
        tabViews.add(timeLineView)
    }

    override fun getCount(): Int {
        return 1
    }

    override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
        return view == `object`
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabViews[position].tab.name.value
    }

    override fun instantiateItem(container: ViewGroup?, position: Int): Any? {
        val view = tabViews[position]
        view.fetch()
        container?.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        container?.removeView(`object` as View)
    }
}

