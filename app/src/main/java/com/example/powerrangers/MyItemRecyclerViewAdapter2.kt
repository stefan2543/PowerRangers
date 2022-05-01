package com.example.powerrangers

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.example.powerrangers.data.Media

import com.example.powerrangers.placeholder.PlaceholderContent.PlaceholderItem
import com.example.powerrangers.databinding.FragmentSearchBinding
import kotlinx.android.synthetic.main.fragment_today.view.*

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyItemRecyclerViewAdapter2(
    private val clickListener: (Media) -> Unit
) : ListAdapter<Media, MyItemRecyclerViewAdapter2.MediaViewHolder>(DiffCallback) {

    class MediaViewHolder(
        private var binding: FragmentSearchBinding
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {


        val layoutInflater = LayoutInflater.from(parent.context)

        return MediaViewHolder(
            FragmentSearchBinding.inflate(layoutInflater, parent, false)
        )

    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        val media = getItem(position)

        holder.itemView.context.let {
            Glide.with(it)
                .load(media.image)
                .into(holder.itemView.morb_img)
        };

        holder.itemView.setOnClickListener{
            clickListener(media)
        }
        holder.bind(media)
    }

    //override fun getItemCount(): Int = values.size

//    inner class ViewHolder(binding: FragmentSearchBinding) : RecyclerView.ViewHolder(binding.root) {
//        //val idView: TextView = binding.itemNumber
//        val contentView: TextView = binding.Title
//
//        override fun toString(): String {
//            return super.toString() + " '" + contentView.text + "'"
//        }
//    }

}