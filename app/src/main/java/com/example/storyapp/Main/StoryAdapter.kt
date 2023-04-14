package com.example.storyapp.Main

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import androidx.core.util.Pair
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storyapp.Model.ListStoryItem
import com.example.storyapp.databinding.StoryViewBinding

class StoryAdapter (private val item: List<ListStoryItem>):  RecyclerView.Adapter<StoryAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = StoryViewBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = item.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(item[position])

    }

    class ListViewHolder(private var binding: StoryViewBinding) : RecyclerView.ViewHolder(binding.root){
        private val photo : ImageView = binding.storyImageView
        private val name : TextView = binding.storyItemName
        private val desc : TextView = binding.storyItemDescription
        fun bind(item: ListStoryItem){
            Glide.with(binding.root)
                .load(item.photoUrl)
                .into(binding.storyImageView)
            binding.apply{
                storyItemName.text = item.name
                storyItemDescription.text = item.description
                root.setOnClickListener {
                    val intent = Intent(root.context, StoryDetailActivity::class.java)
                    intent.putExtra("Story", item)

                    val optionsCompat: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            root.context as Activity,
                            Pair(photo, "detail_photo"),
                            Pair(name, "detail_title"),
                            Pair(desc, "detail_description"),
                        )
                    root.context.startActivity(intent, optionsCompat.toBundle())
                }
            }

        }
    }

}