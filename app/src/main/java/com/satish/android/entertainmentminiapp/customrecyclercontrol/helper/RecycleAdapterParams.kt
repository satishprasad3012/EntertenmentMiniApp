package com.satish.android.entertainmentminiapp.customrecyclercontrol.helpers

/**
 *
 * @author satish
 * 27/03/2019
 *
 * **/

import com.satish.android.entertainmentminiapp.customrecyclercontrol.helper.MultiListInterfaces


class RecycleAdapterParams(var dataObject: Any?, val viewClassName: MultiListInterfaces.OnRecycleViewHolderListner) {
	var isFirstLast: Boolean? = false
		private set

	/**
	 * If view is Horizontal Scroll View, Pager or any collection view
	 */
	var compositeViewStatus: Boolean = false
		private set //Default value
	var numOfColumn = 1 //Default value
	/*************************************************
	 * Below Methods to be used for sticky header implementation
	 */

	var onMultiListItemScrollPositionListner: MultiListInterfaces.OnRecycleViewScrollPositionListner? =
		null
		private set
	/**
	 * TO be used for Sticky header. For deciding if null to be send for Non header objects.
	 */
	var stickyHeaderObject: Any? = null

	fun isCompositeView(isCompositeView: Boolean) {
		this.compositeViewStatus = isCompositeView
	}

	fun setOnMultiListItemAtFirstPositionListner(onMultiListItemScrollPositionListner: MultiListInterfaces.OnRecycleViewScrollPositionListner?) {
		this.onMultiListItemScrollPositionListner = onMultiListItemScrollPositionListner
	}

	/**
	 * Will be called with dataobject provided
	 *
	 * @param onMultiListItemScrollPositionListner
	 * @param stickyHeaderObject
	 */
	fun setOnMultiListItemAtFirstPositionListner(onMultiListItemScrollPositionListner: MultiListInterfaces.OnRecycleViewScrollPositionListner, stickyHeaderObject: Any) {
		this.onMultiListItemScrollPositionListner = onMultiListItemScrollPositionListner
		this.stickyHeaderObject = stickyHeaderObject
	}

	/**
	 * To be used for identify when to start/stop scroll callbacks
	 *
	 * @param isFirstVisibleItem
	 */
	fun setItemAsFirstVisible(isFirstVisibleItem: Boolean?) {
		this.isFirstLast = isFirstVisibleItem
	}

}