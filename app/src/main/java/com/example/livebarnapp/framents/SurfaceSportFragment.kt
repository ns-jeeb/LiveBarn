package com.example.livebarnapp.framents

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.annotation.VisibleForTesting
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.livebarnapp.MySurfaceItemRecyclerViewAdapter
import com.example.livebarnapp.R
import com.example.livebarnapp.SurfaceViewModel
import com.example.livebarnapp.databinding.FragmentSurfaceSportListBinding
import com.example.livebarnapp.models.SurfaceItem

private const val ARG_PARAM1 = "param1"
@RequiresApi(Build.VERSION_CODES.KITKAT)
class SurfaceSoccerFragment : Fragment(), MySurfaceItemRecyclerViewAdapter.OnItemClickListener {

    private var param1: Int? = null
    val shareViewModel: SurfaceViewModel by activityViewModels()
    private var viewModel: SurfaceViewModel = SurfaceViewModel()
    lateinit var binding: FragmentSurfaceSportListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)

        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_surface_sport_list,
            container,
            false
        )

        setupAdapter()

        return binding.root
    }
    fun setupAdapter() {
        val listLiveData : LiveData<ArrayList<SurfaceItem>>? = when(param1){
            0 ->  viewModel._hokeyItems
            1 ->  viewModel._baseballItems
            2 ->  viewModel._soccerItems
            3 ->  viewModel._volleballItems
            4 ->  viewModel._basketItems
            else -> null
        }
        listLiveData?.observe(viewLifecycleOwner, Observer {
            binding.list.adapter = MySurfaceItemRecyclerViewAdapter(viewModel,it)
            (binding.list.adapter as MySurfaceItemRecyclerViewAdapter).setOnItemClickListener(this)
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = shareViewModel
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: Int) =
            SurfaceSoccerFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }

    override fun onItemClick(surface: SurfaceItem) {
        SurfDialogFragment.newInstance(surface.surfaceName,surface.venueName)
            .show(activity?.supportFragmentManager!!, SurfDialogFragment.newInstance(surface.surfaceName,surface.venueName).tag)
    }
}