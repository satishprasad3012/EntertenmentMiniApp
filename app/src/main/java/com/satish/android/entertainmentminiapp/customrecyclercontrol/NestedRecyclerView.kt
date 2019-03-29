package com.satish.android.entertainmentminiapp.customrecyclercontrol

/**
 *
 * @author satish
 * 27/03/2019
 *
 * **/

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration

open class NestedRecyclerView : RecyclerView {
    private val INVALID_POINTER = -1
    private var scrollPointerId = INVALID_POINTER
    private var initialTouchX = 0
    private var initialTouchY = 0
    private var touchSlop = 0

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val vc = ViewConfiguration.get(context)
        touchSlop = vc.scaledTouchSlop
    }

    override fun setScrollingTouchSlop(slopConstant: Int) {
        super.setScrollingTouchSlop(slopConstant)

        val vc = ViewConfiguration.get(context)
        when (slopConstant) {
            TOUCH_SLOP_DEFAULT -> touchSlop = vc.scaledTouchSlop
            TOUCH_SLOP_PAGING -> touchSlop = vc.scaledPagingTouchSlop
        }
    }

    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        if (e == null) {
            return false
        }

        val action = MotionEvent.obtain(e).action
        val actionIndex = MotionEvent.obtain(e).actionIndex

        when (action) {
            MotionEvent.ACTION_DOWN -> {
                scrollPointerId = MotionEvent.obtain(e).getPointerId(0)
                initialTouchX = Math.round(e.x + 0.5f)
                initialTouchY = Math.round(e.y + 0.5f)
                return super.onInterceptTouchEvent(e)
            }

            MotionEvent.ACTION_POINTER_DOWN -> {
                scrollPointerId = MotionEvent.obtain(e).getPointerId(actionIndex)
                initialTouchX = Math.round(MotionEvent.obtain(e).getX(actionIndex) + 0.5f)
                initialTouchY = Math.round(MotionEvent.obtain(e).getY(actionIndex) + 0.5f)
                return super.onInterceptTouchEvent(e)
            }

            MotionEvent.ACTION_MOVE -> {
                val index = MotionEvent.obtain(e).findPointerIndex(scrollPointerId)
                if (index < 0) {
                    return false
                }

                val x = Math.round(MotionEvent.obtain(e).getX(index) + 0.5f)
                val y = Math.round(MotionEvent.obtain(e).getY(index) + 0.5f)
                if (scrollState != SCROLL_STATE_DRAGGING) {
                    val dx = x - initialTouchX
                    val dy = y - initialTouchY
                    var startScroll = false
                    if (layoutManager!!.canScrollHorizontally() && Math.abs(dx) > touchSlop && (layoutManager!!.canScrollVertically() || Math.abs(dx) > Math.abs(dy))) {
                        startScroll = true
                    }
                    if (layoutManager!!.canScrollVertically() && Math.abs(dy) > touchSlop && (layoutManager!!.canScrollHorizontally() || Math.abs(dy) > Math.abs(dx))) {
                        startScroll = true
                    }
                    return startScroll && super.onInterceptTouchEvent(e)
                }

                return super.onInterceptTouchEvent(e)
            }

            else -> {
                return super.onInterceptTouchEvent(e)
            }
        }
    }
}