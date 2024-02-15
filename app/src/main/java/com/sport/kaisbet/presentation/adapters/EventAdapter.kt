package com.sport.kaisbet.presentation.adapters

import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sport.kaisbet.R
import com.sport.kaisbet.databinding.LayoutEventItemBinding
import com.sport.kaisbet.domain.models.Event
import com.sport.kaisbet.units.timeStampToTime

class EventAdapter(
    val callbackEvent: (hasFavorite: Boolean, event: Event) -> Unit
) : ListAdapter<Event, EventAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventAdapter.ViewHolder {
        val binding: LayoutEventItemBinding = LayoutEventItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventAdapter.ViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (holder.timer != null) {
            holder.timer!!.cancel()
        }
        if (currentItem != null) {
            holder.bind(currentItem, position)
        }

        holder.timer = object : CountDownTimer(currentItem.eventStartTime.toLong(), 1000) {

            override fun onTick(millisUntilFinished: Long) {
                currentItem.eventStartTime = millisUntilFinished.toInt()
                holder.binding.timerTxtV.text = currentItem.eventStartTime.toString().timeStampToTime()
            }

            // when the time is up
            override fun onFinish() {
            }
        }.start()

    }

    inner class ViewHolder(val binding: LayoutEventItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var timer: CountDownTimer? = null
        fun bind(item: Event, position: Int) {
            binding.apply {
                teamOneTxtV.text = item.eventName
                teamtwoTxtV.text = item.eventSubName
                vsTxtV.text = binding.root.context.getString(R.string.vs)

                if(item.hasFavorite){
                    favoriteImgV.setBackgroundResource(R.drawable.ic_star_yellow)
                }else{
                    favoriteImgV.setBackgroundResource(R.drawable.ic_star_empty)
                }

                gameContainer.setOnClickListener {
                    if(item.hasFavorite){
                        callbackEvent.invoke(false, item)
                    }else{
                        callbackEvent.invoke(true, item)
                    }
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Event>() {
        // your DiffCallback implementation
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.eventId == newItem.eventId && oldItem.hasFavorite == newItem.hasFavorite
        }

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.equals(newItem)
        }
    }

}