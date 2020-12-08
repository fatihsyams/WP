package com.example.wp.presentation.menu

import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bidikan.baseapp.ui.WarungPojokFragment
import com.example.wp.R
import com.example.wp.base.BaseEndlessRecyclerViewAdapter
import com.example.wp.domain.menu.Category
import com.example.wp.domain.menu.Menu
import com.example.wp.presentation.adapter.CategoryAdapter
import com.example.wp.presentation.adapter.MenuEndlessAdapter
import com.example.wp.presentation.adapter.MenusAdapter
import com.example.wp.presentation.listener.MenuCategoryListener
import com.example.wp.presentation.viewmodel.MaterialViewModel
import com.example.wp.presentation.viewmodel.MenuViewModel
import com.example.wp.utils.Load
import com.example.wp.utils.showContentView
import com.example.wp.utils.showLoadingView
import com.example.wp.utils.showToast
import kotlinx.android.synthetic.main.fragment_menus.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenusFragment : WarungPojokFragment(), MenuCategoryListener,
    BaseEndlessRecyclerViewAdapter.OnLoadMoreListener {

    private val menuViewModel: MenuViewModel by viewModel()
    private val materialViewModel: MaterialViewModel by viewModel()

    var onMenuClickListener: OnMenuClickListener? = null

    private var menuAdapter: MenuEndlessAdapter? = null

    private val categoryAdapter: CategoryAdapter by lazy {
        CategoryAdapter(
            context = requireContext(),
            datas = mutableListOf(),
            menuCategoryListener = this
        )
    }

    private var listMenu = listOf<Menu>()
    private var listUpdatedMenu = mutableListOf<Menu>()

    private var isLoadMore = false

    private var currentPage = 1
    private var totalPages = 0

    override val layoutView: Int = R.layout.fragment_menus

    override fun onPreparation() {
        if (menuAdapter == null) {
            val gridLayoutManager = GridLayoutManager(requireContext(), 3)
            menuAdapter = MenuEndlessAdapter(
                context = requireContext(),
                datas = mutableListOf(),
                onMenuClickListener = { menu ->
                    onMenuClicked(menu)
                })

            menuAdapter?.apply {
                page = currentPage
                totalPage = totalPages
                layoutManager = gridLayoutManager
                onLoadMoreListener = this@MenusFragment
                recyclerView = rvMenus
            }

            rvMenus.apply {
                layoutManager = gridLayoutManager
                adapter = menuAdapter
            }
        }
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
        observeMenus()

        menuViewModel.menusLoad.observe(this, Observer {
            when (it) {
                is Load.Loading -> {
                    msvMenu.showLoadingView()
                    menuAdapter?.setLoadMoreProgress(true)
                }
                is Load.Fail -> {
                    showToast(it.error.localizedMessage)
                }
                is Load.Success -> {
                    msvMenu.showContentView()
                    listMenu = it.data.menus
                    listUpdatedMenu.clear()
                    showMenus(listMenu)
                    isLoadMore = false
                    menuAdapter?.setLoadMoreProgress(false)
                    totalPages = it.data.totalPage
                    menuAdapter?.totalPage = totalPages
                    menuAdapter?.notifyAddOrUpdateChanged(listMenu)
                    if (listMenu.isEmpty()) {
                        if (isLoadMore) {
                            isLoadMore = false
                            menuAdapter?.setLoadMoreProgress(false)
                            menuAdapter?.removeScrollListener()
                        } else {
                            menuAdapter?.datas?.clear()
                            showToast("Tidak ada menu")
                        }
                    }

                }
            }
        })

        menuViewModel.getCategories()
        menuViewModel.categoriesLoad.observe(this, Observer {
            when (it) {
                is Load.Fail -> {
                    showToast(it.error.localizedMessage)
                }
                is Load.Success -> {
                    showCategories(it.data)
                }
            }
        })

        materialViewModel.getMaterialMenuLoad.observe(this, Observer { it ->
            when (it) {
                is Load.Fail -> {
                    showToast(it.error.localizedMessage)
                }
                is Load.Success -> {
                    val materials = it.data

                    if (materials.isNotEmpty()) {
                        val menuId = materials.first().menuId
                        val menu = listMenu.find { menu -> menu.id == menuId }
                        menu?.let { newMenu ->
                            newMenu.materialMenus = materials
                            newMenu.stock =
                                materials.map { materialMenu -> materialMenu.material.stock }.sum()
                            menu.stockRequired = materials.map { it.stockRequired }.sum()
                            listUpdatedMenu.remove(newMenu)
                            listUpdatedMenu.add(newMenu)
                            Log.d("UPDATEDMENU", "$listUpdatedMenu")
                        }
                    }

                    if (listUpdatedMenu.size == listMenu.size) {
                        showMenus(listUpdatedMenu)
                    }
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

    private fun getMenuMaterials(data: List<Menu>) {
        data.forEach { menu ->
            materialViewModel.getMaterialMenus(menu.id)
        }
    }

    private fun showMenus(data: List<Menu>) {
        menuAdapter?.notifyAddOrUpdateChanged(data)
    }

    private fun onMenuClicked(menu: Menu) {
        onMenuClickListener?.onMenuClicked(menu)
    }

    fun filter(query: String) {
        val result = listMenu.filter {
            it.name.contains(query, true).or(false)
        }
        menuAdapter?.notifyAddOrUpdateChanged(result)
    }

    interface OnMenuClickListener {
        fun onMenuClicked(menu: Menu)
    }

    override fun onCategoryClicked(data: Category) {
        menuViewModel.getMenus(data.id, currentPage)
    }

    override fun onLoadMore() {
        isLoadMore = true
        menuAdapter?.setLoadMoreProgress(true)
        currentPage += 1
        menuAdapter?.page = currentPage
        observeMenus()
    }

    private fun observeMenus() {
        menuViewModel.getMenus(page = currentPage)
    }

}