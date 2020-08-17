package com.example.wp.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wp.data.DataItem
import com.example.wp.R
import kotlinx.android.synthetic.main.item_menu.view.*

class MenusAdapter(val context: Context, var data: List<DataItem>) :
    RecyclerView.Adapter<MenusAdapter.ViewHolder>() {


//    var filterNamaMenus : List<ResponseMenu>? = null
//
//    init {
//        filterNamaMenus = data
//    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item_menu, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position])
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val harga = view.tvHargaMenus

        //        val kategori = view.tvKeteranganMenus
        val namaMenu = view.tvNamaMenus

        fun bindItem(item: DataItem) {
            harga.text = item.price.toString()
//            kategori.text = item.category
            namaMenu.text = item.name
        }
    }

   fun updateDataMenu(newData: List<DataItem>) {
       data = newData
       notifyDataSetChanged()
   }

    fun addDataMenus(newData: List<DataItem>) {
        data = newData
    }


}