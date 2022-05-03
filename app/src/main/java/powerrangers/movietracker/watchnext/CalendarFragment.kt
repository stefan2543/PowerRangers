package powerrangers.movietracker.watchnext

import OnSwipeTouchListener
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.movietracker.watchnext.R
import java.util.*

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
        searchButton.setOnClickListener{val action =
            powerrangers.movietracker.watchnext.CalendarFragmentDirections.actionCalendarFragmentToSearchFragment(
                0,
                0,
                0,
                ""
            )
            findNavController().navigate(action)}

        container?.setOnTouchListener(object : OnSwipeTouchListener(context) {
            override fun onSwipeLeft() {
                super.onSwipeLeft()
                Navigation.findNavController(view).navigate(R.id.action_calendarFragment_to_todayFragment)
            }


        })

        val datePicker = view.findViewById<DatePicker>(R.id.date_Picker)
        val today = Calendar.getInstance()
        datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)

        ) { view, year, month, day ->
            val month = month + 1
           // val msg = "You Selected: $day/$month/$year"
           // Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            val action =
                powerrangers.movietracker.watchnext.CalendarFragmentDirections.actionCalendarFragmentToSearchFragment(
                    month,
                    year,
                    day,
                    ""
                )
            findNavController().navigate(action)
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Calendar"
        viewModel = ViewModelProvider(this).get(CalendarViewModel::class.java)

    }

}