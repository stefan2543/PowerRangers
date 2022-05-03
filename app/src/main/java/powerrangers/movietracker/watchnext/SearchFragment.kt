package powerrangers.movietracker.watchnext

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
import powerrangers.movietracker.watchnext.data.Media
import com.movietracker.watchnext.databinding.FragmentSearchListBinding
import powerrangers.movietracker.watchnext.viewmodel.MediaViewModel
import powerrangers.movietracker.watchnext.viewmodel.MediaViewModelFactory
import java.time.LocalDate
import java.time.Period
import kotlin.math.max
import kotlin.math.min

/**
 * A fragment representing a list of Items.
 */
class SearchFragment : Fragment() {

    private var columnCount = 1
    private val navigationArgs: powerrangers.movietracker.watchnext.SearchFragmentArgs by navArgs()
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
                findNavController().navigate(powerrangers.movietracker.watchnext.SearchFragmentDirections.actionSearchFragmentToTodayFragment())           }
            override fun onSwipeLeft() {
                findNavController().navigate(powerrangers.movietracker.watchnext.SearchFragmentDirections.actionSearchFragmentToFilterFragment())           }

        })

        //Buttons
        val todayButton = binding.todayButton
        val filterButton = binding.advancedSearch
        //importCSV()
        todayButton.setOnClickListener{findNavController().navigate(powerrangers.movietracker.watchnext.SearchFragmentDirections.actionSearchFragmentToTodayFragment())}
        filterButton.setOnClickListener{findNavController().navigate(powerrangers.movietracker.watchnext.SearchFragmentDirections.actionSearchFragmentToFilterFragment())}

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
            val action = powerrangers.movietracker.watchnext.SearchFragmentDirections.actionSearchFragmentToDetailsFragment(media.id)
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
            when {
                filter != "" -> {
                    for (media in allMedia) {
                        val mediaFilter = media.network
                        val mediaTitle = media.name.split(" ").toTypedArray()
                        for (word in mediaTitle) {
                            val similarity = findSimilarity(word, filter)
                            if (mediaFilter == filter || similarity > 0.5) {
                                validMedia.add(media)
                                break
                            }
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
                month != 0 -> {
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
                else -> {adapter.submitList(allMedia.sortedBy {
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

    //Code taken from: https://www.techiedelight.com/find-similarities-between-two-strings-in-kotlin/
    private fun getLevenshteinDistance(X: String, Y: String): Int {
        val m = X.length
        val n = Y.length
        val T = Array(m + 1) { IntArray(n + 1) }
        for (i in 1..m) {
            T[i][0] = i
        }
        for (j in 1..n) {
            T[0][j] = j
        }
        var cost: Int
        for (i in 1..m) {
            for (j in 1..n) {
                cost = if (X[i - 1] == Y[j - 1]) 0 else 1
                T[i][j] = min(min(T[i - 1][j] + 1, T[i][j - 1] + 1),
                    T[i - 1][j - 1] + cost)
            }
        }
        return T[m][n]
    }

    fun findSimilarity(x: String?, y: String?): Double {
        require(!(x == null || y == null)) { "Strings should not be null" }

        val maxLength = max(x.length, y.length)
        return if (maxLength > 0) {
            (maxLength * 1.0 - getLevenshteinDistance(x, y)) / maxLength * 1.0
        } else 1.0
    }

}