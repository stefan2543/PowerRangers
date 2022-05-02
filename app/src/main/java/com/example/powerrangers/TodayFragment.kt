package com.example.powerrangers

import OnSwipeTouchListener
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import java.time.LocalDate
import kotlin.properties.Delegates

/**
 * A fragment representing a list of Items.
 */
class TodayFragment : Fragment() {

    private var columnCount = 1
    private lateinit var toast: Toast
    private var empty = false
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



        binding.constraint.setOnTouchListener(object : OnSwipeTouchListener(context) {
            override fun onSwipeRight() {
                if (empty) {                toast.cancel()}
                findNavController().navigate(TodayFragmentDirections.actionTodayFragmentToCalendarFragment())            }
            override fun onSwipeLeft() {
                if (empty) {                toast.cancel()}
                findNavController().navigate(TodayFragmentDirections.actionTodayFragmentToSearchFragment(0,0,0, ""))            }


        })

        calendarButton.setOnClickListener { if (empty) {                toast.cancel()}
            findNavController().navigate(TodayFragmentDirections.actionTodayFragmentToCalendarFragment())}
        searchButton.setOnClickListener{ if (empty) {                toast.cancel()}
            findNavController().navigate(TodayFragmentDirections.actionTodayFragmentToSearchFragment(0,0,0, ""))}
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
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
                //println(empty)
                if (media.favorite) {
                    empty = false
                    validMedia.add(media)
                }
                //println("" + empty + validMedia.isEmpty())
                if (validMedia.isEmpty()) {empty = true}
               // println(empty)

            }

            Handler(Looper.getMainLooper()).postDelayed({
                if (empty) {
                    val text = "You have no media in your watchlist :( \nSwipe right to explore movies and TV shows!"
                    val duration = Toast.LENGTH_LONG
                    toast = Toast.makeText(context, text, duration)
                    toast.setGravity(Gravity.NO_GRAVITY, 0, -800)
                    toast.show()
                }
            }, 100)


            adapter.submitList(validMedia.sortedBy { val mediaDateArray = it.date.split("/").toTypedArray()
                val mediaYear = mediaDateArray[2].toInt() + 2000
                var mediaMonth = mediaDateArray[0]
                if (mediaMonth[0].toString() == " ") {
                    var correctedMonth = ""
                    for (i in 1 until mediaMonth.length){
                        correctedMonth += mediaMonth[i].toString()
                    }
                    mediaMonth = correctedMonth
                }
                val mediaDay = mediaDateArray[1].toInt()
                LocalDate.of(mediaYear, mediaMonth.toInt(), mediaDay)
            })
        }


        binding.apply {
            list.adapter = adapter

        }
    }





    companion object {


        const val ARG_COLUMN_COUNT = "column-count"


        @JvmStatic
        fun newInstance(columnCount: Int) =
            TodayFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}