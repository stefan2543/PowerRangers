package com.example.powerrangers

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.example.powerrangers.placeholder.PlaceholderContent
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

/**
 * A fragment representing a list of Items.
 */
class SearchFragment : Fragment() {

    private var columnCount = 1

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
        val view = inflater.inflate(R.layout.fragment_search_list, container, false)
        //Buttons
        val todayButton = view.findViewById<Button>(R.id.todayButton)
        val detailsButton = view.findViewById<Button>(R.id.detailsButton)

        //val url = "https://www.the-numbers.com/movies/release-schedule"
        //val document = Jsoup
            //.connect(url)
           // .get()
        //val targetElement = document


       /* // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyItemRecyclerViewAdapter2(PlaceholderContent.ITEMS)
            }
        }*/
        todayButton.setOnClickListener{Navigation.findNavController(view).navigate(R.id.action_searchFragment_to_todayFragment)}
        detailsButton.setOnClickListener{Navigation.findNavController(view).navigate(R.id.action_searchFragment_to_detailsFragment)}
        return view
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