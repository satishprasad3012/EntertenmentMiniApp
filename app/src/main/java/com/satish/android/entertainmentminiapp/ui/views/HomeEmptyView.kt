package com.satish.android.entertainmentminiapp.ui.views

import android.content.Context
import android.databinding.ViewDataBinding
import com.satish.android.entertainmentminiapp.R

class HomeEmptyView (context: Context) : BaseView(context) {

    override fun onCreateViewLayout(position: Int): Int {
        return R.layout.home_empty_page
    }

    override fun onBindViewHolder(viewDataBinding: ViewDataBinding, any: Any?) {
    }

}