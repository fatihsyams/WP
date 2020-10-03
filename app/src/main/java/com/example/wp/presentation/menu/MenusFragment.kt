package com.example.wp.presentation.menu

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bidikan.baseapp.ui.WarungPojokFragment
import com.example.wp.R
import com.example.wp.domain.menu.Category
import com.example.wp.domain.menu.Menu
import com.example.wp.presentation.adapter.CategoryAdapter
import com.example.wp.presentation.adapter.MenusAdapter
import com.example.wp.presentation.listener.MenuCategoryListener
import com.example.wp.presentation.viewmodel.MenuViewModel
import com.example.wp.utils.Load
import com.example.wp.utils.showContentView
import com.example.wp.utils.showLoadingView
import com.example.wp.utils.showToast
import kotlinx.android.synthetic.main.fragment_menus.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenusFragment : WarungPojokFragment(), MenuCategoryListener {

    private val menuViewModel: MenuViewModel by viewModel()

    var onMenuClickListener: OnMenuClickListener? = null

    private val menuAdapter: MenusAdapter by lazy {
        MenusAdapter(
            context = requireContext(),
            data = listOf(),
            onMenuClickListener = {
                onMenuClicked(it)
            })
    }

    private val categoryAdapter: CategoryAdapter by lazy {
        CategoryAdapter(
            context = requireContext(),
            datas = mutableListOf(),
            menuCategoryListener = this
        )
    }

    private var listMenu = listOf<Menu>()

    override val layoutView: Int = R.layout.fragment_menus

    override fun onPreparation() {
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
        menuViewModel.getMenus()
        menuViewModel.menusLoad.observe(this, Observer {
            when (it) {
                is Load.Loading -> msvMenu.showLoadingView()
                is Load.Fail -> {
                    showToast(it.error.localizedMessage ?: "Error tidak diketahui")
                }
                is Load.Success -> {
                    msvMenu.showContentView()
                    showMenus(it.data)
                    if (it.data.isEmpty()) showToast("Tidak ada data")
                }
            }
        })

        menuViewModel.getCategories()
        menuViewModel.categoriesLoad.observe(this, Observer {
            when (it) {
                is Load.Fail -> {
                    showToast(it.error.localizedMessage ?: "Error tidak diketahui")
                }
                is Load.Success -> {
                    showCategories(it.data)
                }
            }
        })
    }

    private fun showCategories(categories: List<Category>) {
        categoryAdapter.updateData(categories)
        rvCategory.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }
    }

    private fun showMenus(data: List<Menu>) {
        menuAdapter.addDataMenus(data)
        listMenu = data
        rvMenus.apply {
            adapter = menuAdapter
            layoutManager = GridLayoutManager(context, 3)
        }
    }

    private fun onMenuClicked(menu: Menu) {
        onMenuClickListener?.onMenuClicked(menu)
    }

    fun filter(query: String) {
        val result = listMenu.filter {
            it.name.contains(query, true).or(false)
        }
        menuAdapter.updateDataMenu(result)
    }

    interface OnMenuClickListener {
        fun onMenuClicked(menu: Menu)
    }

    override fun onCategoryClicked(data: Category) {
        menuViewModel.getMenus(data.id)
    }

}