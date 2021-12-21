package com.web0zz.home.adapter.popular.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.web0zz.home.databinding.ViewItemPopularLaunchesBinding

class PopularSmallLauncheItemViewHolder(
    private val binding: ViewItemPopularLaunchesBinding,
    private val onLauncheCLicked: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind() {

    }

    companion object {
        fun create(
            parent: ViewGroup,
            onLauncheCLicked: () -> Unit
        ): PopularSmallLauncheItemViewHolder {
            val view = ViewItemPopularLaunchesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return PopularSmallLauncheItemViewHolder(view, onLauncheCLicked)
        }
    }
}