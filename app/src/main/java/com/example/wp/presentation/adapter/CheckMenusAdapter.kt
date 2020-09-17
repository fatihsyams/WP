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
import com.example.wp.presentation.listener.DeleteMenuListener
import kotlinx.android.synthetic.main.item_menu.view.*

class CheckMenusAdapter(
    val context: Context,
    var data: List<Menu>,
    val onDeleteMenusListener: DeleteMenuListener? = null,
    val onCheckMenuClickListener: ((menu: Menu) -> Unit)? = null


) : RecyclerView.Adapter<CheckMenusAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CheckMenusAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_menu, parent, false))
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

                floatingActionButton.setOnClickListener {
                    onDeleteMenusListener?.onDeleteClicked(item, adapterPosition)
                }
                floatingActionButton.setImageResource(R.drawable.ic_baseline_restore_from_trash_24)

                    if (!item.images.isNullOrEmpty()) {
                    Glide.with(context).load(item.images).into(imgMenus)
                }
                tvHargaMenus.text = item.price.toString()
                tvNamaMenus.text = item.name

                setOnClickListener {
                    onCheckMenuClickListener?.invoke(item)
                }
            }

        }
    }

    fun updateDataMenu(newData: List<Menu>) {
        data = newData
        notifyDataSetChanged()
    }

    fun addDataMenus(newData: List<Menu>) {
        data = newData
    }
}