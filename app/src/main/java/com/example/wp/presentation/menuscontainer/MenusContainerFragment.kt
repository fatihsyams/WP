package com.example.wp.presentation.menuscontainer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wp.R
import com.example.wp.domain.menu.Menu
import com.example.wp.presentation.adapter.TabAdapter
import com.example.wp.presentation.checkmenu.CheckMenuFragment
import com.example.wp.presentation.createmenu.CreateMenuFragment
import com.example.wp.presentation.listener.MenuListener
import com.example.wp.utils.loadFragment
import kotlinx.android.synthetic.main.fragment_base_input_menu.*
import kotlinx.android.synthetic.main.fragment_menus_container.*

class MenusContainerFragment : Fragment(), CheckMenuFragment.OnCheckMenuClickListener {

    var selectedMenu: Menu? = null
    var onMenuSelectListener: MenuListener? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menus_container, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val createMenuFragment = CreateMenuFragment()
        val checkMenuFragment = CheckMenuFragment()
        checkMenuFragment.onCheckMenuClickListener = this


        viewpager_main.adapter =
            TabAdapter(childFragmentManager, listOf(createMenuFragment, checkMenuFragment))
        tabs_main.setupWithViewPager(viewpager_main)

    }

    override fun onCheckMenuClicked(menu: Menu) {
        val updateMenuFragment = CreateMenuFragment.newInstance(menu)
        selectedMenu = menu
        viewpager_main.currentItem = 0
        onMenuSelectListener?.onSelectMenu(menu)
    }
}