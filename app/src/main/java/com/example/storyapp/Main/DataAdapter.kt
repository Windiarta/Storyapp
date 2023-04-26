package com.example.storyapp.Main

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storyapp.Model.ListStoryItem
import com.example.storyapp.databinding.StoryViewBinding

class DataAdapter: PagingDataAdapter<ListStoryItem, DataAdapter.MyViewHolder>(DIFF_CALLBACK){
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    class MyViewHolder(private val binding: StoryViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ListStoryItem) {
            Glide.with(binding.root)
                .load(data.photoUrl)
                .into(binding.storyImageView)
            binding.apply{
                storyItemName.text = data.name
                storyItemDescription.text = data.description
                root.setOnClickListener {
                    val intent = Intent(root.context, StoryDetailActivity::class.java)
                    intent.putExtra("Story", data)

                    val optionsCompat: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            root.context as Activity,
                            Pair(storyImageView, "detail_photo"),
                            Pair(storyItemName, "detail_title"),
                            Pair(storyItemDescription, "detail_description"),
                        )
                    root.context.startActivity(intent, optionsCompat.toBundle())
                }
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = StoryViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
}