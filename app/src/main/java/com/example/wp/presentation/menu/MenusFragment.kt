package com.example.wp.presentation.menu

import android.annotation.SuppressLint
import androidx.appcompat.view.menu.MenuAdapter
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
import com.example.wp.presentation.adapter.MenusAdapter
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

    var isOrderCategoryOpen = false

    private var menuAdapter: MenusAdapter? = null
    var selectedKategoriOrder: KategoriOrder? = null

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
                query?.let { menuViewModel.getMenus(it, selectedKategoriOrder?.id ?:0) }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()){
                    observeMenus(page = currentPage)
                }else{
                    menuViewModel.getMenus(newText, selectedKategoriOrder?.id ?:0)
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
//                    menuAdapter?.setLoadMoreProgress(true)
                }
                is Load.Fail -> {
                    showToast(it.error.localizedMessage)
                }
                is Load.Success -> {
                    val validMenu = it.data.filterNot {
                        it.menuPrice.isEmpty()
                    }
                    msvMenu.showContentView()
                    preparingAdapter(validMenu)
                    listMenu.addAll(validMenu)
                    isLoadMore = false
//                    menuAdapter?.setLoadMoreProgress(false)
                    totalPages = 0
//                    menuAdapter?.totalPage = totalPages
//                    menuAdapter?.notifyAddOrUpdateChanged(it.data)

                    if (validMenu.isEmpty()) {
                        if (isLoadMore) {
                            isLoadMore = false
//                            menuAdapter?.setLoadMoreProgress(false)
//                            menuAdapter?.removeScrollListener()
                        } else {
//                            menuAdapter?.datas?.clear()
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
//                    menuAdapter?.clear()
                    msvMenu.showLoadingView()
                }
                is Load.Fail -> {
                    showToast(it.error.localizedMessage)
                }
                is Load.Success -> {
                    val validMenu = it.data.filterNot {
                        it.menuPrice.isEmpty()
                    }
                    msvMenu.showContentView()
                    listMenu.addAll(validMenu)

                    menuAdapter?.updateDataMenu(validMenu)

                    if (it.data.isEmpty()) {
//                        menuAdapter?.datas?.clear()
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
//        menuAdapter?.clear()
//        menuAdapter?.notifyAddOrUpdateChanged(result)
    }

    interface OnMenuClickListener {
        fun onMenuClicked(menu: Menu)
        fun onCategoryOrderClicked(kategoriOrder: KategoriOrder?)
    }

    override fun onCategoryClicked(data: Category) {
        searchView.setQuery(emptyString(),false)
        searchView.clearFocus()
        listMenu.clear()
        menuViewModel.getMenus(data.id,  6, firstPage)
//        menuAdapter?.clear()
    }

    override fun onLoadMore() {
        isLoadMore = true
//        menuAdapter?.setLoadMoreProgress(true)
        currentPage += 1
//        menuAdapter?.page = currentPage
        observeMenus(currentPage)
    }

    private fun observeMenus(page: Int) {
        menuViewModel.getMenus(page = page)
    }

    private fun preparingAdapter(menus : List<Menu>){
            val gridLayoutManager = CustomNpaGridLayoutManager(requireContext(), 3)
            menuAdapter = MenusAdapter(
                context = requireContext(),
                data = menus,
                onMenuClickListener = { menu ->
                    onMenuClicked(menu)
                }
            )

            rvMenus.apply {
                layoutManager = gridLayoutManager
                adapter = menuAdapter
            }

    }

    @SuppressLint("SetTextI18n")
    fun showKategoriOrder(kategoriOrder: List<KategoriOrder>) {
        if (isOrderCategoryOpen.not()){
            generateCustomBottomSheetDialog(
                context = requireContext(),
                layoutRes = R.layout.layout_alert_option,
                isCancelable = false,
                isExpandMode = true
            ).apply {

                isOrderCategoryOpen = true

                val categoryAdapter = KategoriOrderAdapter(
                    context = requireContext(),
                    datas = kategoriOrder,
                    listener = object : KategoriOrderListener {
                        override fun onKategoriOrderSelected(data: KategoriOrder) {
                            onMenuClickListener?.onCategoryOrderClicked(data)
                            selectedKategoriOrder = data
                            menuViewModel.getMenus(categoryId = data.id, page = currentPage)
                            dismiss()
                            isOrderCategoryOpen  = false
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
    }

    override fun onResume() {
        super.onResume()
//        menuAdapter
//        preparingAdapter()
        observeMenus(firstPage)
        (activity as MainActivity).setupOrderButton()
        orderViewModel.getKategoriOrder()
    }

}