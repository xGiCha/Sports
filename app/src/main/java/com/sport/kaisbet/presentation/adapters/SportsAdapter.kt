package com.sport.kaisbet.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sport.kaisbet.R
import com.sport.kaisbet.databinding.LayoutSportItemBinding
import com.sport.kaisbet.domain.models.Event
import com.sport.kaisbet.domain.models.Sport

class SportsAdapter(
    val context: Context,
    val callback: (isCollapsed: Boolean, position: Int) -> Unit,
    val callBackEvent: (hasFavorite: Boolean, event: Event) -> Unit
) : ListAdapter<Sport, SportsAdapter.ViewHolder>(DiffCallback()) {
    private lateinit var adapter: EventAdapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SportsAdapter.ViewHolder {
        val binding: LayoutSportItemBinding = LayoutSportItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)


        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SportsAdapter.ViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem, position)
        }
    }

    inner class ViewHolder(val binding: LayoutSportItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Sport, position: Int) {
            binding.sportBarTitleTxtV.text = item.sportName

            if(item.isCollapsed){
                binding.sportItemRV.visibility = View.GONE
                binding.sportIcon.setBackgroundResource(R.drawable.ic_arrow_down)
            }else {
                binding.sportIcon.setBackgroundResource(R.drawable.ic_arrow_up)
                binding.sportItemRV.visibility = View.VISIBLE
            }

            binding.sportIcon.setOnClickListener {
                if(!item.isCollapsed) {
                    callback.invoke(true, position)
                }else {
                    callback.invoke(false, position)
                }
            }

            adapter = EventAdapter { hasFavorite, event ->
                callBackEvent.invoke(hasFavorite, event)
            }
            binding.sportItemRV.layoutManager = GridLayoutManager(context, 4)
            binding.sportItemRV.adapter = adapter
            adapter.submitList(item.eventList)
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<Sport>() {
    // your DiffCallback implementation
    override fun areItemsTheSame(oldItem: Sport, newItem: Sport): Boolean {
        return oldItem.sportId == newItem.sportId && oldItem.isCollapsed == newItem.isCollapsed
    }

    override fun areContentsTheSame(oldItem: Sport, newItem: Sport): Boolean {
        return  oldItem.equals(newItem)
    }
}