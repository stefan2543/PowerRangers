package com.example.powerrangers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.powerrangers.data.Media
import com.example.powerrangers.databinding.FragmentSearchListBinding
import com.example.powerrangers.viewmodel.MediaViewModel
import com.example.powerrangers.viewmodel.MediaViewModelFactory
import kotlinx.android.synthetic.main.fragment_search.*

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchListBinding.inflate(inflater, container, false)

        //Buttons
        val todayButton = binding.todayButton
        //importCSV()
        todayButton.setOnClickListener{findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToTodayFragment())}
        return binding.root
    }

        //val url = "https://www.the-numbers.com/movies/release-schedule"
        //val document = Jsoup
            //.connect(url)
           // .get()
        //val targetElement = document





    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val adapter = MyItemRecyclerViewAdapter2 { media ->
            val action = SearchFragmentDirections.actionSearchFragmentToDetailsFragment(media.id)
            findNavController().navigate(action)
        }

        viewModel.allMedia.observe(this.viewLifecycleOwner) { allMedia ->
            val month = navigationArgs.month
            val year = navigationArgs.year - 2000
            val day = navigationArgs.day
            val date : String
            val validMedia : MutableList<Media> = mutableListOf()
            if (month != 0) {
                date = "$month/$day/$year"
                for (media in allMedia) {
                    if (media.date == date) {
                        validMedia.add(media)
                    }
                }
                adapter.submitList(validMedia.sortedBy { it.date })
            }
            else {adapter.submitList(allMedia.sortedBy { it.date })}

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
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }

}