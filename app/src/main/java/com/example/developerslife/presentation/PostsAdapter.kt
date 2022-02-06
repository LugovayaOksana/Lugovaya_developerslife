package com.example.developerslife.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.developerslife.databinding.ItemPageBinding
import com.example.developerslife.domain.model.Post

class PostsAdapter : RecyclerView.Adapter<PagerVH>() {

    private val items = mutableListOf<Post>()

    fun setItems(posts: List<Post>) {
        items.clear()
        items.addAll(posts)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH {
        val binding = ItemPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PagerVH(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PagerVH, position: Int) {
        holder.itemView.run {
            val item = items[position]
            with(holder.binding) {
                titlePage.text = item.description
                Glide.with(image)
                    .load(item.gifURL)
                    .into(image);
            }
        }
    }
}

class PagerVH(val binding: ItemPageBinding) : RecyclerView.ViewHolder(binding.root)