package com.example.powerrangers

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.example.powerrangers.data.Media
import com.example.powerrangers.databinding.FragmentSearchBinding

import com.example.powerrangers.placeholder.PlaceholderContent.PlaceholderItem
import com.example.powerrangers.databinding.FragmentTodayBinding
import kotlinx.android.synthetic.main.fragment_today.view.*

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyItemRecyclerViewAdapter(
    private val clickListener: (Media) -> Unit
) : ListAdapter<Media, MyItemRecyclerViewAdapter.MediaViewHolder>(DiffCallback) {

    class MediaViewHolder(
        private var binding: FragmentTodayBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(media: Media) {
            binding.media = media
            binding.executePendingBindings()


        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<Media>() {
        override fun areItemsTheSame(oldItem: Media, newItem: Media): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Media, newItem: Media): Boolean {
            return oldItem == newItem
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyItemRecyclerViewAdapter.MediaViewHolder {


        val layoutInflater = LayoutInflater.from(parent.context)

        return MediaViewHolder(
            FragmentTodayBinding.inflate(layoutInflater, parent, false)
        )

    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        val media = getItem(position)

        holder.itemView.context.let {
            Glide.with(it)
                .load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRe0O0260hzKyKursZUTtZAxECP0gSVJ2JXwQ&usqp=CAU")
                .into(holder.itemView.morb_img)
        };


        holder.itemView.setOnClickListener{
            clickListener(media)
        }
        if (media.favorite){
        holder.bind(media)}
    }


}