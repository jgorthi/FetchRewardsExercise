package com.fetchrewards.ui.adapters

import com.fetchrewards.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView

class NameAdapter : RecyclerView.Adapter<NameAdapter.InnerViewHolder>() {
    private var namesList = mutableListOf<String?>()

    inner class InnerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textName: AppCompatTextView = view.findViewById(R.id.text_name)
    }

    fun setData(names: List<String?>) {
        this.namesList.addAll(names)
        notifyItemInserted(0)
    }

    fun addNamesList(names: List<String?>) {
        this.namesList.addAll(names)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): InnerViewHolder {
        return InnerViewHolder(LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.name_item, viewGroup, false))
    }

    override fun onBindViewHolder(innerViewHolder: InnerViewHolder, position: Int) {
        innerViewHolder.textName.text = namesList[position]
    }

    override fun getItemCount() = namesList.size
}