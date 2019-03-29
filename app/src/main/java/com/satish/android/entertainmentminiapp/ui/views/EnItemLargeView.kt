package com.satish.android.entertainmentminiapp.ui.views

/**
 *
 * @author satish
 * 28/03/2019
 *
 * **/

import android.content.Context
import android.databinding.ViewDataBinding
import com.satish.android.entertainmentminiapp.R
import com.satish.android.entertainmentminiapp.databinding.EntItemLargeBinding
import com.satish.android.entertainmentminiapp.model.Entertainment
import com.satish.android.entertainmentminiapp.ui.viewmodel.EnBinder

class EnItemLargeView(context: Context, val screenName: String) : BaseView(context) {

    override fun onCreateViewLayout(position: Int) = R.layout.ent_item_large

    override fun onBindViewHolder(viewDataBinding: ViewDataBinding, any: Any?) {
        if (any is Entertainment) {
            val binding = viewDataBinding as EntItemLargeBinding
            binding.data = EnBinder(mContext, any, screenName)
            binding.executePendingBindings()
        }
    }
}