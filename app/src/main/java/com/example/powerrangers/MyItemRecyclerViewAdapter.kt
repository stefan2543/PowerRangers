package com.example.powerrangers

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.example.powerrangers.placeholder.PlaceholderContent.PlaceholderItem
import com.example.powerrangers.databinding.FragmentTodayBinding

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyItemRecyclerViewAdapter(
    //private val values: List<PlaceholderItem>
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentTodayBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //val item = values[position]
        holder.name.text = "Attack on Titan"
        holder.release.text = "Sunday 12:00 AM CT"
        holder.imageView.setImageResource(R.drawable._025e4b811cf2c61281856be28ffc2c91649000159_main_1649001573650)
    }

    override fun getItemCount(): Int = 1//values.size

    inner class ViewHolder(binding: FragmentTodayBinding) : RecyclerView.ViewHolder(binding.root) {
        val name: TextView = binding.itemName
        val imageView: ImageView = binding.itemImage
        val release: TextView = binding.releaseTime

        //override fun toString(): String {
            //return super.toString() + " '" + release.text + "'"
        //}
    }

}