package com.example.wp.presentation.menu.createmenu

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.esafirm.imagepicker.features.ImagePicker
import com.example.wp.R
import com.example.wp.domain.material.Material
import com.example.wp.domain.material.MaterialMenu
import com.example.wp.domain.menu.Category
import com.example.wp.domain.menu.Menu
import com.example.wp.presentation.adapter.CategoryAdapter
import com.example.wp.presentation.listener.CreateMenuListener
import com.example.wp.presentation.listener.MenuCategoryListener
import com.example.wp.presentation.listener.MenuListener
import com.example.wp.presentation.menu.MenusContainerFragment
import com.example.wp.presentation.viewmodel.MaterialViewModel
import com.example.wp.presentation.viewmodel.MenuViewModel
import com.example.wp.utils.*
import com.example.wp.utils.constants.AppConstants
import kotlinx.android.synthetic.main.fragment_create_menu.*
import kotlinx.android.synthetic.main.layout_alert_option.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File


class CreateMenuFragment : Fragment(), CreateMenuInterface.View, MenuListener {

    lateinit var presenter: CreateMenuPresenter

    private var selectedImageFile: File? = null
    private var menus: Menu? = null

    private var selectedCategory: Category? = null
    private var selectedMaterial: Material? = null

    private val menuViewModel: MenuViewModel by viewModel()
    private val materialViewModel: MaterialViewModel by viewModel()

    var onMenuCreateListener: (() -> Unit)? = null

    companion object {
        fun newInstance(menu: Menu) =
            CreateMenuFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(AppConstants.KEY_MENU, menu)
                }
            }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpImage()

        setBackgroundEditText()

        onIntent()

        showMenuDetail()

        onObserve()

        (parentFragment as MenusContainerFragment).onMenuSelectListener = this


        presenter = CreateMenuPresenter(this)
        context?.let { presenter.instencePrefence(it) }

        btnCreateMenu.setOnClickListener {
            if (isFormComplete(
                    views = listOf(
                        tilName,
                        tilDescription,
                        tilPrice,
                        tilPriceGofood,
                        tilPriceGrabfood,
                        tilStock,
                        tilCategoryId,
                        tilMaterial
                    )
                )
            ) {
                selectedImageFile?.let { imageFile ->
                    presenter.logicInputMenus(
                        name = edtNameCreateMenu.text.toString(),
                        description = edtDescriptionCreateMenu.text.toString(),
                        price = edtPriceCreateMenu.text.toString(),
                        category = selectedCategory?.id.toString(),
                        stock = edtStockCreateMenu.text.toString(),
                        image = imageFile,
                        grabFoodPrice = edtPriceGrabfood.text.toString(),
                        goFoodPrice = edtPriceGofood.text.toString()
                    )
                }
            }else{
                showToast(getString(R.string.message_complete_form_first))
            }
        }

        edtCategoryMenuIdCreateMenu.setOnClickListener {
            menuViewModel.getCategories()
        }

        edtMaterial.setOnClickListener {
            materialViewModel.getMaterials()
        }

    }

    private fun onIntent() {
        menus = (parentFragment as MenusContainerFragment).selectedMenu
    }

    private fun onObserve() {
        menuViewModel.categoriesLoad.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Load.Fail -> {
                    showToast(it.error.localizedMessage ?: "Error tidak diketahui")
                }
                is Load.Success -> {
                    showCategoryOptions(it.data)
                }
            }
        })

        materialViewModel.getMaterialLoad.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Load.Fail -> {
                    showToast(it.error.localizedMessage ?: "Error tidak diketahui")
                }
                is Load.Success -> {
                    showMaterialOptions(it.data)
                }
            }
        })

        materialViewModel.materiaMenulLoad.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Load.Fail -> {
                    showToast(it.error.localizedMessage ?: "Error tidak diketahui")
                }
                is Load.Success -> {
                    showToast("Berhasil menambahkan menu")
                    clearForm(
                        views = listOf(
                            edtNameCreateMenu,
                            edtPriceCreateMenu,
                            edtPriceGofood,
                            edtPriceGrabfood,
                            edtMaterial,
                            edtStockCreateMenu,
                            edtCategoryMenuIdCreateMenu,
                            edtDescriptionCreateMenu
                        )
                    )
                    ImgCreateMenu.setImageResource(R.color.colorPrimary)
                    imgIcon.visible()

                    onMenuCreateListener?.invoke()
                }
            }
        })

    }

    private fun setBackgroundEditText() {
        edtCategoryMenuIdCreateMenu.background.setColorFilter(
            resources.getColor(R.color.colorBase),
            PorterDuff.Mode.SRC_ATOP
        )

        edtStockCreateMenu.background.setColorFilter(
            resources.getColor(R.color.colorBase),
            PorterDuff.Mode.SRC_ATOP
        )

        edtPriceCreateMenu.background.setColorFilter(
            resources.getColor(R.color.colorBase),
            PorterDuff.Mode.SRC_ATOP
        )

        edtDescriptionCreateMenu.background.setColorFilter(
            resources.getColor(R.color.colorBase),
            PorterDuff.Mode.SRC_ATOP
        )

        edtNameCreateMenu.background.setColorFilter(
            resources.getColor(R.color.colorBase),
            PorterDuff.Mode.SRC_ATOP
        )
    }

    private fun setUpImage() {
        ImgCreateMenu.setOnClickListener {
            ImagePicker.create(this) // Activity or Fragment
                .folderMode(false)
                .start()
        }
    }

    private fun showMenuDetail() {
        menus?.apply {
            if (images.isNotEmpty()) {
                Glide.with(requireContext())
                    .load(images)
                    .into(ImgCreateMenu)
                imgIcon.gone()
            }

            edtNameCreateMenu.setText(name)
            edtDescriptionCreateMenu.setText((description))
            edtPriceCreateMenu.setText(price.toString())
            edtStockCreateMenu.setText(stock.toString())
            edtCategoryMenuIdCreateMenu.setText(category)
            edtPriceGofood.setText(goFoodPrice.toString())
            edtPriceGrabfood.setText(grabFoodPrice.toString())
            btnCreateMenu.text = "Update"

            btnCreateMenu.setOnClickListener {
                if (isFormComplete(
                        views = listOf(
                            tilName,
                            tilDescription,
                            tilPrice,
                            tilPriceGofood,
                            tilPriceGrabfood,
                            tilStock,
                            tilCategoryId,
                            tilMaterial
                        )
                    )
                ){
                    selectedImageFile?.let { imageFile ->
                        presenter.updateMenus(
                            id,
                            name = edtNameCreateMenu.text.toString(),
                            description = edtDescriptionCreateMenu.text.toString(),
                            price = edtPriceCreateMenu.text.toString(),
                            category = selectedCategory?.id.toString(),
                            stock = edtStockCreateMenu.text.toString(),
                            image = imageFile
                        )
                    }
                }
            }
        }
    }

    private fun showCategoryOptions(categories: List<Category>) {
        generateCustomBottomSheetDialog(
            context = requireContext(),
            layoutRes = R.layout.layout_alert_option,
            isCancelable = true,
            isExpandMode = true
        ).apply {

            val categoryAdapter = CategoryAdapter(
                context = requireContext(),
                datas = categories,
                menuCategoryListener = object : MenuCategoryListener {
                    override fun onCategoryClicked(data: Category) {
                        selectedCategory = data
                        getSelectedCategory()
                        dismiss()
                    }
                }
            )


            tvTitle.text = getString(R.string.title_select_category)

            rvOption.apply {
                layoutManager = GridLayoutManager(requireContext(), 3)
                adapter = categoryAdapter
            }
        }
    }

    private fun showMaterialOptions(materials: List<Material>) {
        generateCustomBottomSheetDialog(
            context = requireContext(),
            layoutRes = R.layout.layout_alert_option,
            isCancelable = true,
            isExpandMode = true
        ).apply {

            val categoryAdapter = CategoryAdapter(
                context = requireContext(),
                datas = materials.map { it.toCategoryOption() },
                menuCategoryListener = object : MenuCategoryListener {
                    override fun onCategoryClicked(data: Category) {
                        selectedMaterial = data.toMaterial()
                        getSelectedMaterial()
                        dismiss()
                    }
                }
            )


            tvTitle.text = getString(R.string.title_select_material)

            rvOption.apply {
                layoutManager = GridLayoutManager(requireContext(), 3)
                adapter = categoryAdapter
            }
        }
    }


    fun getSelectedCategory() {
        edtCategoryMenuIdCreateMenu.setText(selectedCategory?.name.orEmpty())
    }

    fun getSelectedMaterial() {
        edtMaterial.setText(selectedMaterial?.material.orEmpty())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            val image = ImagePicker.getFirstImageOrNull(data)
            val path: String = image.path
            selectedImageFile = File(path)

            context?.let {
                Glide.with(it)
                    .load(path)
                    .into(ImgCreateMenu)
            }

            imgIcon.gone()
        }

        super.onActivityResult(requestCode, resultCode, data)

    }

    override fun showLoading(isLoading: Boolean) {
        pbCreateMenu.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun showAlertSuccess(msg: String, menu: Menu?) {
        materialViewModel.postMaterialMenu(
            MaterialMenu(
                materialId = selectedMaterial?.id ?: 0,
                menuId = menu?.id ?: 0,
                stockRequired = edtStockCreateMenu.text.toString().trim().toDouble()
            )
        )
    }

    override fun showAlertFailed(msg: String) {
        showToast(msg)
    }

    override fun onSelectMenu(menu: Menu) {
        menus = menu
        showMenuDetail()
    }

}