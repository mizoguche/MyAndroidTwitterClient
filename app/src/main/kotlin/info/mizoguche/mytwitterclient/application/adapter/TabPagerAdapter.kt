package info.mizoguche.mytwitterclient.application.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import info.mizoguche.mytwitterclient.R
import info.mizoguche.mytwitterclient.application.decorator.DividerItemDecoration
import info.mizoguche.mytwitterclient.domain.collection.Tabs
import java.util.*

class TabPagerAdapter(val tabs: Tabs, val context: Context): PagerAdapter(){
    val tabViews: MutableList<RecyclerView> = ArrayList<RecyclerView>()
    init {
        tabs.forEach {
            val recyclerView = LayoutInflater.from(context).inflate(R.layout.view_time_line, null)
                    .findViewById(R.id.recycler_view) as RecyclerView
            val layoutManager = LinearLayoutManager(context)
            recyclerView.layoutManager = layoutManager
            recyclerView.addItemDecoration(DividerItemDecoration(context))
            it.timeLine()
                    .subscribe {
                        val adapter = TimeLineAdapter(context, it)
                        recyclerView.adapter = adapter
                        Log.d("MyTwitterClient", "it: ${it.tweets}")
                    }
            tabViews.add(recyclerView)
        }
    }

    override fun getCount(): Int {
        return tabs.count()
    }

    override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
        return view == `object`
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabs[position].name.value
    }

    override fun instantiateItem(container: ViewGroup?, position: Int): Any? {
        val view = tabViews[position]
        container?.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        container?.removeView(`object` as View)
    }
}

