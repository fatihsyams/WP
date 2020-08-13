package com.example.wp.view.pesanan

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wp.data.DataItem
import com.example.wp.R
import com.example.wp.adapter.MenusAdapter
import kotlinx.android.synthetic.main.fragment_pesanan.*

class PesananFragment : Fragment(),
    PesananInterface.View {

    lateinit var presenter: PesananPresenter

//    private val viewmodel: PesananViewModel by lazy {
//        ViewModelProviders.of(this).get(PesananViewModel::class.java)
//    }

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
        presenter.logicData()

//        viewmodel.getInfoMenus()
//
//        viewmodel.pesananLoad.observe(viewLifecycleOwner, Observer {
//            when (it) {
//                is Load.Success -> {
//                    Toast.makeText(context, "$it.data.id", Toast.LENGTH_LONG).show()
//
//                    rvMenus.layoutManager = LinearLayoutManager(context)
//                    rvMenus.adapter = MenusAdapter(context!!, it.data.data!!)
//
//                }
//                is Load.Fail -> {
//                    Toast.makeText(context, "Fail + ${it.error}", Toast.LENGTH_LONG).show()
//                }
//            }
//        })


//        searchView.setOnQueryTextListener(object :
//            androidx.appcompat.widget.SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                adapters.filter.filter(newText)
//                return false
//            }
//
//        })
//
//
    }

    override fun showData(data: List<DataItem>) {
        Log.d("data", "${data.size}")
        rvMenus.apply {
            adapter = MenusAdapter(context, data)
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun alertSuccess(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    override fun alertFailed(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }


}