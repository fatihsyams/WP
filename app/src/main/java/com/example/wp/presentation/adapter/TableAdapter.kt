package com.example.wp.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wp.R
import com.example.wp.domain.table.Table
import com.example.wp.presentation.listener.TableListener
import kotlinx.android.synthetic.main.item_table.view.*

class TableAdapter(val context: Context, var datas: List<Table>, val listener:TableListener? = null) :
    RecyclerView.Adapter<TableAdapter.TableViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableViewHolder {
        return TableViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_table, parent, false)
        )
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: TableViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class TableViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: Table) {
            with(itemView) {
                btnTable.text = data.number.toString()

                btnTable.setOnClickListener {
                    listener?.onTableSelected(data)
                }
            }
        }
    }

    fun updateData(newData: List<Table>) {
        datas = newData
        notifyDataSetChanged()
    }
}