package info.mizoguche.mytwitterclient.application.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import info.mizoguche.mytwitterclient.R
import info.mizoguche.mytwitterclient.application.activity.UserActivity
import info.mizoguche.mytwitterclient.databinding.ViewUserBinding
import info.mizoguche.mytwitterclient.domain.collection.Users
import info.mizoguche.mytwitterclient.domain.entity.User


class UserViewHolder(var view: View, var binding: ViewUserBinding) : RecyclerView.ViewHolder(view) {
    val context = view.context

    fun bind(user: User){
        binding.user = user
        bindImage(binding.profileImage, user.profileImageUrl.big)
        binding.profileImage.setOnClickListener {
            context.startActivity(UserActivity.createIntent(context, user))
        }
        binding.executePendingBindings()
    }

    private fun bindImage(view: ImageView, url: String){
        Picasso.with(context)
                .load(url)
                .into(view)
    }
}

class UsersAdapter(val context: Context, val users: Users) : RecyclerView.Adapter<UserViewHolder>() {
    override fun onBindViewHolder(holder: UserViewHolder?, position: Int) {
        holder?.bind(users[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): UserViewHolder? {
        val inflater = LayoutInflater.from(context)
        val binding = DataBindingUtil.inflate<ViewUserBinding>(inflater, R.layout.view_user, parent, false)
        val viewHolder = UserViewHolder(binding.root, binding)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return users.count()
    }
}

