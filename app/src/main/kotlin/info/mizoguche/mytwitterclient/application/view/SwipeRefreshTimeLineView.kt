package info.mizoguche.mytwitterclient.application.view

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import info.mizoguche.mytwitterclient.R
import info.mizoguche.mytwitterclient.application.adapter.ProgressViewAdapter
import info.mizoguche.mytwitterclient.application.adapter.TimeLineAdapter
import info.mizoguche.mytwitterclient.application.decorator.DividerItemDecoration
import info.mizoguche.mytwitterclient.domain.value.Tab

class SwipeRefreshTimeLineView(context: Context, attrs: AttributeSet): SwipeRefreshLayout(context, attrs){
    lateinit var recyclerView: RecyclerView
    init {
        setOnRefreshListener {
            fetch(true)
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        recyclerView = findViewById(R.id.recycler_view) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = ProgressViewAdapter(context)
    }

    lateinit var tab: Tab
    var didFetch = false

    fun fetch(force: Boolean = false){
        if(!force && didFetch){
            return
        }

        recyclerView.addItemDecoration(DividerItemDecoration(context))

        isRefreshing = true
        didFetch = true

        tab.timeLine()
                .subscribe {
                    val adapter = TimeLineAdapter(context, it)
                    recyclerView.adapter = adapter
                    isRefreshing = false
                }
    }
}
