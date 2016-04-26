package info.mizoguche.mytwitterclient.application.view

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import info.mizoguche.mytwitterclient.R
import info.mizoguche.mytwitterclient.application.adapter.ProgressViewAdapter
import info.mizoguche.mytwitterclient.application.decorator.DividerItemDecoration

abstract class SwipeRefreshCollectionView(context: Context, attrs: AttributeSet): SwipeRefreshLayout(context, attrs){
    protected abstract fun fetchCollection()
    abstract fun getTitle(): String

    lateinit var recyclerView: RecyclerView
    private var didFetch = false

    init {
        setColorSchemeResources(R.color.primary, R.color.primaryDark, R.color.accent, R.color.accentLight)
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

    fun fetch(force: Boolean = false){
        if(!force && didFetch){
            return
        }

        recyclerView.addItemDecoration(DividerItemDecoration(context))

        isRefreshing = true
        didFetch = true

        fetchCollection()
    }
}
