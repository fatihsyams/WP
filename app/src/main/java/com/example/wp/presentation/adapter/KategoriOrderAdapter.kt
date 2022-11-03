package com.example.wp.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wp.R

import com.example.wp.domain.kategoriorder.KategoriOrder
import com.example.wp.presentation.listener.KategoriOrderListener
import kotlinx.android.synthetic.main.item_kategori_order.view.*


class KategoriOrderAdapter(val context: Context, var datas: List<KategoriOrder>, val listener: KategoriOrderListener? = null) :
    RecyclerView.Adapter<KategoriOrderAdapter.KategoriOrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KategoriOrderViewHolder {
        return KategoriOrderViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_kategori_order, parent, false)
        )
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: KategoriOrderViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class KategoriOrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: KategoriOrder) {
            with(itemView) {
                btnKategoriOrder.text = data.name

                btnKategoriOrder.setOnClickListener {
                    listener?.onKategoriOrderSelected(data)
                }
            }
        }
    }

    fun updateData(newData: List<KategoriOrder>) {
        datas = newData
        notifyDataSetChanged()
    }
}