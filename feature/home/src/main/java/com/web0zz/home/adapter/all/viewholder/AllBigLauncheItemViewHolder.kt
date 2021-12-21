package com.web0zz.home.adapter.all.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.web0zz.home.databinding.ViewItemAllLaunchesBinding

class AllBigLauncheItemViewHolder(
    private val binding: ViewItemAllLaunchesBinding,
    private val onLauncheCLicked: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind() {

    }

    companion object {
        fun create(
            parent: ViewGroup,
            onLauncheCLicked: () -> Unit
        ): AllBigLauncheItemViewHolder {
            val view = ViewItemAllLaunchesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return AllBigLauncheItemViewHolder(view, onLauncheCLicked)
        }
    }
}