package com.wrmh.allmyfood.adapters

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wrmh.allmyfood.R
import com.wrmh.allmyfood.models.StepModel
import kotlinx.android.synthetic.main.component_editable_list_item.view.*


class CreateStepRecyclerAdapter(
    private val changeListener: OnChangeListener,
    private val clickListener: OnClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CreateStepViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.component_editable_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CreateStepViewHolder -> {
                holder.itemView.list_editable.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable) {}

                    override fun beforeTextChanged(
                        s: CharSequence, start: Int,
                        count: Int, after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence, start: Int,
                        before: Int, count: Int
                    ) {
                        changeListener.onChange(items[position], position, s)
                    }
                })

                holder.itemView.list_box.setOnClickListener {
                    clickListener.onClick(position, (it as CheckBox).isChecked)
                }

                holder.bind(items[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(elementsList: List<String>) {
        items = elementsList
    }

    class CreateStepViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        private val listEditable: TextView = itemView.list_editable

        fun bind(step: String) {
            listEditable.text = step
        }
    }

    class OnChangeListener(
        val changeListener: (
            list: String,
            position: Int,
            s: CharSequence
        ) -> Unit
    ) {
        fun onChange(
            element: String,
            position: Int,
            s: CharSequence
        ) = changeListener(element, position, s)
    }

    class OnClickListener(
        val clickListener: (pos: Int, check: Boolean) -> Unit
    ) {
        fun onClick(pos: Int, check: Boolean) = clickListener(pos, check)
    }
}