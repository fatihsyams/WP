package com.example.wp.presentation.createmenu

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.esafirm.imagepicker.features.ImagePicker
import com.example.wp.R
import com.example.wp.domain.menu.Menu
import com.example.wp.presentation.listener.MenuListener
import com.example.wp.presentation.menuscontainer.MenusContainerFragment
import com.example.wp.utils.constants.AppConstants
import com.example.wp.utils.showToast
import kotlinx.android.synthetic.main.fragment_create_menu.*
import java.io.File


class CreateMenuFragment : Fragment(), CreateMenuInterface.View, MenuListener {

    lateinit var presenter: CreateMenuPresenter

    private var selectedImageFile: File? = null
    private var menus: Menu? = null


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

        (parentFragment as MenusContainerFragment).onMenuSelectListener = this


        presenter = CreateMenuPresenter(this)
        context?.let { presenter.instencePrefence(it) }
        btnCreateMenu.setOnClickListener {
            selectedImageFile?.let { imageFile ->
                presenter.logicInputMenus(
                    name = edtNameCreateMenu.text.toString(),
                    description = edtDescriptionCreateMenu.text.toString(),
                    price = edtPriceCreateMenu.text.toString(),
                    category = edtCategoryMenuIdCreateMenu.text.toString(),
                    stock = edtStockCreateMenu.text.toString(),
                    image = imageFile
                )
            }

        }

    }

    private fun onIntent() {
        menus = (parentFragment as MenusContainerFragment).selectedMenu
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
            if (!images.isNullOrEmpty()) {
                Glide.with(requireContext())
                    .load(images)
                    .into(ImgCreateMenu)
            }

            edtNameCreateMenu.setText(name)
            edtDescriptionCreateMenu.setText((description))
            edtPriceCreateMenu.setText(price.toString())
            edtStockCreateMenu.setText(stock.toString())
            edtCategoryMenuIdCreateMenu.setText(category)
            btnCreateMenu.text = "Update"

            btnCreateMenu.setOnClickListener {
                selectedImageFile?.let { imageFile ->
                    presenter.updateMenus(
                        id,
                        name = edtNameCreateMenu.text.toString(),
                        description = edtDescriptionCreateMenu.text.toString(),
                        price = edtPriceCreateMenu.text.toString(),
                        category = edtCategoryMenuIdCreateMenu.text.toString(),
                        stock = edtStockCreateMenu.text.toString(),
                        image = imageFile
                    )
                }
            }
        }
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
        }

        super.onActivityResult(requestCode, resultCode, data)

    }


    override fun showAlertSuccess(msg: String) {
        showToast(msg)
    }

    override fun showAlertFailed(msg: String) {
        showToast(msg)
    }

    override fun onSelectMenu(menu: Menu) {
        menus = menu
        showMenuDetail()
    }


}