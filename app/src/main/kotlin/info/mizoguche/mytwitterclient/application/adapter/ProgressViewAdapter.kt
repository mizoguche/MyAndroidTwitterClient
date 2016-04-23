package info.mizoguche.mytwitterclient.application.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import info.mizoguche.mytwitterclient.R
import info.mizoguche.mytwitterclient.databinding.ViewProgressBinding


class ProgressViewHolder(view: View) : RecyclerView.ViewHolder(view) {
}

class ProgressViewAdapter(val context: Context) : RecyclerView.Adapter<ProgressViewHolder>() {
    override fun onBindViewHolder(holder: ProgressViewHolder?, position: Int) {
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ProgressViewHolder? {
        val inflater = LayoutInflater.from(context)
        val binding = DataBindingUtil.inflate<ViewProgressBinding>(inflater, R.layout.view_progress, parent, false)
        val viewHolder = ProgressViewHolder(binding.root)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return 1
    }
}

