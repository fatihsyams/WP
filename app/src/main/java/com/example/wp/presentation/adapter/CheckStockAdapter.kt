package com.example.wp.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wp.R
import com.example.wp.domain.material.Material
import com.example.wp.presentation.listener.StockListener
import kotlinx.android.synthetic.main.item_checkstock.view.*

class CheckStockAdapter(
    val context: Context,
    var data: List<Material>,
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


        fun bindItem(item: Material) {
            with(itemView) {

                btnPlusStok.setOnClickListener {
                    item.stock++
                    notifyItemChanged(adapterPosition)
                }

                btnMinesAddStok.setOnClickListener {
                    if (item.stock > 0) item.stock--
                    notifyItemChanged(adapterPosition)
                }

                btnSave.setOnClickListener {
                    onSaveListener?.onSaveStockClicked(item)
                }

                tvValueAddStok.text = item.stock.toString()

                tvName.text = item.material
                tvTotalStok.text = item.stock.toString()
            }
        }
    }

    fun updateDataMenu(newData: List<Material>) {
        data = newData
        notifyDataSetChanged()
    }


}