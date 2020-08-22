package com.example.wp.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wp.R
import com.example.wp.data.api.model.response.DataItem
import kotlinx.android.synthetic.main.item_menu.view.*

class MenusAdapter(val context: Context, var data: List<DataItem>) :
    RecyclerView.Adapter<MenusAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item_menu, parent, false)
        return ViewHolder(v)
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
                if (!item.images.isNullOrEmpty()) {
                    Glide.with(context).load(item.images.first().imageUrl).into(imgMenus)
                }
                tvHargaMenus.text = item.price.toString()
                tvNamaMenus.text = item.name
            }
        }
    }

    fun updateDataMenu(newData: List<DataItem>) {
        data = newData
        notifyDataSetChanged()
    }

    fun addDataMenus(newData: List<DataItem>) {
        data = newData
    }

}