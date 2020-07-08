package com.wrmh.allmyfood.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wrmh.allmyfood.R
import com.wrmh.allmyfood.models.ElementModel
import com.wrmh.allmyfood.models.StepModel
import kotlinx.android.synthetic.main.component_non_editable_list_item.view.*

class StepsRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<StepModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return StepViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.component_non_editable_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is StepViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(steps: List<StepModel>) {
        items = steps
    }

    class StepViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        private val listEditable: TextView = itemView.view_list_item

        fun bind(step: StepModel) {
            listEditable.text = step.step
        }
    }
}