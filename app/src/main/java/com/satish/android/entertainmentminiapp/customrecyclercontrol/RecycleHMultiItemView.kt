package com.satish.android.entertainmentminiapp.customrecyclercontrol

/**
 *
 * @author satish
 * 27/03/2019
 *
 * **/

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import com.satish.android.entertainmentminiapp.R
import com.satish.android.entertainmentminiapp.customrecyclercontrol.adapter.BaseHolder
import com.satish.android.entertainmentminiapp.customrecyclercontrol.helper.MultiListInterfaces


class RecycleHMultiItemView(private val mContext: Context) {
    private val PAGINATION_START = 0
    private var recyclerView: HScrollConsumerRV? = null
    private var onLoadMoreListner: MultiListInterfaces.MultiListLoadMoreListner? = null
    var populatedView: View? = null
    var layoutManager: LinearLayoutManager? = null
        private set
    private var pastVisiblesItems: Int = 0
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0
    private var loading = true
    private var currentPage = PAGINATION_START

    init {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        populatedView = inflater.inflate(R.layout.view_multi_horizontal_item_recycleview, null)
        recyclerView = populatedView?.findViewById(R.id.recycleViewHome)
    }

    fun setOnLoadMoreListner(onLoadMoreListner: MultiListInterfaces.MultiListLoadMoreListner?) {
        this.onLoadMoreListner = onLoadMoreListner
        if (onLoadMoreListner != null) {
            loading = false
        }
    }

    fun setAdapter(adapter: RecyclerView.Adapter<BaseHolder<*>>) {
        try {
            layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
            recyclerView?.layoutManager = layoutManager
            recyclerView?.setHasFixedSize(true)
            recyclerView?.adapter = adapter
            recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrollStateChanged(view: RecyclerView, scrollState: Int) {}

                override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {

                    visibleItemCount = layoutManager?.childCount ?: 0
                    totalItemCount = layoutManager?.itemCount ?: 0
                    pastVisiblesItems = layoutManager?.findFirstVisibleItemPosition() ?: 0
                    //only if scrolling right
                    if (dx >= 0) {
                        if (!loading && visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            if (onLoadMoreListner != null) {
                                loading = true
                                onLoadMoreListner?.loadMoreData(currentPage)
                            } else {
                                removeFooterLoader()
                            }
                        }
                    }
                }
            })

        } catch (e: Exception) {
        }

    }

    fun removeLoadMoreListener() {
        setOnLoadMoreListner(null)
        removeFooterLoader()
    }

    fun removeFooterLoader() {
        loading = false
        currentPage++
    }

    fun resetPagination() {
        currentPage = PAGINATION_START
        loading = false

    }

    /**
     * Set recycled view count for viewType paas viewtype as hashcode of the view
     *
     * @param viewType
     * @param max
     */
    fun setMaxRecycledViews(viewType: Int, max: Int) {
        recyclerView?.recycledViewPool?.setMaxRecycledViews(viewType, max)

    }
}

