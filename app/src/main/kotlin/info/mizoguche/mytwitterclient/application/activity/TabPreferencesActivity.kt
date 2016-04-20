package info.mizoguche.mytwitterclient.application.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.ListView
import info.mizoguche.mytwitterclient.R
import info.mizoguche.mytwitterclient.application.adapter.UserListsAdapter
import info.mizoguche.mytwitterclient.databinding.ActivityTabPreferencesBinding
import info.mizoguche.mytwitterclient.domain.repository.UserListRepository

class TabPreferencesActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_preferences)

        val binding = DataBindingUtil.setContentView<ActivityTabPreferencesBinding>(this, R.layout.activity_tab_preferences)
        val recyclerView = binding.recyclerView
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        UserListRepository.getUserLists()
                .subscribe {
                    val listView = ListView(this)
                    val adapter = UserListsAdapter(this, it)
                    listView.adapter = adapter
                    AlertDialog.Builder(this)
                            .setTitle("User Lists")
                            .setView(listView)
                            .show()
                }
    }

    companion object {
        fun createIntent (context: Context) : Intent {
            return Intent(context, TabPreferencesActivity::class.java)
        }
    }
}
