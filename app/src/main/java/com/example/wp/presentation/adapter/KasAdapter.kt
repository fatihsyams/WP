package com.example.wp.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wp.R
import com.example.wp.domain.order.Wallet
import com.example.wp.presentation.listener.KasListener
import kotlinx.android.synthetic.main.item_kas.view.*


class KasAdapter(val context: Context, var datas: List<Wallet>, val listener: KasListener? = null) :
    RecyclerView.Adapter<KasAdapter.KasViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KasViewHolder {
        return KasViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_kas, parent, false)
        )
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: KasViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class KasViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: Wallet) {
            with(itemView) {
                btnKas.text = data.name

                btnKas.setOnClickListener {
                    listener?.onKasSelected(data)
                }
            }
        }
    }

    fun updateData(newData: List<Wallet>) {
        datas = newData
        notifyDataSetChanged()
    }
}