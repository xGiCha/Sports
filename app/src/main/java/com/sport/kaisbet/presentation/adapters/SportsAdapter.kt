package com.sport.kaisbet.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.sport.kaisbet.databinding.LayoutSportItemBinding
import com.sport.kaisbet.domain.models.Event
import com.sport.kaisbet.domain.models.SportUi
import com.sport.kaisbet.presentation.viewHolder.SportsViewHolder

class SportsAdapter(
    private val callback: (isCollapsed: Boolean, position: Int) -> Unit,
    private val callBackEvent: (hasFavorite: Boolean, event: Event) -> Unit,
    private val callBackSwitch: (switchState: Boolean, sportUi: SportUi) -> Unit
) : ListAdapter<SportUi, SportsViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SportsViewHolder {
        val binding: LayoutSportItemBinding = LayoutSportItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SportsViewHolder(binding, callback, callBackEvent, callBackSwitch)
    }

    override fun onBindViewHolder(holder: SportsViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem, position)
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<SportUi>() {
    override fun areItemsTheSame(oldItem: SportUi, newItem: SportUi): Boolean {
        return oldItem.sportId == newItem.sportId && oldItem.isCollapsed == newItem.isCollapsed
    }

    override fun areContentsTheSame(oldItem: SportUi, newItem: SportUi): Boolean {
        return  oldItem.equals(newItem)
    }
}