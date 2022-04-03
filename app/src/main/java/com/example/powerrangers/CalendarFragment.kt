package com.example.powerrangers

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation

class CalendarFragment : Fragment() {

    companion object {
        fun newInstance() = CalendarFragment()
    }

    private lateinit var viewModel: CalendarViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.calendar_fragment, container, false)
        //Buttons
        val todayButton = view.findViewById<Button>(R.id.todayButton)
        val searchButton = view.findViewById<Button>(R.id.searchButton)
        todayButton.setOnClickListener{Navigation.findNavController(view).navigate(R.id.action_calendarFragment_to_todayFragment)}
        searchButton.setOnClickListener{Navigation.findNavController(view).navigate(R.id.action_calendarFragment_to_searchFragment)}

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CalendarViewModel::class.java)
        // TODO: Use the ViewModel
    }

}