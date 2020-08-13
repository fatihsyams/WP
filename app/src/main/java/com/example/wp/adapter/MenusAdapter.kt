package com.example.wp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.wp.data.DataItem
import com.example.wp.R
import kotlinx.android.synthetic.main.item_menu.view.*

class MenusAdapter(val context: Context, val data: List<DataItem>) :
    RecyclerView.Adapter<MenusAdapter.ViewHolder>(), Filterable {


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

    override fun getFilter(): Filter {
        TODO("Not yet implemented")
    }


//    override fun getFilter(): Filter {
//        return object : Filter() {
//            override fun performFiltering(constraint: CharSequence?): FilterResults {
//                val charSearch = constraint.toString()
//                filterNamaMenus = if (charSearch.isEmpty()) {
//                    data
//                } else {
//                    val resultList = ArrayList<ResponseMenu>()
//                    for (row in data) {
//                        if (row.name.toLowerCase(Locale.ROOT).contains(
//                                charSearch.toLowerCase(
//                                    Locale.ROOT
//                                )
//                            )
//                        ) {
//                            resultList.add(row)
//                        }
//                    }
//                    resultList
//                }
//                val filterResults = FilterResults()
//                filterResults.values = filterNamaMenus
//                return filterResults
//            }
//
//            @Suppress("UNCHECKED_CAST")
//            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
//                filterNamaMenus = results?.values as ArrayList<ResponseMenu>
//                notifyDataSetChanged()
//            }
//        }
//    }
}