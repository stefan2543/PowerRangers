package powerrangers.movietracker.watchnext

import OnSwipeTouchListener
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import powerrangers.movietracker.watchnext.data.Media
import com.movietracker.watchnext.databinding.FragmentFilterBinding
import powerrangers.movietracker.watchnext.viewmodel.MediaViewModel
import powerrangers.movietracker.watchnext.viewmodel.MediaViewModelFactory

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
        val backButton = binding.backButton


        binding.constraint.setOnTouchListener(object : OnSwipeTouchListener(context) {
            override fun onSwipeRight() {
                findNavController().navigate(
                    powerrangers.movietracker.watchnext.FilterFragmentDirections.actionFilterFragmentToSearchFragment(
                        0,
                        0,
                        0,
                        ""
                    )
                )            }


        })


        backButton.setOnClickListener{findNavController().navigate(
            powerrangers.movietracker.watchnext.FilterFragmentDirections.actionFilterFragmentToSearchFragment(
                0,
                0,
                0,
                ""
            )
        )}
        theaterButton.setOnClickListener{
            findNavController().navigate(
                powerrangers.movietracker.watchnext.FilterFragmentDirections.actionFilterFragmentToSearchFragment(
                    0,
                    0,
                    0,
                    "Theaters"
                )
            )}
        appleButton.setOnClickListener{
            findNavController().navigate(
                powerrangers.movietracker.watchnext.FilterFragmentDirections.actionFilterFragmentToSearchFragment(
                    0,
                    0,
                    0,
                    "Apple TV+"
                )
            )}
        hboButton.setOnClickListener{
            findNavController().navigate(
                powerrangers.movietracker.watchnext.FilterFragmentDirections.actionFilterFragmentToSearchFragment(
                    0,
                    0,
                    0,
                    "HBO Max"
                )
            )}
        huluButton.setOnClickListener{
            findNavController().navigate(
                powerrangers.movietracker.watchnext.FilterFragmentDirections.actionFilterFragmentToSearchFragment(
                    0,
                    0,
                    0,
                    "Hulu"
                )
            )}
        netflixButton.setOnClickListener{
            findNavController().navigate(
                powerrangers.movietracker.watchnext.FilterFragmentDirections.actionFilterFragmentToSearchFragment(
                    0,
                    0,
                    0,
                    "Netflix"
                )
            )}
        pbsButton.setOnClickListener{
            findNavController().navigate(
                powerrangers.movietracker.watchnext.FilterFragmentDirections.actionFilterFragmentToSearchFragment(
                    0,
                    0,
                    0,
                    "PBS"
                )
            )}
        showtimeButton.setOnClickListener{
            findNavController().navigate(
                powerrangers.movietracker.watchnext.FilterFragmentDirections.actionFilterFragmentToSearchFragment(
                    0,
                    0,
                    0,
                    "Showtime"
                )
            )}
        paramountButton.setOnClickListener{
            findNavController().navigate(
                powerrangers.movietracker.watchnext.FilterFragmentDirections.actionFilterFragmentToSearchFragment(
                    0,
                    0,
                    0,
                    "Paramount+"
                )
            )}
        searchButton.setOnClickListener{
            findNavController().navigate(
                powerrangers.movietracker.watchnext.FilterFragmentDirections.actionFilterFragmentToSearchFragment(
                    0, 0, 0,
                    searchText.text.toString()
                )
            )
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Advanced Search"


    }



}