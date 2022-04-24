package com.example.powerrangers

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetailsFragmentBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        val addButton = binding.addButton

        container?.context?.let {
            Glide.with(it)
                .load("http://via.placeholder.com/300.png")
                .into(morb_img)
        };
        addButton.setOnClickListener{
            media.favorite = true
            findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToTodayFragment())}
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.id
        // TODO: Observe a forageable that is retrieved by id, set the forageable variable,
        //  and call the bind forageable method

        viewModel.getMedia(id).observe(this.viewLifecycleOwner) { Media ->
            media = Media
            if(media.favorite) {binding.addButton.text = "Remove from Watchlist"}
            bindMedia()
        }

    }

    private fun bindMedia() {
        binding.apply {
            title.text = media?.name
            release.text = media?.date
            network.text = media?.network
        }
    }

}