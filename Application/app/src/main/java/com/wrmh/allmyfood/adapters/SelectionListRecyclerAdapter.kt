package com.wrmh.allmyfood.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wrmh.allmyfood.R
import com.wrmh.allmyfood.models.ListModel
import kotlinx.android.synthetic.main.component_list_overview.view.*

class SelectionListRecyclerAdapter(
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<ListModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.component_list_overview, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]

        holder.itemView.setOnClickListener{
            onClickListener.onClick(item)
        }
        (holder as ListViewHolder).bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(listLists: List<ListModel>) {
        items = listLists
    }

    class ListViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        private val listTitle: TextView = itemView.list_title

        fun bind(list: ListModel) {
            listTitle.text = list.name
        }
    }

    class OnClickListener(val clickListener: (list: ListModel) -> Unit) {
        fun onClick(list: ListModel) = clickListener(list)
    }
}