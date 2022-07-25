package com.mbano.eventsapp.ui.events

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mbano.core.utils.Util
import com.mbano.eventsapp.databinding.EventsItemViewBinding
import com.mbano.eventsapp.ui.events.uI.EventUI
import com.squareup.picasso.Picasso

class EventsAdapter: RecyclerView.Adapter<EventViewHolder>() {
    var items = listOf<EventUI>()
    val util: Util by lazy { Util() }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder{
        val inflater = LayoutInflater.from(parent.context)
        val binding = EventsItemViewBinding.inflate(inflater, parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount(): Int = items.size
}

class EventViewHolder(
    private val binding: EventsItemViewBinding
): RecyclerView.ViewHolder(binding.root) {
    val util: Util by lazy { Util() }
    fun bind(eventUI: EventUI) {
        with(binding) {
            title.text=eventUI.title
            subTitle.text= eventUI.subtitle
            defDate.text =util.formatToYesterdayOrToday(eventUI.date)
            Picasso.get().load(eventUI.imageUrl).into(imageView)
        }
    }
}