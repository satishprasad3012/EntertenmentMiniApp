package com.satish.android.entertainmentminiapp.customrecyclercontrol.helpers

/**
 *
 * @author satish
 * 27/03/2019
 *
 * **/
import com.satish.android.entertainmentminiapp.customrecyclercontrol.helper.MultiListInterfaces

class RecycleAdapterParamInfo(private val mArrListAdpaterParams: ArrayList<RecycleAdapterParams>) {
	var maxColoumCount = 1

	fun getlistForSingleItemAdapter(): ArrayList<RecycleAdapterParams> {
		val listSingleItemParam = ArrayList<RecycleAdapterParams>()
		mArrListAdpaterParams.filterNotNull()
			.forEach { adapterParam ->
				if (adapterParam.dataObject is List<*>) {
					//Arraylist or any list
					val listDataObject = adapterParam.dataObject as List<*>
					if (!adapterParam.compositeViewStatus) {
						listDataObject.forEach {
							listSingleItemParam.add(recycleAdapterParamsItem(adapterParam, RecycleAdapterParams(it, adapterParam.viewClassName)))
						}
					} else {
						//Its composite view HScrollView/Pager etc
						listSingleItemParam.add(recycleAdapterParamsItem(adapterParam, RecycleAdapterParams(listDataObject, adapterParam.viewClassName)))
					}
				} else {
					// Single object for example string
					listSingleItemParam.add(recycleAdapterParamsItem(adapterParam, RecycleAdapterParams(adapterParam.dataObject, adapterParam.viewClassName)))
				}
			}
		return listSingleItemParam
	}

	private fun recycleAdapterParamsItem(adapterParam: RecycleAdapterParams, newAdapterParam: RecycleAdapterParams): RecycleAdapterParams {
		var onMultiListScrollPositionListner: MultiListInterfaces.OnRecycleViewScrollPositionListner? =
			null
		var stickyHeaderObject: Any? = null
		if (adapterParam.onMultiListItemScrollPositionListner != null) {
			onMultiListScrollPositionListner = adapterParam.onMultiListItemScrollPositionListner
			stickyHeaderObject = adapterParam.stickyHeaderObject ?: adapterParam.dataObject
		}
		newAdapterParam.setOnMultiListItemAtFirstPositionListner(onMultiListScrollPositionListner)
		newAdapterParam.stickyHeaderObject = stickyHeaderObject
		if (adapterParam.numOfColumn > maxColoumCount) maxColoumCount = adapterParam.numOfColumn
		newAdapterParam.numOfColumn = adapterParam.numOfColumn
		newAdapterParam.setItemAsFirstVisible(adapterParam.isFirstLast)
		return newAdapterParam
	}
}
