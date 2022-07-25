package com.mbano.eventsapp.ui.schedule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mbano.core.utils.Util
import com.mbano.eventsapp.databinding.ScheduleItemViewBinding
import com.mbano.eventsapp.ui.schedule.ui.ScheduleUI

class ScheduleAdapter: RecyclerView.Adapter<ScheduleViewHolder>() {
    var items = listOf<ScheduleUI>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ScheduleItemViewBinding.inflate(inflater, parent, false)
        return ScheduleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount(): Int = items.size
}

class ScheduleViewHolder(
    private val binding: ScheduleItemViewBinding
): RecyclerView.ViewHolder(binding.root) {
    val util: Util by lazy { Util() }
    fun bind(scheduleUI: ScheduleUI) {
        with(binding) {
            title.text=scheduleUI.title
            subTitle.text= scheduleUI.subtitle
            defDate.text =util.formatToTodayOrLater(scheduleUI.date)
            com.squareup.picasso.Picasso.get().load(scheduleUI.imageUrl).into(imageView)
        }
    }
}