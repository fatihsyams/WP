package com.example.wp.presentation.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.wp.R
import com.example.wp.base.BaseEndlessRecyclerViewAdapter
import com.example.wp.base.BaseViewHolder
import com.example.wp.domain.menu.Menu
import com.example.wp.presentation.listener.DeleteMenuListener
import com.example.wp.utils.toCurrencyFormat
import kotlinx.android.synthetic.main.item_loading.view.*
import kotlinx.android.synthetic.main.item_menu.view.*

class MenuEndlessAdapter(
    override val context: Context,
    override var datas: MutableList<Menu>,
    val onMenuClickListener: ((menu: Menu) -> Unit)? = null,
    val onDeleteMenusListener: DeleteMenuListener? = null,
    val onCheckMenuClickListener: ((menu: Menu) -> Unit)? = null,
    val isCheckMenu:Boolean = false
) :
    BaseEndlessRecyclerViewAdapter<Menu>(context, datas) {

    companion object {
        val LOAD_MORE_ITEM = Menu()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Menu> {
        return if (viewType == VIEW_TYPE_ITEM) {
            if (isCheckMenu) CheckMenuViewHolder(getView(parent, viewType)) else MenuViewHolder(getView(parent, viewType))
        } else {
            LoadMoreViewHolder(getView(parent, viewType))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (datas[position] != LOAD_MORE_ITEM) {
            VIEW_TYPE_ITEM
        } else {
            VIEW_TYPE_LOAD_MORE
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Menu>, position: Int) {
        holder.bind(data = datas[position])
    }

    override fun getItemResourceLayout(viewType: Int): Int {
        return if (viewType == VIEW_TYPE_ITEM) {
            R.layout.item_menu
        } else {
            R.layout.item_loading
        }
    }

    public override fun setLoadMoreProgress(isProgress: Boolean) {
        isLoadMoreLoading = isProgress
        if (isProgress) {
            datas.add(datas.size, LOAD_MORE_ITEM)
        } else {
            if (datas.size > 0) {
                datas.remove(LOAD_MORE_ITEM)
            }
        }
        notifyDataSetChanged()
    }


    override fun getItemCount() = datas.size

    inner class MenuViewHolder(itemView: View) : BaseViewHolder<Menu>(itemView) {
        override fun bind(item: Menu) {
            with(itemView) {

                if (item.images.isNotEmpty()) {
                    Glide.with(context).load(item.images).into(imgMenus)
                }

                tvHargaMenus.text = toCurrencyFormat(item.price)
                tvNamaMenus.text = item.name
                tvDiscount.text = "${item.discount} %"

                tvSoldOut.visibility =
                    if (item.stock > item.stockRequired) View.GONE else View.VISIBLE
                tvDiscount.visibility = if (item.discount == 0) View.GONE else View.VISIBLE

                tvHargaMenus.setTextColor(
                    if (item.discount == 0) ContextCompat.getColor(
                        context,
                        R.color.colorGrey
                    ) else ContextCompat.getColor(context, R.color.colorRed)
                )

                setOnClickListener {
                    if (item.stock > item.stockRequired) onMenuClickListener?.invoke(item)
                }
            }
        }
    }

    inner class CheckMenuViewHolder(view: View) : BaseViewHolder<Menu>(view) {
        override fun bind(item: Menu) {
            with(itemView) {

                floatingActionButton.setOnClickListener {
                    onDeleteMenusListener?.onDeleteClicked(item, adapterPosition)
                }
                floatingActionButton.setImageResource(R.drawable.ic_baseline_restore_from_trash_24)

                if (item.images.isNotEmpty()) {
                    Glide.with(context).load(item.images).into(imgMenus)
                }

                tvHargaMenus.text = toCurrencyFormat(item.price)
                tvNamaMenus.text = item.name
                tvDiscount.text = "${item.discount} %"

                tvSoldOut.visibility =
                    if (item.stock > item.stockRequired) View.GONE else View.VISIBLE
                tvDiscount.visibility = if (item.discount == 0) View.GONE else View.VISIBLE

                tvHargaMenus.setTextColor(
                    if (item.discount == 0) ContextCompat.getColor(
                        context,
                        R.color.colorGrey
                    ) else ContextCompat.getColor(context, R.color.colorRed)
                )

                setOnClickListener {
                    onCheckMenuClickListener?.invoke(item)
                }
            }

        }
    }


    inner class LoadMoreViewHolder(
        view: View
    ) : BaseViewHolder<Menu>(view) {
        override fun bind(data: Menu) {
            with(itemView) {
                if (isLoadMoreLoading) {
                    pbLoading.visibility = View.VISIBLE
                } else {
                    pbLoading.visibility = View.GONE
                }
            }
        }
    }
}