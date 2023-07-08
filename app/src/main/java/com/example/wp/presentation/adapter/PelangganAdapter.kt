package com.example.wp.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wp.R
import com.example.wp.domain.order.Customer
import com.example.wp.presentation.listener.PelangganListener
import kotlinx.android.synthetic.main.item_pelanggan.view.*

class PelangganAdapter(val context: Context, var datas: List<Customer>, val listener: PelangganListener? = null) :
    RecyclerView.Adapter<PelangganAdapter.PelangganViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PelangganViewHolder {
        return PelangganViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_pelanggan, parent, false)
        )
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: PelangganViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class PelangganViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: Customer) {
            with(itemView) {
                btnPelanggan.text = data.name

                btnPelanggan.setOnClickListener {
                    listener?.onPelangganSelected(data)
                }
            }
        }
    }

    fun updateData(newData: List<Customer>) {
        datas = newData
        notifyDataSetChanged()
    }
}