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
import com.example.wp.utils.toCurrencyFormat
import kotlinx.android.synthetic.main.item_menu.view.*
import kotlinx.android.synthetic.main.item_menu.view.imgMenus
import kotlinx.android.synthetic.main.item_menu.view.tvHargaMenus
import kotlinx.android.synthetic.main.item_menu.view.tvNamaMenus
import kotlinx.android.synthetic.main.item_order.view.*

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
        const val ORDER_READ_GRAB_FOOD_TYPE = 3
        const val ORDER_READ_GO_FOOD_TYPE = 4
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_menu -> MenuViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_menu, parent, false)
            )
            R.layout.item_order -> OrderViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_order, parent, false)
            )
            else -> OrderViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_order, parent, false)
            )
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (type) {
            MENU_TYPE -> R.layout.item_menu
            ORDER_EDIT_TYPE, ORDER_READ_TYPE -> R.layout.item_order
            else -> R.layout.item_order
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
                tvHargaMenus.text =toCurrencyFormat(item.price)
                tvNamaMenus.text = item.name

                tvSoldOut.visibility = if(item.stock > item.stockRequired) View.GONE else View.VISIBLE

                setOnClickListener {
                    if (item.stock > item.stockRequired) onMenuClickListener?.invoke(item)
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

                tvHargaMenus.text = when (type) {
                    ORDER_EDIT_TYPE -> toCurrencyFormat(item.price*item.quantity)
                    ORDER_READ_GO_FOOD_TYPE ->  toCurrencyFormat(item.goFoodPrice*item.quantity)
                    ORDER_READ_GRAB_FOOD_TYPE -> toCurrencyFormat(item.grabFoodPrice*item.quantity)
                    else ->  toCurrencyFormat(item.price*item.quantity)
                }

                tvNamaMenus.text = item.name
                tvInformation.text = item.additionalInformation
                tvQuantity.text = item.quantity.toString()

                btnMinus.setOnClickListener {
                    val realQuantity = item.quantity*item.stockRequired
                    if (realQuantity > item.stockRequired) {
                        item.quantity--
                        item.totalPrice =  when (type) {
                            ORDER_EDIT_TYPE -> item.price*item.quantity
                            ORDER_READ_GO_FOOD_TYPE ->  item.goFoodPrice*item.quantity
                            ORDER_READ_GRAB_FOOD_TYPE -> item.grabFoodPrice*item.quantity
                            else ->  item.price*item.quantity
                        }
                    }
                    onCalculateMenuListener?.onMinuslicked(item, adapterPosition)
                    notifyItemChanged(adapterPosition)
                }

                btnPlus.setOnClickListener {
                    val realQuantity = item.quantity*item.stockRequired
                    if (realQuantity < item.stock) {
                        item.quantity++
                        item.totalPrice =  when (type) {
                            ORDER_EDIT_TYPE -> item.price*item.quantity
                            ORDER_READ_GO_FOOD_TYPE ->  item.goFoodPrice*item.quantity
                            ORDER_READ_GRAB_FOOD_TYPE -> item.grabFoodPrice*item.quantity
                            else ->  item.price*item.quantity
                        }
                    }
                    onCalculateMenuListener?.onPlusClicked(item, adapterPosition)
                    notifyItemChanged(adapterPosition)
                }

                btnDelete.setOnClickListener {
                    onCalculateMenuListener?.onDeleteClicked(item, adapterPosition)
                    notifyItemRemoved(adapterPosition)
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

    fun updateOrderReadType(selectedOrderType:Int){
        type = selectedOrderType
        notifyDataSetChanged()
    }

}