package com.example.powerrangers

import OnSwipeTouchListener
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.powerrangers.data.Media
import com.example.powerrangers.databinding.FragmentSearchListBinding
import com.example.powerrangers.viewmodel.MediaViewModel
import com.example.powerrangers.viewmodel.MediaViewModelFactory
import kotlinx.android.synthetic.main.fragment_search.*
import java.time.LocalDate
import java.time.Period

/**
 * A fragment representing a list of Items.
 */
class SearchFragment : Fragment() {

    private var columnCount = 1
    private val navigationArgs: SearchFragmentArgs by navArgs()
    private val viewModel: MediaViewModel by activityViewModels {
        MediaViewModelFactory(
            (activity?.application as BaseApplication).repository
        )
    }

    private var _binding: FragmentSearchListBinding? = null

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
        _binding = FragmentSearchListBinding.inflate(inflater, container, false)

        binding.constraint.setOnTouchListener(object : OnSwipeTouchListener(context) {
            override fun onSwipeRight() {
                findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToTodayFragment())           }
            override fun onSwipeLeft() {
                findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToFilterFragment())           }

        })

        //Buttons
        val todayButton = binding.todayButton
        val filterButton = binding.advancedSearch
        //importCSV()
        todayButton.setOnClickListener{findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToTodayFragment())}
        filterButton.setOnClickListener{findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToFilterFragment())}

        return binding.root
    }

        //val url = "https://www.the-numbers.com/movies/release-schedule"
        //val document = Jsoup
            //.connect(url)
           // .get()
        //val targetElement = document





    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Search"


        val adapter = MyItemRecyclerViewAdapter2 { media ->
            val action = SearchFragmentDirections.actionSearchFragmentToDetailsFragment(media.id)
            findNavController().navigate(action)
        }

        viewModel.allMedia.observe(this.viewLifecycleOwner) { allMedia ->
            val month = navigationArgs.month
            val year = navigationArgs.year
            val day = navigationArgs.day
            val filter = navigationArgs.filter
            val date : LocalDate
            val nextdate: LocalDate
            val validMedia : MutableList<Media> = mutableListOf()
            if (filter != ""){
                for (media in allMedia) {
                    val mediaFilter = media.network
                    val mediaTitle = media.name
                    if (mediaFilter == filter || mediaTitle == filter) {
                        validMedia.add(media)
                    }
                }
                adapter.submitList(validMedia.sortedBy {
                    val mediaDateArray = it.date.split("/").toTypedArray()
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
            else if (month != 0) {
                date = LocalDate.of(year,month,day)
                nextdate = date.plus(Period.of(0,0,6))
                for (media in allMedia) {
                    val mediaDateArray = media.date.split("/").toTypedArray()
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
                    val mediaDate = LocalDate.of(mediaYear, mediaMonth.toInt(), mediaDay)
                    if ( mediaDate in date..nextdate) {
                        validMedia.add(media)
                    }
                }
                adapter.submitList(validMedia.sortedBy {
                    val mediaDateArray = it.date.split("/").toTypedArray()
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
            else {adapter.submitList(allMedia.sortedBy {
                val mediaDateArray = it.date.split("/").toTypedArray()
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
            })}

        }
        binding.apply {
           list.adapter = adapter
            }
        }


    companion object {


        const val ARG_COLUMN_COUNT = "column-count"


        @JvmStatic
        fun newInstance(columnCount: Int) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }

}