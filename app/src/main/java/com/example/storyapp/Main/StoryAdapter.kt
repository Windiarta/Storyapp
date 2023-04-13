package com.example.storyapp.Main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storyapp.API.ListStoryItem
import com.example.storyapp.databinding.StoryViewBinding

class StoryAdapter (private val item: List<ListStoryItem>):  RecyclerView.Adapter<StoryAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    class ListViewHolder(var binding: StoryViewBinding) : RecyclerView.ViewHolder(binding.root)

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = StoryViewBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = item.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val selectedItem = item[position]
        Glide.with(holder.binding.root)
            .load(selectedItem.photoUrl)
            .into(holder.binding.storyImageView)
        holder.binding.apply{
            storyItemName.text = selectedItem.name
        }
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(item[holder.adapterPosition])
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ListStoryItem)
    }
}