package com.wrmh.allmyfood.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.wrmh.allmyfood.R
import com.wrmh.allmyfood.models.ElementModel
import com.wrmh.allmyfood.models.RecipeModel
import kotlinx.android.synthetic.main.card_component.view.*
import kotlinx.android.synthetic.main.non_editable_list_item.view.*

class MyListRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<ElementModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RecipeViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.non_editable_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RecipeViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(elementsList: List<ElementModel>) {
        items = elementsList
    }

    class RecipeViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        private val listEditable: TextView = itemView.view_list_item

        fun bind(listElement: ElementModel) {
            listEditable.text = listElement.desc + ", " + listElement.quantity
        }
    }
}