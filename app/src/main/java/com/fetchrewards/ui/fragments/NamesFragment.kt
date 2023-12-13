package com.fetchrewards.ui.fragments

import com.fetchrewards.Constants
import com.fetchrewards.R
import com.fetchrewards.ui.adapters.NameAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView



class NamesFragment: Fragment() {

    companion object {
        fun newInstance() = NamesFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view: View = inflater.inflate(R.layout.names_fragment, container, false)

        var count = 25
        val inc = 25
        val nameAdapter = NameAdapter()
        val recyclerViewNames: RecyclerView = view.findViewById(R.id.recyclerview_names)
        recyclerViewNames.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = nameAdapter
        }

        setFragmentResultListener(Constants.KEY) { _, bundle ->
            val names = bundle.getStringArrayList("names")
            names?.apply {
                sortedBy { it!!.substring(5, it.length).toInt()
                }.also {
                    nameAdapter.setData(it.slice(0..if (inc < it.size) inc else it.size - 1))
                    recyclerViewNames.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                            super.onScrolled(recyclerView, dx, dy)
                            val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                            if (linearLayoutManager.findLastCompletelyVisibleItemPosition() ==
                                nameAdapter.itemCount - 4 && nameAdapter.itemCount < it.size) {
                                nameAdapter.addNamesList(it.slice(count..if (count+inc < it.size) count+inc else it.size - 1))
                                recyclerView.post {
                                    nameAdapter.notifyItemInserted(nameAdapter.itemCount - 21)
                                }
                                count += inc
                            }
                        }
                    })
                }
            }

        }
        return view
    }
}