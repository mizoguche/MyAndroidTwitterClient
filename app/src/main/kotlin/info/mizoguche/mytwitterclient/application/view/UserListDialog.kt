package info.mizoguche.mytwitterclient.application.view

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.widget.ListView
import info.mizoguche.mytwitterclient.application.adapter.UserListsAdapter
import info.mizoguche.mytwitterclient.domain.collection.Tabs
import info.mizoguche.mytwitterclient.domain.repository.UserListRepository
import info.mizoguche.mytwitterclient.domain.value.Tab
import info.mizoguche.mytwitterclient.domain.value.TabDetail
import info.mizoguche.mytwitterclient.domain.value.TabDetailUserList
import info.mizoguche.mytwitterclient.domain.value.TabName

object UserListDialog {
    fun show(context: Context, callback: (tabs: Tabs) -> Unit){
        val progressDialog = ProgressDialog(context)
        progressDialog.show()
        UserListRepository.getUserLists()
                .subscribe {
                    progressDialog.dismiss()
                    val listView = ListView(context)
                    val listAdapter = UserListsAdapter(context, it)
                    listView.adapter = listAdapter
                    showDialog(callback, context, listAdapter, listView)
                }
    }

    private fun showDialog(callback: (Tabs) -> Unit, context: Context, listAdapter: UserListsAdapter, listView: ListView) {
        AlertDialog.Builder(context)
                .setTitle("Add User List Tab")
                .setView(listView)
                .setPositiveButton("Add", { dialogInterface, i ->
                    val list = listAdapter
                            .checkedUserLists()
                            .map { Tab(TabName(it.name.value), TabDetail(TabDetailUserList, it.id.value)) }
                            .toMutableList()
                    callback(Tabs(list))
                })
                .setNegativeButton("Cancel", null)
                .show()
    }
}
