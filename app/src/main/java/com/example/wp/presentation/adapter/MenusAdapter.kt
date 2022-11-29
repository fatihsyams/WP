package com.example.wp.presentation.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wp.R
import com.example.wp.domain.menu.Menu
import com.example.wp.presentation.listener.CalculateMenuListener
import com.example.wp.utils.toCurrencyFormat
import com.example.wp.utils.toStrikethrough
import kotlinx.android.synthetic.main.item_menu.view.*
import kotlinx.android.synthetic.main.item_menu.view.imgMenus
import kotlinx.android.synthetic.main.item_menu.view.tvHargaMenus
import kotlinx.android.synthetic.main.item_menu.view.tvNamaMenus
import kotlinx.android.synthetic.main.item_menu_order.view.*

class MenusAdapter(
    val context: Context,
    var data: List<Menu>,
    val onMenuClickListener: ((menu: Menu) -> Unit)? = null,
    var type: Int = MENU_TYPE,
    val onCalculateMenuListener: CalculateMenuListener? = null
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val MENU_TYPE = 0
        const val ORDER_EDIT_TYPE = 1
        const val ORDER_READ_TYPE = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_menu -> MenuViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_menu, parent, false)
            )
            R.layout.item_menu_order -> OrderViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_menu_order, parent, false)
            )
            else -> OrderViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_menu_order, parent, false)
            )
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (type) {
            MENU_TYPE -> R.layout.item_menu
            ORDER_EDIT_TYPE, ORDER_READ_TYPE -> R.layout.item_menu_order
            else -> R.layout.item_menu_order
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (type == MENU_TYPE) {
            (holder as MenuViewHolder).bindItem(data[position])
        } else {
            (holder as OrderViewHolder).bindItem(data[position])
        }
    }

    inner class MenuViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(item: Menu) {
            with(itemView) {
                if (item.images.isNotEmpty()) {
                    Glide.with(context).load(item.images).into(imgMenus)

                }

                tvHargaMenus.text = toCurrencyFormat(item.menuPrice.firstOrNull()?.price?.toDouble() ?: 0.0)
//                tvDiscountPrice.text = toCurrencyFormat(item.price,item.discount)
                tvNamaMenus.text = item.name
                tvDiscount.text = "${item.discount} %"

//                tvSoldOut.visibility =
//                    if (item.stock > item.stockRequired) View.GONE else View.VISIBLE
                tvDiscount.visibility = if (item.discount == 0) View.GONE else View.VISIBLE
//                tvDiscountPrice.visibility = if (item.discount == 0) View.GONE else View.VISIBLE

//                if (item.discount != 0) tvHargaMenus.toStrikethrough()

                tvHargaMenus.setTextColor(
                    if (item.discount == 0) ContextCompat.getColor(
                        context,
                        R.color.colorGrey
                    ) else ContextCompat.getColor(context, R.color.colorRed)
                )

                setOnClickListener {
                    onMenuClickListener?.invoke(item)
                }
            }
        }
    }

    inner class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(item: Menu) {
            with(itemView) {
                if (item.images.isNotEmpty()) {
                    Glide.with(context).load(item.images).into(imgMenus)
                }

                item.totalPrice = (item.menuPrice.firstOrNull()?.price?.toDouble() ?: 0.0) * item.quantity

                tvHargaMenus.text = toCurrencyFormat(item.totalPrice)


                tvDiscountOrder.text = "${item.discount}%"

//                tvDiscountOrder.visibility = item.discount == 0 View.GONE else View.VISIBLE


                tvNamaMenus.text = item.name
                tvInformation.text = item.additionalInformation
                tvQuantity.text = item.quantity.toString()

                btnMinus.setOnClickListener {
                        item.quantity--
                    item.totalPrice = (item.menuPrice.firstOrNull()?.price?.toDouble() ?: 0.0) * item.quantity
                    onCalculateMenuListener?.onMinuslicked(item, adapterPosition)
                    notifyItemChanged(adapterPosition)
                }




                btnPlus.setOnClickListener {
                        item.quantity++
                    item.totalPrice = (item.menuPrice.firstOrNull()?.price?.toDouble() ?: 0.0) * item.quantity
                    onCalculateMenuListener?.onPlusClicked(item, adapterPosition)
                    notifyItemChanged(adapterPosition)
                }

                btnDelete.setOnClickListener {
                    onCalculateMenuListener?.onDeleteClicked(item, adapterPosition)
//                    notifyItemRemoved(adapterPosition)
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

    fun updateOrderReadType(selectedOrderType: Int) {
        type = selectedOrderType
        notifyDataSetChanged()
    }

}