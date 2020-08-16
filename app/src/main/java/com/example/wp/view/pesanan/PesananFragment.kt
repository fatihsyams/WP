package com.example.wp.view.pesanan

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wp.data.DataItem
import com.example.wp.R
import com.example.wp.adapter.MenusAdapter
import kotlinx.android.synthetic.main.fragment_pesanan.*

class PesananFragment : Fragment(),
    PesananInterface.View {

    lateinit var presenter: PesananPresenter

    private val menuAdapter: MenusAdapter by lazy {
        MenusAdapter(requireContext(), listOf())
    }

    var listMenu = listOf<DataItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pesanan, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = PesananPresenter(this)
        context?.let { presenter.initSession(it) }
        presenter.logicData()



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

//
    }

    override fun showData(data: List<DataItem>) {
        Log.d("data", "${data.size}")
        menuAdapter.addDataMenus(data)
        listMenu = data
        rvMenus.apply {
            adapter = menuAdapter
            layoutManager = GridLayoutManager(context, 2)
        }
    }

    override fun alertSuccess(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    override fun alertFailed(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }


    fun filter(query: String) {
        val result = listMenu.filter {
            it.name?.contains(query, true)!!.or(false)
        }
        menuAdapter.updateDataMenu(result)
    }

}