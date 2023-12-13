package com.fetchrewards.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.fetchrewards.R


class IdAdapter : RecyclerView.Adapter<IdAdapter.InnerViewHolder>() {
    private var idsList = listOf<Int>()
    private var data = mapOf<Int, List<String?>>()
    var onItemClick: ((List<String?>, Int) -> Unit)? = null

    inner class InnerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textHeader: AppCompatTextView = view.findViewById(R.id.text_header)
        init {
            view.setOnClickListener {
                onItemClick?.invoke(data[idsList[adapterPosition]]!!, idsList[adapterPosition])
            }
        }
    }

    fun setData(data: Map<Int, List<String?>>) {
        idsList = data.keys.toList()
        this.data = data
        notifyItemInserted(0)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): InnerViewHolder {
        return InnerViewHolder(LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.listid_item, viewGroup, false))
    }

    override fun onBindViewHolder(innerViewHolder: InnerViewHolder, position: Int) {
        innerViewHolder.textHeader.text = idsList[position].toString()
    }

    override fun getItemCount() = data.size
}