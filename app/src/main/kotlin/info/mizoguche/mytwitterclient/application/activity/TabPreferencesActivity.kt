package info.mizoguche.mytwitterclient.application.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.ListView
import info.mizoguche.mytwitterclient.R
import info.mizoguche.mytwitterclient.application.adapter.TabsAdapter
import info.mizoguche.mytwitterclient.application.adapter.UserListsAdapter
import info.mizoguche.mytwitterclient.databinding.ActivityTabPreferencesBinding
import info.mizoguche.mytwitterclient.domain.collection.Tabs
import info.mizoguche.mytwitterclient.domain.repository.TabRepository
import info.mizoguche.mytwitterclient.domain.repository.UserListRepository
import info.mizoguche.mytwitterclient.domain.value.Tab
import info.mizoguche.mytwitterclient.domain.value.TabDetail
import info.mizoguche.mytwitterclient.domain.value.TabDetailUserList
import info.mizoguche.mytwitterclient.domain.value.TabName

class TabPreferencesActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_preferences)

        val binding = DataBindingUtil.setContentView<ActivityTabPreferencesBinding>(this, R.layout.activity_tab_preferences)
        val recyclerView = binding.recyclerView
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val tabs = TabRepository.getTabs()
        val adapter = TabsAdapter(this, tabs)
        recyclerView.adapter = adapter

        UserListRepository.getUserLists()
                .subscribe {
                    val listView = ListView(this)
                    val listAdapter = UserListsAdapter(this, it)
                    listView.adapter = listAdapter
                    AlertDialog.Builder(this)
                            .setTitle("Add User List Tab")
                            .setView(listView)
                            .setPositiveButton("Add", DialogInterface.OnClickListener { dialogInterface, i ->
                                listAdapter.checkedUserLists()
                                .forEach {
                                    tabs.add(Tab(TabName(it.name.value), TabDetail(TabDetailUserList, it.id.value)))
                                    adapter.notifyDataSetChanged()
                                    saveTabs(tabs)
                                }
                             })
                            .setNegativeButton("Cancel", null)
                            .show()
                }
    }

    fun saveTabs(tabs: Tabs){
        TabRepository.putTabs(tabs)
    }

    companion object {
        fun createIntent (context: Context) : Intent {
            return Intent(context, TabPreferencesActivity::class.java)
        }
    }
}
