package com.fetchrewards.ui.fragments

import com.fetchrewards.Constants
import com.fetchrewards.R
import com.fetchrewards.ui.adapters.IdAdapter
import com.fetchrewards.ui.MainViewModel
import android.os.Bundle
import androidx.core.os.bundleOf
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.setFragmentResult
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.CircularProgressIndicator


class MainFragment : Fragment(), DefaultLifecycleObserver {

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment().apply {
                arguments = Bundle().apply {
                    putString("url", Constants.DATA_URL)
                }
            }
        }
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val idAdapter = IdAdapter()
        val view: View = inflater.inflate(R.layout.main_fragment, container, false)
        val indicator: CircularProgressIndicator = view.findViewById(R.id.load_data)
        val recyclerViewIds: RecyclerView = view.findViewById(R.id.recyclerview_ids)

        recyclerViewIds.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = idAdapter
        }

        idAdapter.onItemClick = { names, key ->
            setFragmentResult(Constants.KEY, bundleOf(Pair("names", names), Pair("key", key)))
            parentFragmentManager.commit {
                replace(R.id.container, NamesFragment.newInstance())
                addToBackStack(Constants.RESPONSE)
            }
        }

        viewModel.responseUrlData.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                idAdapter.setData(response)
                recyclerViewIds.visibility = View.VISIBLE
            }
        }

        viewModel.loadingIndicator.observe(viewLifecycleOwner) { loadingStatus ->
            if (loadingStatus) {
                indicator.visibility = View.VISIBLE
                recyclerViewIds.visibility = View.GONE
            } else {
                indicator.visibility = View.GONE
            }
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.lifecycle?.addObserver(ActivityLifeCycleObserver {
            viewModel = ViewModelProvider(this)[MainViewModel::class.java]
            viewModel.getDataFromUrl(requireArguments().getString("url", Constants.DATA_URL)!!)
        })
    }

}