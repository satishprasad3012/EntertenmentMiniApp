package com.satish.android.entertainmentminiapp.ui.views

/**
 *
 * @author satish
 * 28/03/2019
 *
 * **/

import android.content.Context
import android.databinding.ViewDataBinding
import android.support.constraint.Constraints
import android.text.TextUtils
import android.view.View
import com.satish.android.entertainmentminiapp.R
import com.satish.android.entertainmentminiapp.customrecyclercontrol.RecycleHMultiItemView
import com.satish.android.entertainmentminiapp.customrecyclercontrol.adapter.SingleItemRecycleAdapter
import com.satish.android.entertainmentminiapp.customrecyclercontrol.helpers.RecycleAdapterParams
import com.satish.android.entertainmentminiapp.databinding.EntLayoutHeadingHorizontalBinding
import com.satish.android.entertainmentminiapp.model.Entertainment
import com.satish.android.entertainmentminiapp.utility.isArrayEmpty

class BookmarkHView(context: Context, private val any: ArrayList<Entertainment>,
                    private val heading: String = "",
                   val screenName: String) :
    BaseView(context) {
    private val mMultiItemRowAdapter = SingleItemRecycleAdapter()

    override fun onCreateViewLayout(position: Int) = R.layout.ent_layout_heading_horizontal

    override fun onBindViewHolder(viewDataBinding: ViewDataBinding, any: Any?) {
        val binding = viewDataBinding as EntLayoutHeadingHorizontalBinding
        val data: ArrayList<Entertainment> = when (any) {
            is ArrayList<*> -> any as ArrayList<Entertainment>
            else -> this.any
        }
        if (isArrayEmpty(data)) {
            binding.parent.layoutParams =
                Constraints.LayoutParams(Constraints.LayoutParams.MATCH_PARENT, 0)
            return
        } else {
            binding.parent.layoutParams =
                Constraints.LayoutParams(Constraints.LayoutParams.MATCH_PARENT, Constraints.LayoutParams.WRAP_CONTENT)
        }
        if (TextUtils.isEmpty(heading)) {
            binding.heading.visibility = View.GONE
        } else {
            binding.heading.text = heading
        }
        val mMultiItemRowAdapter = SingleItemRecycleAdapter()
        val recycleMultiItemView = RecycleHMultiItemView(mContext)
        val mArrListAdapterParam: ArrayList<RecycleAdapterParams> = ArrayList()
        mArrListAdapterParam.add(RecycleAdapterParams(data, EntHItemView(mContext, screenName)))
        mMultiItemRowAdapter.setAdapterParams(mArrListAdapterParam)
        recycleMultiItemView.setAdapter(mMultiItemRowAdapter)
        binding.llContainer.visibility = View.VISIBLE
        binding.llContainer.removeAllViews()
        binding.llContainer.addView(recycleMultiItemView.populatedView)
    }

    fun notifyDataChanged() = mMultiItemRowAdapter.notifyDatahasChanged()
}
