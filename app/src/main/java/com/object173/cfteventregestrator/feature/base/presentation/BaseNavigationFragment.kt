package com.object173.cfteventregestrator.feature.base.presentation

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.object173.cfteventregestrator.R
import dagger.android.support.DaggerFragment

open class BaseNavigationFragment : DaggerFragment() {

    private val navigator by lazy {
        Navigation.findNavController(activity!!, R.id.nav_host_fragment)
    }

    private val actionBar by lazy {
        (activity as AppCompatActivity).supportActionBar
    }

    protected fun goToFragment(id : Int, bundle: Bundle) {
        navigator.navigate(id, bundle)
    }

    protected open fun onHomeClick() : Boolean {
        activity?.onBackPressed()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            android.R.id.home -> onHomeClick()
            else -> super.onOptionsItemSelected(item)
        }
    }

    protected fun setTitle(title : String?) {
        if(title != null) {
            actionBar?.title = title
        }
        else {
            actionBar?.title = getString(R.string.app_name)
        }
    }

    protected open fun getTitle() = getString(R.string.app_name)
    protected open fun getSubtitle() = ""

    protected fun setSubtitle(subtitle : String?) {
        actionBar?.subtitle = subtitle
    }

    protected fun setHomeButton(id : Int?, enabled: Boolean) {
        id?.let {
            actionBar?.setHomeAsUpIndicator(id)
        }
        actionBar?.setDisplayHomeAsUpEnabled(enabled)
    }

    protected open fun handleError(messageId : Int) {
        view?.let {
            Snackbar.make(it, messageId, Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onStart() {
        super.onStart()
        setHomeButton(0, false)
        updateActionBar()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    protected open fun updateActionBar() {
        setTitle(getTitle())
        setSubtitle(getSubtitle())
    }
}