package com.example.wp.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wp.R
import com.example.wp.domain.menu.getTakeAwayImage
import com.example.wp.domain.order.OrderResult
import com.example.wp.presentation.listener.OrderResultListener
import com.example.wp.utils.emptyString
import kotlinx.android.synthetic.main.item_order.view.*

class OrderResultAdapter(val context: Context, var datas: List<OrderResult>, val listener: OrderResultListener? = null) :
    RecyclerView.Adapter<OrderResultAdapter.OrderResultViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderResultViewHolder {
        return OrderResultViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_order, parent, false)
        )
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: OrderResultViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class OrderResultViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: OrderResult) {
            with(itemView) {
                tvOrderType.text = data.menu.map { it.menuPrice.firstOrNull()?.orderCategory }.firstOrNull()?.name.orEmpty()
                tvOrderName.text = if (data.order.customerName.isEmpty()) context.getString(R.string.label_no_name) else data.order.customerName.toUpperCase()
                val orderImage = data.menu.firstOrNull()?.menuPrice?.firstOrNull()?.menu?.images.orEmpty()
                Glide.with(context).load(orderImage).into(imgOrderType)

                btnBill.setOnClickListener {
                    listener?.onBillClicked(data)
                }

                btnPay.setOnClickListener {
                    listener?.onPayClicked(data)
                }

                btnCancel.setOnClickListener {
                    listener?.onCancelClicked(data)
                }

                itemView.setOnClickListener {
                    listener?.onOrderClicked(data)
                }
            }
        }
    }

    fun updateData(newData: List<OrderResult>) {
        datas = newData
        notifyDataSetChanged()
    }
}