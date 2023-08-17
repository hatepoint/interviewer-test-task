package com.hatepoint.interviewer.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hatepoint.interviewer.databinding.ItemTopicBinding

class TopicAdapter(val onTopicClick: (String) -> Unit) : RecyclerView.Adapter<TopicAdapter.TopicViewHolder>() {

    class TopicViewHolder(val binding: ItemTopicBinding) : RecyclerView.ViewHolder(binding.root)

    var items = emptyList<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TopicAdapter.TopicViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTopicBinding.inflate(inflater, parent, false)
        return TopicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopicAdapter.TopicViewHolder, position: Int) {
        val item = items[position]
        holder.binding.apply {
            topicTextView.text = item
            topicTextView.setOnClickListener {
                val pos = holder.adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    onTopicClick(items[pos])
                }
            }
        }
    }

    override fun getItemCount(): Int = items.size

}