package com.example.wp.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wp.R
import com.example.wp.domain.menu.Category
import com.example.wp.presentation.listener.MenuCategoryListener
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryAdapter(val context:Context, var datas:List<Category>, val menuCategoryListener: MenuCategoryListener? = null):RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.item_category, parent, false))
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class CategoryViewHolder(view:View):RecyclerView.ViewHolder(view){
        fun bind(data:Category){
            with(itemView){
                btnCategory.text = data.name

                setOnClickListener {
                    menuCategoryListener?.onCategoryClicked(data)
                }
            }
        }
    }

    fun updateData(newData: List<Category>) {
        datas = newData
        notifyDataSetChanged()
    }

}