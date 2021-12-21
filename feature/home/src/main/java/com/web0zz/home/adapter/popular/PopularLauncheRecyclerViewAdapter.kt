package com.web0zz.home.adapter.popular

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.web0zz.home.adapter.popular.viewholder.PopularBigLauncheItemViewHolder
import com.web0zz.home.adapter.popular.viewholder.PopularSmallLauncheItemViewHolder

class PopularLauncheRecyclerViewAdapter(
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
            is PopularBigLauncheItemViewHolder -> holder.bind()
            is PopularSmallLauncheItemViewHolder -> holder.bind()
            else -> throw Exception("Unknown view type exception")
        }
    }

    override fun getItemCount() = items.size
}