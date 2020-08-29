package com.example.wp.presentation.checkstock

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wp.R
import com.example.wp.data.api.model.response.DataItem
import com.example.wp.data.api.model.response.ResponseMenuWp
import com.example.wp.presentation.adapter.CheckStockAdapter
import kotlinx.android.synthetic.main.fragment_check_stock.*


class CheckStockFragment : Fragment(), CheckStockInterface.View {

    lateinit var presenter: CheckStockInterface.Presenter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check_stock, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = CheckStockPresenter(this)
        presenter.getDataStock()
        context?.let {
            presenter.instencePrefence(it)
        }


    }

    override fun showData(data: List<DataItem>) {
        Log.d("data", data.size.toString())
        rvCheckStok.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = CheckStockAdapter(context, data)
        }
    }

    override fun showEror(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

}