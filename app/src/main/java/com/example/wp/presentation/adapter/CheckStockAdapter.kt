package com.example.wp.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wp.R
import com.example.wp.data.api.model.response.DataItem
import kotlinx.android.synthetic.main.item_checkstock.view.*
import kotlinx.android.synthetic.main.item_menu.view.*

class CheckStockAdapter(
    val context: Context,
    var data: List<DataItem>
) :
    RecyclerView.Adapter<CheckStockAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_checkstock, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position])
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        fun bindItem(item: DataItem) {
            with(itemView) {

                tvName.text = item.name
                tvTotalStok.text = item.stock.toString()
                if (!item.images.isNullOrEmpty()) {
                    Glide.with(context).load(item.images.first().imageUrl).into(imgStock)
                }

            }
        }
    }

}