package com.example.wp.presentation.checkmenu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.bidikan.baseapp.ui.WarungPojokFragment
import com.example.wp.R
import com.example.wp.domain.menu.Menu
import com.example.wp.presentation.adapter.CheckMenusAdapter
import com.example.wp.presentation.adapter.MenusAdapter
import com.example.wp.presentation.listener.DeleteMenuListener
import com.example.wp.presentation.viewmodel.MenuViewModel
import com.example.wp.utils.Load
import com.example.wp.utils.showContentView
import com.example.wp.utils.showLoadingView
import com.example.wp.utils.showToast
import kotlinx.android.synthetic.main.fragment_check_menu.*
import kotlinx.android.synthetic.main.fragment_pesanan.*
import kotlinx.android.synthetic.main.fragment_pesanan.searchView
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale.filter


class CheckMenuFragment : WarungPojokFragment(), DeleteMenuListener {

    private val menuViewModel: MenuViewModel by viewModel()
    private val checkMenuAdapter: CheckMenusAdapter by lazy {
        CheckMenusAdapter(
            context = requireContext(),
            data = listOf(),
            onDeleteMenusListener = this
        )
    }

    override val layoutView: Int = R.layout.fragment_check_menu
    private var listMenu = listOf<Menu>()


    override fun onPreparation() {
    }

    override fun onIntent() {
    }

    override fun onView() {
    }

    override fun onAction() {
        searchViewCheckMenus.setOnQueryTextListener(object :
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

    private fun showMenus(data: List<Menu>) {
//        showCategories()
        checkMenuAdapter.addDataMenus(data)
        listMenu = data
        rvCheckMenus.apply {
            adapter = checkMenuAdapter
            layoutManager = GridLayoutManager(context, 3)
        }
    }

    override fun onObserver() {
        menuViewModel.getMenus()
        menuViewModel.menusLoad.observe(this, Observer {
            when (it) {
                is Load.Loading -> msvCheckMenu.showLoadingView()
                is Load.Fail -> {
                    showToast(it.error.localizedMessage ?: "Error tidak diketahui")
                }
                is Load.Success -> {
                    msvCheckMenu.showContentView()
                    showMenus(it.data)
                }
            }
        })

        menuViewModel.deleteMenu.observe(this, Observer {
            when (it) {
                is Load.Fail -> {
                    showToast(it.error.localizedMessage ?: "Error tidak diketahui")
                }
                is Load.Success -> {
                    menuViewModel.getMenus()
                    showToast("Berhasil Hapus Data")
                }
            }
        })
    }

    fun filter(query: String) {
        val result = listMenu.filter {
            it.name.contains(query, true).or(false)
        }
        checkMenuAdapter.updateDataMenu(result)
    }

    override fun onDeleteClicked(menu: Menu, position: Int) {
        menuViewModel.deleteMenus(menu.id)
    }


}