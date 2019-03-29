package com.satish.android.entertainmentminiapp.customrecyclercontrol.adapter

/**
 *
 * @author satish
 * 27/03/2019
 *
 * **/

import android.databinding.ViewDataBinding
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.ViewGroup
import com.satish.android.entertainmentminiapp.R
import com.satish.android.entertainmentminiapp.customrecyclercontrol.helper.MultiListInterfaces
import com.satish.android.entertainmentminiapp.customrecyclercontrol.helpers.RecycleAdapterParamInfo
import com.satish.android.entertainmentminiapp.customrecyclercontrol.helpers.RecycleAdapterParams
import com.satish.android.entertainmentminiapp.customrecyclercontrol.helpers.RecycleAdapterParamsCallback


open class SingleItemRecycleAdapter : RecyclerView.Adapter<BaseHolder<ViewDataBinding>>() {

	private var mArrListAdpaterParams: ArrayList<RecycleAdapterParams>? = null
	private val mArrListSingleItemAdpaterParams: MutableList<RecycleAdapterParams> = ArrayList()
	private var mAdapterParamInfo: RecycleAdapterParamInfo? = null
	private var isScrolling = false
	private var recycleViewHolderListnerSparseArray: SparseArray<MultiListInterfaces.OnRecycleViewHolderListner> =
		SparseArray()

	fun setAdapterParams(arrListAdpaterParams: ArrayList<RecycleAdapterParams>?) {
		this.mArrListAdpaterParams = arrListAdpaterParams
		updateRecyclerAdaptarParams()
	}

	fun notifyDatahasChanged() {
		updateRecyclerAdaptarParams()
	}

	private fun updateRecyclerAdaptarParams() {
		if (mArrListAdpaterParams?.isNotEmpty() == true) {
			mAdapterParamInfo = RecycleAdapterParamInfo(mArrListAdpaterParams!!)
			val mArrListSingleItemAdpaterParams = mAdapterParamInfo!!.getlistForSingleItemAdapter()
			val diffCallback =
				RecycleAdapterParamsCallback(this@SingleItemRecycleAdapter.mArrListSingleItemAdpaterParams, mArrListSingleItemAdpaterParams)
			val diffResult = DiffUtil.calculateDiff(diffCallback)
			this@SingleItemRecycleAdapter.mArrListSingleItemAdpaterParams.clear()
			this@SingleItemRecycleAdapter.mArrListSingleItemAdpaterParams.addAll(mArrListSingleItemAdpaterParams)
			recycleViewHolderListnerSparseArray = getSparseArrayOfViewClassName()
			diffResult.dispatchUpdatesTo(this@SingleItemRecycleAdapter)
		}
	}

	fun getMaxColumCount() = mAdapterParamInfo?.maxColoumCount ?: 1

	private fun getSparseArrayOfViewClassName(): SparseArray<MultiListInterfaces.OnRecycleViewHolderListner> {
		val listenerSparseArray = SparseArray<MultiListInterfaces.OnRecycleViewHolderListner>()
		mArrListSingleItemAdpaterParams.forEach { adapter ->
			adapter.let {
				val viewClassName = adapter.viewClassName
				listenerSparseArray.remove(viewClassName.javaClass.hashCode())
				listenerSparseArray.append(viewClassName.javaClass.hashCode(), viewClassName)
			}
		}
		return listenerSparseArray
	}

	override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BaseHolder<*> {
		return recycleViewHolderListnerSparseArray.get(viewType)
			.onCreateHolder(viewGroup, viewType)
	}

	override fun onBindViewHolder(viewHolder: BaseHolder<*>, position: Int) {
		recycleViewHolderListnerSparseArray.get(getItemViewType(viewHolder.adapterPosition))
			?.onBindViewHolder(viewHolder.binding(), mArrListSingleItemAdpaterParams[viewHolder.adapterPosition].dataObject, isScrolling)
	}

	override fun getItemViewType(position: Int) =
		mArrListSingleItemAdpaterParams[position].viewClassName.javaClass.hashCode()

	override fun getItemCount() = mArrListSingleItemAdpaterParams.size

	fun getItem(position: Int) = mArrListSingleItemAdpaterParams[position]

	override fun getItemId(position: Int) = position.toLong()

	fun setScrolling(isScrolling: Boolean) {
		this.isScrolling = isScrolling
	}

	override fun onViewRecycled(holder: BaseHolder<*>) {
		try {
			val tag = holder.itemView.getTag(R.string.key_view_adapter_position) as Int?
			tag?.let {
				recycleViewHolderListnerSparseArray.get(getItemViewType(tag))
					.onViewRecycled(holder)
			}
		} catch (e: Exception) {
			e.printStackTrace()
		}

	}

	/**
	 * It is used to set maximum column count, use it before setting adapter,
	 * because during notify we are not checking any change in maxColumnCount
	 * so whatever maximum value we are giving to any adapterParam before setting adapter will
	 * be maximum column count, by default it is set to 1
	 *
	 * @param columnCount maximum column count in the adapter
	 */
	fun setMaxColumnCount(columnCount: Int) {
		mAdapterParamInfo!!.maxColoumCount = columnCount
	}
}