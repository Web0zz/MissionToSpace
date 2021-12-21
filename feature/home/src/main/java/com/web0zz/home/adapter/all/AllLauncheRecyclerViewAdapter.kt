package com.web0zz.home.adapter.all

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.web0zz.home.adapter.all.viewholder.AllBigLauncheItemViewHolder
import com.web0zz.home.adapter.all.viewholder.AllSmallLauncheItemViewHolder

class AllLauncheRecyclerViewAdapter(
    private val items: List<Int>,
    private val onLauncheClicked: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemViewType(position: Int): Int = items[position] // TODO get viewtype int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            // TODO
            else -> throw Exception("Unknown view type exception")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is AllBigLauncheItemViewHolder -> holder.bind()
            is AllSmallLauncheItemViewHolder -> holder.bind()
            else -> throw Exception("Unknown view type exception")
        }
    }

    override fun getItemCount() = items.size
}