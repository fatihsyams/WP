package com.example.wp.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wp.R
import com.example.wp.domain.menu.Menu
import com.example.wp.presentation.listener.CalculateMenuListener
import com.example.wp.presentation.listener.StockListener
import kotlinx.android.synthetic.main.item_checkstock.view.*

class CheckStockAdapter(
    val context: Context,
    var data: List<Menu>,
    val onSaveListener: StockListener? = null

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

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        fun bindItem(item: Menu) {
            with(itemView) {

                btnPlusStok.setOnClickListener {
                    item.quantity++
                    notifyItemChanged(adapterPosition)
                }

                btnMinesAddStok.setOnClickListener {
                    if (item.quantity!! > 0) item.quantity--
                    notifyItemChanged(adapterPosition)
                }

                btnSave.setOnClickListener {
                    onSaveListener?.onSeveClicked(item)
                }

                tvValueAddStok.text = item.quantity.toString()

                tvName.text = item.name
                tvTotalStok.text = item.stock.toString()
                if (!item.images.isNullOrEmpty()) {
                    Glide.with(context).load(item.images).into(imgStock)
                }

            }
        }
    }

    fun updateDataMenu(newData: List<Menu>) {
        data = newData
        notifyDataSetChanged()
    }


}