package com.satish.android.entertainmentminiapp.ui.activity

/**
 *
 * @author satish
 * 28/03/2019
 *
 * **/


import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.TextView
import com.satish.android.entertainmentminiapp.R

abstract class BaseActivity: AppCompatActivity(), Toolbar.OnMenuItemClickListener {

    var toolbar: Toolbar? = null
    private var toolbarHeading: TextView? = null


    override fun setContentView(@LayoutRes layoutResID: Int) {
        super.setContentView(layoutResID)
        initUI()
    }

    private fun initUI() {
        toolbar = findViewById(R.id.toolbar)
        toolbarHeading = findViewById(R.id.toolbar_heading)
        toolbar?.let { toolbar ->
            //Clear toolbar icons
            toolbar.menu.clear()
            toolbar.setOnMenuItemClickListener(this)
            toolbar.setNavigationOnClickListener { onBackPressed() }
        }
    }

    override fun onMenuItemClick(menuItem: MenuItem?): Boolean {
        when (menuItem?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(menuItem)
    }

    fun setToolbarTitle(title: String?) {
        toolbar?.let { toolbar ->
            toolbar.title = title
        }
    }

    protected fun setNavigationIcon(icon: Int) {
        toolbar?.setNavigationIcon(icon)
    }

    fun setToolbarHeading(heading:String){
        toolbarHeading?.text = heading
    }

    override fun onStart() {
        super.onStart()
        //TODO; need to start listening(eg: event tracking, firebase start etc)
    }

    override fun onStop() {
        super.onStop()
        //TODO;  need to stop listening(eg: event tracking, firebase listener etc)
    }

    protected abstract val screenName: String // used for screenName tracking in analytics
}