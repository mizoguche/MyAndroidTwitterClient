package info.mizoguche.mytwitterclient.application.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import info.mizoguche.mytwitterclient.R
import info.mizoguche.mytwitterclient.application.adapter.TabsAdapter
import info.mizoguche.mytwitterclient.application.decorator.DividerItemDecoration
import info.mizoguche.mytwitterclient.application.view.UserListDialog
import info.mizoguche.mytwitterclient.databinding.ActivityTabPreferencesBinding
import info.mizoguche.mytwitterclient.domain.collection.Tabs
import info.mizoguche.mytwitterclient.domain.repository.TabRepository
import info.mizoguche.mytwitterclient.domain.value.Tab
import info.mizoguche.mytwitterclient.domain.value.TabDetail
import info.mizoguche.mytwitterclient.domain.value.TabDetailHome
import info.mizoguche.mytwitterclient.domain.value.TabName

class TabPreferencesActivity: AppCompatActivity() {
    lateinit var tabs: Tabs
    lateinit var adapter: TabsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_preferences)

        val binding = DataBindingUtil.setContentView<ActivityTabPreferencesBinding>(this, R.layout.activity_tab_preferences)
        val recyclerView = binding.recyclerView
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        tabs = TabRepository.getTabs()
        adapter = TabsAdapter(this, tabs)
        recyclerView.adapter = adapter

        val callback = object : ItemTouchHelper.Callback(){
            override fun getMovementFlags(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
                return makeFlag(ItemTouchHelper.ACTION_STATE_IDLE, ItemTouchHelper.RIGHT) or
                makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) or
                makeFlag(ItemTouchHelper.ACTION_STATE_DRAG, ItemTouchHelper.DOWN or ItemTouchHelper.UP)
            }

            override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
                val from = viewHolder?.adapterPosition as Int
                val to = target?.adapterPosition as Int
                adapter.move(from, to)
                saveTabs()
                return true
            }

            override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
                super.onSelectedChanged(viewHolder, actionState)
                if(actionState != ItemTouchHelper.ACTION_STATE_IDLE){
                    viewHolder?.itemView?.setBackgroundColor(Color.argb(64, 128, 128, 128))
                }
            }

            override fun clearView(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?) {
                super.clearView(recyclerView, viewHolder)
                viewHolder?.itemView?.setBackgroundColor(Color.TRANSPARENT)
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
                adapter.remove(viewHolder?.adapterPosition as Int)
                saveTabs()
            }
        }
        val helper = ItemTouchHelper(callback)
        helper.attachToRecyclerView(recyclerView)
        recyclerView.addItemDecoration(helper)
        recyclerView.addItemDecoration(DividerItemDecoration(this))

        setSupportActionBar(binding.toolBar)
    }

    fun saveTabs(){
        TabRepository.putTabs(tabs)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tab_preferences_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.action_add_user_list -> {
                UserListDialog.show(this, { addedTabs ->
                    tabs.addAll(addedTabs)
                    TabRepository.putTabs(tabs)
                    adapter.notifyDataSetChanged()
                })
            }
            R.id.action_add_home_time_line -> tabs.add(Tab(TabName("Home"), TabDetail(TabDetailHome, 1)))
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun createIntent (context: Context) : Intent {
            return Intent(context, TabPreferencesActivity::class.java)
        }
    }
}
