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
import com.satish.android.entertainmentminiapp.databinding.EntHorizontalViewBinding
import com.satish.android.entertainmentminiapp.model.Entertainment
import com.satish.android.entertainmentminiapp.ui.viewmodel.EnBinder


class EntHItemView(context: Context, val screenName: String) : BaseView(context) {

    override fun onCreateViewLayout(position: Int) = R.layout.ent_horizontal_view

    override fun onBindViewHolder(b: ViewDataBinding, any: Any?) {
        val binding = b as EntHorizontalViewBinding
        if (any is Entertainment) {
            binding.data = EnBinder(mContext, any, screenName)
            binding.executePendingBindings()
        }
    }
}