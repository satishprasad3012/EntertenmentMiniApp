package com.satish.android.entertainmentminiapp.customrecyclercontrol.helpers

/**
 *
 * @author satish
 * 27/03/2019
 *
 * **/

import android.support.v7.util.DiffUtil

class RecycleAdapterParamsCallback(oldList: List<RecycleAdapterParams?>?, newList: List<RecycleAdapterParams?>?) :
	DiffUtil.Callback() {
	private val mOldList: List<RecycleAdapterParams?>? = oldList
	private val mNewList: List<RecycleAdapterParams?>? = newList

	override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
		mOldList?.get(oldItemPosition)?.dataObject?.equals(mNewList?.get(newItemPosition)?.dataObject)
				?: false

	override fun getOldListSize() = mOldList?.size ?: 0

	override fun getNewListSize() = mNewList?.size ?: 0

	override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
		mOldList?.get(oldItemPosition)?.dataObject?.hashCode() == mNewList?.get(newItemPosition)?.dataObject?.hashCode()
				&& mOldList?.get(oldItemPosition)?.viewClassName == mNewList?.get(newItemPosition)?.viewClassName
}
