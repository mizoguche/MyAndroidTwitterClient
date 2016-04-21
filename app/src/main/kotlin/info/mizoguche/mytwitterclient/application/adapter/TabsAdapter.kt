package info.mizoguche.mytwitterclient.application.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import info.mizoguche.mytwitterclient.R
import info.mizoguche.mytwitterclient.databinding.ViewTabBinding
import info.mizoguche.mytwitterclient.domain.collection.Tabs
import info.mizoguche.mytwitterclient.domain.value.Tab


class TabViewHolder(view: View, val binding: ViewTabBinding) : RecyclerView.ViewHolder(view) {
    fun bind(tab: Tab){
        binding.tab = tab
        binding.executePendingBindings()
    }
}

class TabsAdapter(val context: Context, val tabs: Tabs) : RecyclerView.Adapter<TabViewHolder>() {
    override fun onBindViewHolder(holder: TabViewHolder?, position: Int) {
        holder?.bind(tabs[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TabViewHolder? {
        val inflater = LayoutInflater.from(context)
        val binding = DataBindingUtil.inflate<ViewTabBinding>(inflater, R.layout.view_tab, parent, false)
        val viewHolder = TabViewHolder(binding.root, binding)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return tabs.count()
    }
}

