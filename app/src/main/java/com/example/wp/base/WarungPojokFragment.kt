package com.bidikan.baseapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

abstract class WarungPojokFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return  inflater.inflate(layoutView, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

}