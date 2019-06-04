package com.object173.cfteventregestrator.feature.base.presentation.list

import android.view.MenuItem
import androidx.databinding.BindingAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

@BindingAdapter("onNavigationItemSelected")
fun setOnNavigationItemSelectedListener(
    view: BottomNavigationView, listener: (MenuItem) -> Boolean) {
    view.setOnNavigationItemSelectedListener(listener)
}

@BindingAdapter("menu")
fun BottomNavigationView.setMenu(id : Int?) {
    id?.let {this.inflateMenu(id)}
}

@BindingAdapter("selectedItem")
fun BottomNavigationView.setSelectedItem(id : Int?) {
    id?.let {this.selectedItemId = id}
}