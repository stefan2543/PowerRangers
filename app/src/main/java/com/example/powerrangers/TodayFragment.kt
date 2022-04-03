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
import androidx.navigation.fragment.findNavController
import com.example.powerrangers.placeholder.PlaceholderContent

/**
 * A fragment representing a list of Items.
 */
class TodayFragment : Fragment() {

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
        val view = inflater.inflate(R.layout.fragment_today_list, container, false)

        // Buttons
        val nextDayButton = view.findViewById<Button>(R.id.nextDayButton)
        val calendarButton = view.findViewById<Button>(R.id.calendarButton)
        val searchButton = view.findViewById<Button>(R.id.searchButton)


        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyItemRecyclerViewAdapter(PlaceholderContent.ITEMS)
            }
        }

        nextDayButton.setOnClickListener{ Navigation.findNavController(view).navigate(R.id.action_todayFragment_self)}
        calendarButton.setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_todayFragment_to_calendarFragment)}
        searchButton.setOnClickListener{ Navigation.findNavController(view).navigate(R.id.action_todayFragment_to_searchFragment)}
        return view
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