package com.example.wp.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.wp.R
import com.example.wp.domain.material.Material
import com.example.wp.presentation.listener.StockListener
import kotlinx.android.synthetic.main.item_checkstock.view.*
import java.util.logging.Handler

class CheckStockAdapter(
    val context: Context,
    var data: List<Material>,
    val onSaveListener: StockListener? = null
) :
    RecyclerView.Adapter<CheckStockAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_checkstock, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        fun bindItem(item: Material) {
            with(itemView) {

                item.isEdited = item.increasedQuantity != item.decreasedQuantity
                btnSave.visibility = if (item.isEdited) View.VISIBLE else View.GONE

                btnPlusStok.setOnClickListener {
                    item.stock++
                    item.increasedQuantity++
                    item.decreasedQuantity--
                    item.isEdited = true
                    notifyItemChanged(adapterPosition)
                    val isIncrease = item.increasedQuantity > item.decreasedQuantity
                    onSaveListener?.onIncreaseStockClicked(isIncrease, item.increasedQuantity)
                }

                btnMinesAddStok.setOnClickListener {
                    if (item.stock > 0) item.stock--
                    item.decreasedQuantity++
                    item.increasedQuantity--
                    item.isEdited = false
                    notifyItemChanged(adapterPosition)
                    val isDecrease = item.decreasedQuantity > item.increasedQuantity
                    onSaveListener?.onDecreaseStockClicked(isDecrease, item.decreasedQuantity)
                }

                btnSave.setOnClickListener {
                    onSaveListener?.onSaveStockClicked(adapterPosition, item)
                }

                tvValueAddStok.setText(item.stock.toString())

                tvName.text = item.material
                tvTotalStok.text = item.stock.toString()

                tvValueAddStok.setOnEditorActionListener { v, actionId, event ->
                    if(actionId == EditorInfo.IME_ACTION_DONE){
                        if (v.text.isNotEmpty()){
                            val quantity = v.text.toString().toDouble()
                            if (quantity > item.stock) {
                                item.increasedQuantity = quantity - item.stock
                                onSaveListener?.onIncreaseStockClicked(true, item.increasedQuantity)
                            }else{
                                item.decreasedQuantity = item.stock - quantity
                                onSaveListener?.onDecreaseStockClicked(true, item.decreasedQuantity)
                            }
                            item.isEdited = true
                            item.stock = quantity
                            notifyItemChanged(adapterPosition)
                            return@setOnEditorActionListener true
                        }
                    }
                    return@setOnEditorActionListener false
                }
            }
        }
    }

    fun notifyMenuUpdated(position: Int, material: Material) {
        material.isEdited = false
        material.increasedQuantity = 0.0
        material.decreasedQuantity = 0.0
        notifyItemChanged(position, material)
    }


}