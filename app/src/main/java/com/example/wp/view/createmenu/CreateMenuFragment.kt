package com.example.wp.view.createmenu

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.net.toFile
import com.example.wp.R
import kotlinx.android.synthetic.main.fragment_base_input_menu.*
import kotlinx.android.synthetic.main.fragment_create_menu.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.File


class CreateMenuFragment : Fragment(), CreateMenuInterface.View {

    lateinit var presenter: CreateMenuPresenter
    val PICK_IMAGE = 1
    var uri: Uri? = null
    val RESULT_OKE = -1
    lateinit var file: File


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

        presenter = CreateMenuPresenter(this)
        context?.let { presenter.instencePrefence(it) }
        btnCreateMenu.setOnClickListener {
            var requestFile: RequestBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), file)


            var nama: RequestBody = RequestBody.create(
                MediaType.parse("multipart/form-data"),
                edtNameCreateMenu.text.toString()
            )
            var description: RequestBody = RequestBody.create(
                MediaType.parse("multipart/form-data"),
                edtDescriptionCreateMenu.text.toString()
            )
            var Price: RequestBody = RequestBody.create(
                MediaType.parse("multipart/form-data"),
                edtPriceCreateMenu.text.toString()
            )
            var stock: RequestBody = RequestBody.create(
                MediaType.parse("multipart/form-data"),
                edtStockCreateMenu.text.toString()
            )
            var category_menu_id: RequestBody = RequestBody.create(
                MediaType.parse("multipart/form-data"),
                edtCategoryMenuIdCreateMenu.text.toString()
            )
            var body: MultipartBody.Part =
                MultipartBody.Part.createFormData("image", file.path, requestFile)

            presenter.logicInputMenus(
                nama, description, Price, category_menu_id, stock, body
            )

        }

    }

    private fun setUpImage() {
        ImgCreateMenu.setOnClickListener {
            Toast.makeText(context, "TEST", Toast.LENGTH_LONG).show()
            val intentGallery = Intent()
            intentGallery.apply {
                type = "image/*"
                action = Intent.ACTION_GET_CONTENT
                startActivityForResult(
                    Intent.createChooser(intentGallery, "Pilih Photo"),
                    PICK_IMAGE
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        var resolver = activity!!.contentResolver

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OKE) {
            try {
//                var extras: Bundle? = data?.extras
//                var bitmap: Bitmap = extras?.get("data") as Bitmap
//
//                uri = data?.data
//
//
//                val bitmap: Bitmap =
////                    MediaStore.Images.Media.getBitmap(resolver, uri)
//                var filesDir: File = activity?.filesDir!!
//                var imageFile = File(filesDir, "image" + ".jpg")
//
//                file = imageFile
//
//                imgInputMenus.setImageBitmap(bitmap)
            } catch (e: HttpException) {
                Toast.makeText(context, e.message(), Toast.LENGTH_LONG).show()
            }
        }
    }


    override fun showAlertSuccess(msg: String) {

    }

    override fun showAlertFailed(msg: String) {

    }


}