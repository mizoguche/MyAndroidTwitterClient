package info.mizoguche.mytwitterclient.application.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import info.mizoguche.mytwitterclient.R
import info.mizoguche.mytwitterclient.application.adapter.TabsAdapter
import info.mizoguche.mytwitterclient.application.view.UserListDialog
import info.mizoguche.mytwitterclient.databinding.ActivityTabPreferencesBinding
import info.mizoguche.mytwitterclient.domain.collection.Tabs
import info.mizoguche.mytwitterclient.domain.repository.TabRepository

class TabPreferencesActivity: Activity() {
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
    }

    fun saveTabs(){
        TabRepository.putTabs(tabs)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tab_preferences_menu, menu)
        return true
    }

    override fun onMenuItemSelected(featureId: Int, item: MenuItem?): Boolean {
        if(item?.itemId == R.id.action_add_user_list){
            UserListDialog.show(this, { addedTabs ->
                tabs.addAll(addedTabs)
                TabRepository.putTabs(tabs)
                adapter.notifyDataSetChanged()
            })
        }
        return super.onMenuItemSelected(featureId, item)
    }

    companion object {
        fun createIntent (context: Context) : Intent {
            return Intent(context, TabPreferencesActivity::class.java)
        }
    }
}
