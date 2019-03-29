package com.satish.android.entertainmentminiapp.ui.views

import android.content.Context
import android.databinding.ViewDataBinding
import com.satish.android.entertainmentminiapp.R
import com.satish.android.entertainmentminiapp.databinding.HomeEmptyPageBinding

class HomeEmptyView(context: Context, val title: String = "") : BaseView(context) {

    override fun onCreateViewLayout(position: Int): Int {
        return R.layout.home_empty_page
    }

    override fun onBindViewHolder(viewDataBinding: ViewDataBinding, any: Any?) {
        if(title.isNullOrBlank()) return
        val binding = viewDataBinding as HomeEmptyPageBinding
        binding.emtpyHomeTv.text = title
    }

}