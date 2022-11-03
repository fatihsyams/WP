package com.example.wp.presentation.menu

import android.annotation.SuppressLint
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bidikan.baseapp.ui.WarungPojokFragment
import com.example.wp.R
import com.example.wp.base.BaseEndlessRecyclerViewAdapter
import com.example.wp.domain.kategoriorder.KategoriOrder
import com.example.wp.domain.menu.Category
import com.example.wp.domain.menu.Menu
import com.example.wp.presentation.adapter.CategoryAdapter
import com.example.wp.presentation.adapter.KategoriOrderAdapter
import com.example.wp.presentation.adapter.MenuEndlessAdapter
import com.example.wp.presentation.listener.KategoriOrderListener
import com.example.wp.presentation.listener.MenuCategoryListener
import com.example.wp.presentation.main.MainActivity
import com.example.wp.presentation.viewmodel.MenuViewModel
import com.example.wp.presentation.viewmodel.OrderViewModel
import com.example.wp.utils.*
import com.example.wp.utils.custom.CustomNpaGridLayoutManager
import kotlinx.android.synthetic.main.fragment_menus.*
import kotlinx.android.synthetic.main.layout_alert_option.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenusFragment : WarungPojokFragment(), MenuCategoryListener,
    BaseEndlessRecyclerViewAdapter.OnLoadMoreListener {

    private val menuViewModel: MenuViewModel by viewModel()
    private val orderViewModel: OrderViewModel by viewModel()
    var onMenuClickListener: OnMenuClickListener? = null

    private var menuAdapter: MenuEndlessAdapter? = null

    private val categoryAdapter: CategoryAdapter by lazy {
        CategoryAdapter(
            context = requireContext(),
            datas = mutableListOf(),
            menuCategoryListener = this
        )
    }

    private var listMenu = mutableListOf<Menu>()

    private var isLoadMore = false

    private var currentPage = 1
    private var totalPages = 0
    private var firstPage = 1

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
                query?.let { menuViewModel.getMenus(it) }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()){
                    observeMenus(page = currentPage)
                }else{
                    menuViewModel.getMenus(newText)
                }
                return false
            }
        })

        searchView.setOnCloseListener {
            observeMenus(firstPage)
            return@setOnCloseListener true
        }
        btnPilihKategoriOrder.setOnClickListener {
           orderViewModel.getKategoriOrder()
        }
    }

    override fun onObserver() {
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
                    listMenu.addAll(it.data.menus)
                    isLoadMore = false
                    menuAdapter?.setLoadMoreProgress(false)
                    totalPages = it.data.totalPage
                    menuAdapter?.totalPage = totalPages
                    menuAdapter?.notifyAddOrUpdateChanged(it.data.menus)

                    if (it.data.menus.isEmpty()) {
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

        orderViewModel.kategoriOrder.observe(this, Observer {
            when (it) {
                is Load.Fail -> {
                    showToast(it.error.localizedMessage)
                }
                is Load.Success -> {
                    showKategoriOrder(it.data)
                }
            }
        })

        menuViewModel.searchMenuLoad.observe(this, Observer {
            when (it) {
                is Load.Loading -> {
                    menuAdapter?.clear()
                    msvMenu.showLoadingView()
                }
                is Load.Fail -> {
                    showToast(it.error.localizedMessage)
                }
                is Load.Success -> {
                    msvMenu.showContentView()
                    listMenu.addAll(it.data)
                    menuAdapter?.notifyAddOrUpdateChanged(it.data)

                    if (it.data.isEmpty()) {
                        menuAdapter?.datas?.clear()
                        showToast("Tidak ada menu")
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

    private fun onMenuClicked(menu: Menu) {
        onMenuClickListener?.onMenuClicked(menu)
    }

    fun filter(query: String) {
        val result = listMenu.filter {
            it.name.contains(query, true).or(false)
        }
        menuAdapter?.clear()
        menuAdapter?.notifyAddOrUpdateChanged(result)
    }

    interface OnMenuClickListener {
        fun onMenuClicked(menu: Menu)
    }

    override fun onCategoryClicked(data: Category) {
        searchView.setQuery(emptyString(),false)
        searchView.clearFocus()
        listMenu.clear()
        menuViewModel.getMenus(data.id, firstPage)
        menuAdapter?.clear()
    }

    override fun onLoadMore() {
        isLoadMore = true
        menuAdapter?.setLoadMoreProgress(true)
        currentPage += 1
        menuAdapter?.page = currentPage
        observeMenus(currentPage)
    }

    private fun observeMenus(page: Int) {
        menuViewModel.getMenus(page = page)
    }

    private fun preparingAdapter(){
        if (menuAdapter == null) {
            val gridLayoutManager = CustomNpaGridLayoutManager(requireContext(), 3)
            menuAdapter = MenuEndlessAdapter(
                context = requireContext(),
                datas = mutableListOf(),
                onMenuClickListener = { menu ->
                    onMenuClicked(menu)
                },
                isCheckMenu = false
            )

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

    @SuppressLint("SetTextI18n")
    fun showKategoriOrder(kategoriOrder: List<KategoriOrder>) {
        generateCustomBottomSheetDialog(
            context = requireContext(),
            layoutRes = R.layout.layout_alert_option,
            isCancelable = true,
            isExpandMode = true
        ).apply {

            val categoryAdapter = KategoriOrderAdapter(
                context = requireContext(),
                datas = kategoriOrder,
                listener = object : KategoriOrderListener {
                    override fun onKategoriOrderSelected(data: KategoriOrder) {
                        dismiss()
                    }
                }
            )


            tvTitle.text = getString(R.string.pilih_kategori_order)

            rvOption.apply {
                layoutManager = GridLayoutManager(requireContext(), 3)
                adapter = categoryAdapter
            }
        }
    }

    override fun onResume() {
        super.onResume()
        menuAdapter = null
        preparingAdapter()
        observeMenus(firstPage)
        (activity as MainActivity).setupOrderButton()
    }

}