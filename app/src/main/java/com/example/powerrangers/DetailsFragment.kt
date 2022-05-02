package com.example.powerrangers

import OnSwipeTouchListener
import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.powerrangers.data.Media
import com.example.powerrangers.databinding.DetailsFragmentBinding
import com.example.powerrangers.viewmodel.MediaViewModel
import com.example.powerrangers.viewmodel.MediaViewModelFactory
import kotlinx.android.synthetic.main.fragment_search.*

class DetailsFragment : Fragment() {

    private val navigationArgs: DetailsFragmentArgs by navArgs()

    private val viewModel: MediaViewModel by activityViewModels {
        MediaViewModelFactory(
            (activity?.application as BaseApplication).repository
        )
    }

    private lateinit var media: Media
    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetailsFragmentBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        val searchButton = binding.searchButton

        binding.description.movementMethod = ScrollingMovementMethod()


        binding.frameLayout.setOnTouchListener(object : OnSwipeTouchListener(context) {
            override fun onSwipeRight() {
                findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToSearchFragment(0, 0, 0,""))            }


        })


        searchButton.setOnClickListener{
            //media.favorite = true
            findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToSearchFragment(0, 0, 0,""))}
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Details"

        val id = navigationArgs.id


        viewModel.getMedia(id).observe(this.viewLifecycleOwner) { Media ->
            val addButton = binding.addButton
            media = Media
            if(media.favorite) {binding.addButton.text = getString(R.string.remove)
            }
            else {binding.addButton.text = getString(R.string.add)
            }
            addButton.setOnClickListener{
                val newMedia: Media
                if(media.favorite) {newMedia = media.copy(favorite = false)}
                else { newMedia = media.copy(favorite = true)}
                viewModel.updateMedia(newMedia)
                findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToTodayFragment())}
            bindMedia(media)
        }

    }

    private fun bindMedia(media: Media) {
        binding.apply {
            title.text = media.name
            release.text = media.date
            network.text = media.network
            description.text = media.description

            view?.let {
                Glide.with(it)
                    .load(media.image)
                    .into(binding.morbImg)
            }
        }
    }

}