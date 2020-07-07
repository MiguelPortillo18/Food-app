package com.wrmh.allmyfood.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.wrmh.allmyfood.R
import com.wrmh.allmyfood.models.ListModel
import com.wrmh.allmyfood.views.HomeActivity
import com.wrmh.allmyfood.views.lists.MyListFragment
import kotlinx.android.synthetic.main.list_overview_component.view.*

class SelectionListRecyclerAdapter(hostActivity: AppCompatActivity) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private var items: List<ListModel> = ArrayList()
    private val hostingActivity = hostActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_overview_component, parent, false),
            hostingActivity
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ListViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(listLists: List<ListModel>) {
        items = listLists
    }

    class ListViewHolder constructor(
        itemView: View,
        hostActivity: AppCompatActivity
    ) : RecyclerView.ViewHolder(itemView) {
        private val listTitle: TextView = itemView.list_title
        private val hostingActivity = hostActivity

        fun bind(list: ListModel) {
            listTitle.text = list.name
            listTitle.setOnClickListener{
                hostingActivity.supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment_container, MyListFragment(list)).commit()
            }
        }
    }
}