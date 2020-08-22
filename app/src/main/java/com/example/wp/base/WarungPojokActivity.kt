package com.example.wp.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

abstract class WarungPojokActivity: AppCompatActivity(), BaseInterface {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutView)

        onPreparation()
        onIntent()
        onView()
        onAction()
        onObserver()

    }

    abstract val layoutView: Int

    abstract fun onPreparation()

    abstract fun onIntent()

    abstract fun onView()

    abstract fun onAction()

    abstract fun onObserver()

    override fun setupToolbar(toolbar: Toolbar, title: String, isBack: Boolean) {
        setSupportActionBar(toolbar)

        supportActionBar?.let {
            it.title = title
            it.setDefaultDisplayHomeAsUpEnabled(isBack)
        }
    }
}

interface BaseInterface{
    fun setupToolbar(toolbar: Toolbar, title:String, isBack:Boolean)
}