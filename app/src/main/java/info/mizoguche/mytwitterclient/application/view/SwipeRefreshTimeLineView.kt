package info.mizoguche.mytwitterclient.application.view

import android.content.Context
import android.util.AttributeSet
import info.mizoguche.mytwitterclient.application.adapter.TimeLineAdapter
import info.mizoguche.mytwitterclient.domain.value.Tab

class SwipeRefreshTimeLineView(context: Context, attrs: AttributeSet): SwipeRefreshCollectionView(context, attrs){
    lateinit var tab: Tab
    override fun fetchCollection() {
        tab.timeLine()
                .subscribe {
                    val adapter = TimeLineAdapter(context, it)
                    recyclerView.adapter = adapter
                    isRefreshing = false
                }
    }

    override fun getTitle(): String {
        return tab.name.value
    }
}
