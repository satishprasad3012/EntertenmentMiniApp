package com.satish.android.entertainmentminiapp.customrecyclercontrol.adapter

/**
 *
 * @author satish
 * 27/03/2019
 *
 * **/

import android.support.v7.widget.RecyclerView
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.View


class BaseHolder<out T : ViewDataBinding>(itemView: View) : RecyclerView.ViewHolder(itemView) {
	private val binding: T = DataBindingUtil.bind(itemView)!!

	fun binding(): T {
		return binding
	}
}