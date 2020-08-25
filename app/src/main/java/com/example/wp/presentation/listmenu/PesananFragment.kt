package com.example.wp.presentation.listmenu

import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.bidikan.baseapp.ui.WarungPojokFragment
import com.example.wp.R
import com.example.wp.data.api.model.response.DataItem
import com.example.wp.presentation.adapter.MenusAdapter
import com.example.wp.utils.loadFragment
import kotlinx.android.synthetic.main.fragment_pesanan.*

class PesananFragment : WarungPojokFragment(),
    PesananInterface.View {

    lateinit var presenter: PesananPresenter

    var onMenuClickListener:OnMenuClickListener? = null

    private val menuAdapter: MenusAdapter by lazy {
        MenusAdapter(
            context = requireContext(),
            data = listOf(),
            onMenuClickListener = {
            onMenuClicked(it)
        })
    }

    var listMenu = listOf<DataItem>()

    override val layoutView: Int = R.layout.fragment_pesanan

    override fun onPreparation() {
        presenter = PesananPresenter(this)
        context?.let { presenter.initSession(it) }
        presenter.logicData()
    }

    override fun onIntent() {
    }

    override fun onView() {
    }

    override fun onAction() {
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filter(query.orEmpty())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filter(newText.orEmpty())
                return false
            }

        })
    }

    override fun onObserver() {
    }

    override fun showData(data: List<DataItem>) {
        Log.d("data", "${data.size}")
        menuAdapter.addDataMenus(data)
        listMenu = data
        rvMenus.apply {
            adapter = menuAdapter
            layoutManager = GridLayoutManager(context, 3)
        }
    }

    override fun alertSuccess(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    override fun alertFailed(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    private fun onMenuClicked(menu:DataItem){
        onMenuClickListener?.onMenuClicked(menu)
    }

    fun filter(query: String) {
        val result = listMenu.filter {
            it.name?.contains(query, true)!!.or(false)
        }
        menuAdapter.updateDataMenu(result)
    }

    interface OnMenuClickListener{
        fun onMenuClicked(menu: DataItem)
    }

}