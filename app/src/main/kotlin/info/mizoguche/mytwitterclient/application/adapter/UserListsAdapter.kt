package info.mizoguche.mytwitterclient.application.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import info.mizoguche.mytwitterclient.R
import info.mizoguche.mytwitterclient.databinding.ViewUserListBinding
import info.mizoguche.mytwitterclient.domain.collection.UserLists
import info.mizoguche.mytwitterclient.domain.entity.UserList

class UserListViewHolder(view: View, binding: ViewUserListBinding) {
    val context: Context = view.context
    val binding: ViewUserListBinding = binding

    fun bind(userList: UserList){
        binding.userList = userList
        binding.executePendingBindings()
    }
}

class UserListsAdapter(context: Context, userLists: UserLists): BaseAdapter() {
    val context = context
    val userLists = userLists

    override fun getCount(): Int {
        return userLists.count()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        if(convertView == null){
            val inflater = LayoutInflater.from(context)
            val binding = DataBindingUtil.inflate<ViewUserListBinding>(inflater, R.layout.view_user_list, parent, false)
            val viewHolder = UserListViewHolder(binding.root, binding)
            viewHolder.bind(userLists[position])
            binding.root.tag = viewHolder
            return binding.root
        }

        val viewHolder = convertView.tag as UserListViewHolder
        viewHolder.bind(userLists[position])
        return convertView
    }

    override fun getItem(position: Int): Any? {
        return userLists[position]
    }

    override fun getItemId(position: Int): Long {
        return userLists[position].id.value
    }

}
