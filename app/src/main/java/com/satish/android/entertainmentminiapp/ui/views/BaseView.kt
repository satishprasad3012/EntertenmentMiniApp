package com.satish.android.entertainmentminiapp.ui.views

/**
 *
 * @author satish
 * 28/03/2019
 *
 * **/

import android.content.Context
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.satish.android.entertainmentminiapp.customrecyclercontrol.adapter.BaseHolder
import com.satish.android.entertainmentminiapp.customrecyclercontrol.helper.MultiListInterfaces


abstract class BaseView @JvmOverloads constructor(protected val mContext: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    LinearLayout(mContext, attrs, defStyleAttr), MultiListInterfaces.OnRecycleViewHolderListner {
    private val mInflater: LayoutInflater =
        mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    protected fun getNewView(layoutId: Int, parent: ViewGroup?): View =
        mInflater.inflate(layoutId, parent ?: this, false)

    override fun onCreateHolder(parent: ViewGroup, position: Int): BaseHolder<*> =
        BaseHolder<ViewDataBinding>(getNewView(onCreateViewLayout(position), parent))

    @LayoutRes
    protected abstract fun onCreateViewLayout(position: Int): Int

    override fun onBindViewHolder(viewDataBinding: ViewDataBinding, any: Any?, isScrolling: Boolean) =
        onBindViewHolder(viewDataBinding, any)

    protected abstract fun onBindViewHolder(viewDataBinding: ViewDataBinding, any: Any?)

    override fun onViewRecycled(holder: BaseHolder<*>) {}
}