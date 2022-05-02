package com.example.powerrangers

import OnSwipeTouchListener
import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
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
import com.example.powerrangers.databinding.FragmentFilterBinding
import com.example.powerrangers.viewmodel.MediaViewModel
import com.example.powerrangers.viewmodel.MediaViewModelFactory
import kotlinx.android.synthetic.main.fragment_search.*

class FilterFragment : Fragment() {

    private val viewModel: MediaViewModel by activityViewModels {
        MediaViewModelFactory(
            (activity?.application as BaseApplication).repository
        )
    }

    private lateinit var media: Media
    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        val searchText = binding.searchText
        val theaterButton = binding.theatersButton
        val appleButton = binding.appleButton
        val hboButton = binding.hboButton
        val huluButton = binding.huluButton
        val netflixButton = binding.netflixButton
        val pbsButton = binding.pbsButton
        val showtimeButton = binding.showtimeButton
        val paramountButton = binding.paramountButton
        val searchButton = binding.search

        binding.layout.setOnTouchListener(object : OnSwipeTouchListener(context) {
            override fun onSwipeLeft() {
                findNavController().navigate(FilterFragmentDirections.actionFilterFragmentToSearchFragment(0, 0, 0,""))            }


        })


        theaterButton.setOnClickListener{
            findNavController().navigate(FilterFragmentDirections.actionFilterFragmentToSearchFragment(0, 0, 0,"Theaters"))}
        appleButton.setOnClickListener{
            findNavController().navigate(FilterFragmentDirections.actionFilterFragmentToSearchFragment(0, 0, 0,"Apple TV+"))}
        hboButton.setOnClickListener{
            findNavController().navigate(FilterFragmentDirections.actionFilterFragmentToSearchFragment(0, 0, 0,"HBO Max"))}
        huluButton.setOnClickListener{
            findNavController().navigate(FilterFragmentDirections.actionFilterFragmentToSearchFragment(0, 0, 0,"Hulu"))}
        netflixButton.setOnClickListener{
            findNavController().navigate(FilterFragmentDirections.actionFilterFragmentToSearchFragment(0, 0, 0,"Netflix"))}
        pbsButton.setOnClickListener{
            findNavController().navigate(FilterFragmentDirections.actionFilterFragmentToSearchFragment(0, 0, 0,"PBS"))}
        showtimeButton.setOnClickListener{
            findNavController().navigate(FilterFragmentDirections.actionFilterFragmentToSearchFragment(0, 0, 0,"Showtime"))}
        paramountButton.setOnClickListener{
            findNavController().navigate(FilterFragmentDirections.actionFilterFragmentToSearchFragment(0, 0, 0,"Paramount+"))}
        searchButton.setOnClickListener{
            findNavController().navigate(FilterFragmentDirections.actionFilterFragmentToSearchFragment(0,0,0,
                searchText.text.toString()
            ))
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Search and Filter"


    }



}