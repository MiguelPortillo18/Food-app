package com.wrmh.allmyfood.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wrmh.allmyfood.R
import com.wrmh.allmyfood.models.IngredientModel
import kotlinx.android.synthetic.main.component_ingredient.view.*

class IngredientsRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return IngredientViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.component_ingredient, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        (holder as IngredientViewHolder).bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(ingredient: List<String>) {
        items = ingredient
    }

    class IngredientViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        private val ingredientMain = itemView.h1_ingredient

        fun bind(ingredient: String) {
            ingredientMain.text = ingredient
        }
    }
}