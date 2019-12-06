package com.pmlnunes.healthcode

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class HistoryAdapter(val items : MutableList<Product>, val context: Context) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    var onItemClick: ((Product) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.history_product_item, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(items[position])
    }

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }


    inner class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        fun bindItems(p: Product) {
            var img = itemView?.findViewById(R.id.history_product_img) as ImageView
            var name = itemView?.findViewById(R.id.history_product_name) as TextView

            Picasso.get().load(p.picture).into(img);
            name.text = p.name

        }

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(items[adapterPosition])
            }
        }

    }

}

