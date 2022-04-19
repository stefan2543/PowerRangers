package com.example.powerrangers

import OnSwipeTouchListener
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
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


        view.setOnTouchListener(object : OnSwipeTouchListener(context) {
            override fun onSwipeLeft() {
                Navigation.findNavController(view).navigate(R.id.action_todayFragment_to_calendarFragment)
            }
            override fun onSwipeRight() {
                Navigation.findNavController(view).navigate(R.id.action_todayFragment_to_calendarFragment)


            }
            override fun onSwipeUp() {
                Navigation.findNavController(view).navigate(R.id.action_todayFragment_to_calendarFragment)


            }
            override fun onSwipeDown() {
                Navigation.findNavController(view).navigate(R.id.action_todayFragment_to_calendarFragment)


            }
        })

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