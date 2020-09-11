package com.example.wp.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wp.R
import com.example.wp.domain.menu.TakeAway
import com.example.wp.presentation.listener.TakeAwayListener
import kotlinx.android.synthetic.main.item_take_away.view.*

class TakeAwayAdapter(val context: Context, var datas: List<TakeAway>, val listener:TakeAwayListener? = null) :
    RecyclerView.Adapter<TakeAwayAdapter.TakeAwayViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TakeAwayViewHolder {
        return TakeAwayViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_take_away, parent, false)
        )
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: TakeAwayViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class TakeAwayViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: TakeAway) {
            with(itemView) {
                tvTakeAway.text = data.name
                Glide.with(context).load(data.logoUrl).into(imgLogo)

                setOnClickListener {
                    listener?.onTakeAwaySelected(data)
                }
            }
        }
    }

    fun updateData(newData: List<TakeAway>) {
        datas = newData
        notifyDataSetChanged()
    }
}