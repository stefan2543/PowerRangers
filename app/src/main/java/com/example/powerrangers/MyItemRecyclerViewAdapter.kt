package com.example.powerrangers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.powerrangers.data.Media
import com.example.powerrangers.databinding.FragmentTodayBinding
import kotlinx.android.synthetic.main.fragment_today.view.*

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 *
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
                .load(media.image)
                .into(holder.itemView.morb_img)
        }


        holder.itemView.setOnClickListener{
            clickListener(media)
        }
        if (media.favorite){
        holder.bind(media)}
    }


}