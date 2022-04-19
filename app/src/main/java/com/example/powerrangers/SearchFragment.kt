package com.example.powerrangers

import android.os.Bundle
import android.os.Environment
import android.os.Environment.getExternalStorageDirectory
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.room.RoomMasterTable.TABLE_NAME
import com.example.powerrangers.databinding.FragmentSearchListBinding
import com.example.powerrangers.viewmodel.MediaViewModel
import com.example.powerrangers.viewmodel.MediaViewModelFactory
import com.mortgage.fauxiq.pawnbroker.utils.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.FileReader

/**
 * A fragment representing a list of Items.
 */
class SearchFragment : Fragment() {

    private var columnCount = 1
    private val viewModel: MediaViewModel by activityViewModels {
        MediaViewModelFactory(
            (activity?.application as BaseApplication).database.mediaDao()
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
        importCSV()
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

        viewModel.allMedia.observe(this.viewLifecycleOwner) {allMedia ->
            adapter.submitList(allMedia)
        }
        binding.apply {
           list.adapter = adapter
            }
        }

    private fun importCSV(){
        val csvReader =
            CSVReader(FileReader())/* path of local storage (it should be your csv file locatioin)*/
        var nextLine: Array<String> ? = null
        var count = 0
        val columns = StringBuilder()
        GlobalScope.launch(Dispatchers.IO) {
            do {
                val value = StringBuilder()
                nextLine = csvReader.readNext()
                nextLine?.let {nextLine->
                    for (i in 0 until nextLine.size - 1) {
                        if (count == 0) {                             // the count==0 part only read
                            if (i == nextLine.size - 2) {             //your csv file column name
                                columns.append(nextLine[i])
                                count =1
                            }
                            else
                                columns.append(nextLine[i]).append(",")
                        } else {                         // this part is for reading value of each row
                            if (i == nextLine.size - 2) {
                                value.append("'").append(nextLine[i]).append("'")
                                count = 2
                            }
                            else
                                value.append("'").append(nextLine[i]).append("',")
                        }
                    }
                    if (count==2) {
                        viewModel.pushCustomerData(columns, value)//write here your code to insert all values
                    }
                }
            }while ((nextLine)!=null)
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