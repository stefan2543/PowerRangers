package com.example.powerrangers

import OnSwipeTouchListener
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.powerrangers.data.Media
import com.example.powerrangers.databinding.FragmentSearchListBinding
import com.example.powerrangers.databinding.FragmentTodayBinding
import com.example.powerrangers.databinding.FragmentTodayListBinding
import com.example.powerrangers.placeholder.PlaceholderContent
import com.example.powerrangers.viewmodel.MediaViewModel
import com.example.powerrangers.viewmodel.MediaViewModelFactory
import kotlinx.android.synthetic.main.fragment_search_list.*
import kotlinx.android.synthetic.main.fragment_today.view.*

/**
 * A fragment representing a list of Items.
 */
class TodayFragment : Fragment() {

    private var columnCount = 1
    private val viewModel: MediaViewModel by activityViewModels {
        MediaViewModelFactory(
            (activity?.application as BaseApplication).repository
        )
    }
    private var _binding: FragmentTodayListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTodayListBinding.inflate(inflater, container, false)

        // Buttons
        val nextDayButton = binding.nextDayButton
        val calendarButton = binding.calendarButton
        val searchButton = binding.searchButton

        /*// Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyItemRecyclerViewAdapter()
            }
        }*/


        nextDayButton.setOnClickListener{ findNavController().navigate(TodayFragmentDirections.actionTodayFragmentSelf()) }
        calendarButton.setOnClickListener { findNavController().navigate(TodayFragmentDirections.actionTodayFragmentToCalendarFragment())}
        searchButton.setOnClickListener{ findNavController().navigate(TodayFragmentDirections.actionTodayFragmentToSearchFragment(0,0,0))}
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = "Your Watchlist"

        val adapter = MyItemRecyclerViewAdapter { media ->
            val action = TodayFragmentDirections.actionTodayFragmentToDetailsFragment(media.id)
            findNavController().navigate(action)
        }


        viewModel.allMedia.observe(this.viewLifecycleOwner) { allMedia ->
            val validMedia : MutableList<Media> = mutableListOf()
            for (media in allMedia) {
                if (media.favorite) {
                    validMedia.add(media)
                }
            }
            adapter.submitList(validMedia.sortedBy { it.date })
        }
        binding.apply {
            list.adapter = adapter

        }
    }






    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            TodayFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}