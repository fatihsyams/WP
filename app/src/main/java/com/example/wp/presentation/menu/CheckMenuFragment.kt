package com.example.wp.presentation.menu

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.bidikan.baseapp.ui.WarungPojokFragment
import com.example.wp.R
import com.example.wp.base.BaseEndlessRecyclerViewAdapter
import com.example.wp.domain.menu.Menu
import com.example.wp.presentation.adapter.MenuEndlessAdapter
import com.example.wp.presentation.listener.CreateMenuListener
import com.example.wp.presentation.listener.DeleteMenuListener
import com.example.wp.presentation.viewmodel.MenuViewModel
import com.example.wp.utils.Load
import com.example.wp.utils.showContentView
import com.example.wp.utils.showLoadingView
import com.example.wp.utils.showToast
import kotlinx.android.synthetic.main.fragment_check_menu.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CheckMenuFragment : WarungPojokFragment(), DeleteMenuListener, CreateMenuListener,
    BaseEndlessRecyclerViewAdapter.OnLoadMoreListener {

    private val menuViewModel: MenuViewModel by viewModel()

    private var menuAdapter: MenuEndlessAdapter? = null

    var onCheckMenuClickListener: OnCheckMenuClickListener? = null

    private var listMenu = listOf<Menu>()

    private var isLoadMore = false

    private var currentPage = 1
    private var totalPages = 0

    override val layoutView: Int = R.layout.fragment_check_menu

    override fun onPreparation() {
        (parentFragment as MenusContainerFragment).onCreateMenuListener = this

        if (menuAdapter == null) {
            val gridLayoutManager = GridLayoutManager(requireContext(), 3)
            menuAdapter = MenuEndlessAdapter(
                context = requireContext(),
                datas = mutableListOf(),
                onDeleteMenusListener = this,
                onCheckMenuClickListener = { menu ->
                    onCheckMenuClicked(menu)
                },
                isCheckMenu = true
            )

            menuAdapter?.apply {
                page = currentPage
                totalPage = totalPages
                layoutManager = gridLayoutManager
                onLoadMoreListener = this@CheckMenuFragment
                recyclerView = rvCheckMenus
            }

            rvCheckMenus.apply {
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

    override fun onObserver() {
        menuViewModel.menusLoad.observe(this, Observer {
            when (it) {
                is Load.Loading -> msvCheckMenu.showLoadingView()
                is Load.Fail -> {
                    showToast(it.error.localizedMessage ?: "Error tidak diketahui")
                }
                is Load.Success -> {
                    msvCheckMenu.showContentView()
                    listMenu = it.data.menus
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

        menuViewModel.deleteMenu.observe(this, Observer {
            when (it) {
                is Load.Fail -> {
                    showToast(it.error.localizedMessage ?: "Error tidak diketahui")
                }
                is Load.Success -> {
                    observeMenus()
                    showToast("Berhasil Hapus Data")
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        observeMenus()
    }

    fun filter(query: String) {
        val result = listMenu.filter {
            it.name.contains(query, true).or(false)
        }
        menuAdapter?.notifyAddOrUpdateChanged(result)
    }

    override fun onDeleteClicked(menu: Menu, position: Int) {
        menuViewModel.deleteMenus(menu.id)
    }

    private fun onCheckMenuClicked(menu: Menu) {
        onCheckMenuClickListener?.onCheckMenuClicked(menu)
    }

    interface OnCheckMenuClickListener {
        fun onCheckMenuClicked(menu: Menu)
    }

    override fun onMenuCreated() {
        observeMenus()
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

