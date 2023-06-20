package com.example.wp.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wp.R
import com.example.wp.domain.payment.Payment
import com.example.wp.presentation.listener.PaymentListener
import kotlinx.android.synthetic.main.item_payment.view.*

class PaymentAdapter(val context: Context, var datas: List<Payment>, val listener: PaymentListener? = null) :
    RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        return PaymentViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_payment, parent, false)
        )
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class PaymentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: Payment) {
            with(itemView) {
                btnPayment.text = data.name

                btnPayment.setOnClickListener {
                    listener?.onPaymentSelected(data)
                }
            }
        }
    }

    fun updateData(newData: List<Payment>) {
        datas = newData
        notifyDataSetChanged()
    }
}