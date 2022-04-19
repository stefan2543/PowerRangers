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
import com.example.powerrangers.data.Media
import com.example.powerrangers.databinding.DetailsFragmentBinding
import com.example.powerrangers.viewmodel.MediaViewModel
import com.example.powerrangers.viewmodel.MediaViewModelFactory

class DetailsFragment : Fragment() {

    private val navigationArgs: DetailsFragmentArgs by navArgs()

    private val viewModel: MediaViewModel by activityViewModels {
        MediaViewModelFactory(
            (activity?.application as BaseApplication).database.mediaDao()
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
        addButton.setOnClickListener{findNavController().navigate(R.id.action_searchFragment_to_todayFragment)}
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.id
        // TODO: Observe a forageable that is retrieved by id, set the forageable variable,
        //  and call the bind forageable method
        viewModel.getMedia(id).observe(this.viewLifecycleOwner) {Media ->
            media = Media
            bindMedia()
        }
    }

    private fun bindMedia() {
        binding.apply {
            title.text = media.name
            release.text = media.date.toString()
            //notes.text = media.notes
        }
    }

}