package info.mizoguche.mytwitterclient.application.adapter

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.DataBindingUtil
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CompoundButton
import info.mizoguche.mytwitterclient.R
import info.mizoguche.mytwitterclient.databinding.ViewUserListBinding
import info.mizoguche.mytwitterclient.domain.collection.UserLists
import info.mizoguche.mytwitterclient.domain.entity.UserList
import java.util.*

class UserListSelection(var isSelected: Boolean, val userList: UserList): BaseObservable()

class UserListViewHolder(binding: ViewUserListBinding) {
    val binding: ViewUserListBinding = binding

    fun bind(userListSelection: UserListSelection){
        binding.userListCheckbox.setOnCheckedChangeListener(null)
        binding.userListSelection = userListSelection
        binding.executePendingBindings()
        binding.userListCheckbox.setOnCheckedChangeListener(
                CompoundButton.OnCheckedChangeListener { compoundButton, b ->
                    userListSelection.isSelected = b
                    binding.executePendingBindings()
                    Log.d("MyTwitterClient", "checked: ${b}, selection: ${userListSelection.userList.name}")
                })
    }
}

class UserListsAdapter(val context: Context, val userLists: UserLists): BaseAdapter() {
    val selections: MutableMap<UserList, UserListSelection> = HashMap<UserList, UserListSelection>()

    override fun getCount(): Int {
        return userLists.count()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val userList = userLists[position]
        val selection = if(selections.containsKey(userList)){
            selections[userList] as UserListSelection
        }else{
            val s = UserListSelection(false, userLists[position])
            selections[userList] = s
            s
        }

        if(convertView == null){
            val inflater = LayoutInflater.from(context)
            val binding = DataBindingUtil.inflate<ViewUserListBinding>(inflater, R.layout.view_user_list, parent, false)
            val viewHolder = UserListViewHolder(binding)
            viewHolder.bind(selection)
            binding.root.tag = viewHolder
            return binding.root
        }

        val viewHolder = convertView.tag as UserListViewHolder
        viewHolder.bind(selection)
        return convertView
    }

    override fun getItem(position: Int): Any? {
        return userLists[position]
    }

    override fun getItemId(position: Int): Long {
        return userLists[position].id.value
    }

    fun checkedUserLists(): UserLists {
        val list = selections.filterValues { it.isSelected }
                .map { it.key }
        return UserLists(list)
    }

}
