package com.sport.kaisbet.presentation.viewHolder

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sport.kaisbet.R
import com.sport.kaisbet.databinding.LayoutSportItemBinding
import com.sport.kaisbet.domain.models.Event
import com.sport.kaisbet.domain.models.SportUi
import com.sport.kaisbet.presentation.adapters.EventAdapter

class SportsViewHolder(
    val binding: LayoutSportItemBinding,
    private val callback: (isCollapsed: Boolean, position: Int) -> Unit,
    private val callBackEvent: (hasFavorite: Boolean, event: Event) -> Unit,
    private val callBackSwitch: (switchState: Boolean, sportUi: SportUi) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var adapter: EventAdapter

    init {
       initAdapter()
    }

    fun bind(item: SportUi, position: Int) {
        binding.apply {
            sportBarTitleTxtV.text = item.sportName

            sportItemRV.visibility = View.GONE.takeIf { item.isCollapsed } ?: View.VISIBLE
            sportIcon.setBackgroundResource(R.drawable.ic_arrow_down.takeIf { item.isCollapsed } ?: R.drawable.ic_arrow_up)

            sportIcon.setOnClickListener {
                callback.invoke(true.takeIf { !item.isCollapsed } ?: false, position)
            }

            switchV.setOnCheckedChangeListener { compoundButton, isChecked ->
                if (switchV.isChecked != item.switchState)
                    callBackSwitch.invoke(isChecked, item)

            }
            switchV.isChecked = item.switchState
            adapter.submitList(item.eventList)
        }
    }

    private fun initAdapter() {
        binding.apply {
            adapter = EventAdapter { hasFavorite, event ->
                callBackEvent.invoke(hasFavorite, event)
            }
            sportItemRV.layoutManager = GridLayoutManager(root.context, 4)
//            sportItemRV.layoutManager = LinearLayoutManager(root.context, LinearLayoutManager.HORIZONTAL, false)
            sportItemRV.adapter = adapter
        }
    }
}