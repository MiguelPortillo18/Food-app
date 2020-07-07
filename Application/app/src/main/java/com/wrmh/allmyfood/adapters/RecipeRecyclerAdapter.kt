package com.wrmh.allmyfood.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.wrmh.allmyfood.R
import com.wrmh.allmyfood.models.RecipeModel
import kotlinx.android.synthetic.main.card_component.view.*

class RecipeRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<RecipeModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RecipeViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_component, parent, false)
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

    fun submitList(recipeList: List<RecipeModel>) {
        items = recipeList
    }

    class RecipeViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        private val recipeTitle: TextView = itemView.recipe_title
        private val recipeImage: ImageView = itemView.recipe_image
        private val recipeDesc: TextView = itemView.recipe_desc

        fun bind(recipe: RecipeModel) {
            recipeTitle.text = recipe.title

            if(recipe.desc.length > 150)
                recipeDesc.text = recipe.desc.substring(150) + "..."
            else
                recipeDesc.text = recipe.desc

            if(recipe.recipeImage != "INF")
                Picasso.get().load(recipe.recipeImage).into(recipeImage)
        }
    }
}