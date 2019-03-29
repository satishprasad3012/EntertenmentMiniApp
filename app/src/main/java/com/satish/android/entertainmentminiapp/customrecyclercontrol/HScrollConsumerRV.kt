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

/**
 * RecyclerView which says that it can always scroll horizontally to the parent querying for it.
 * Use case is ViewPager. Which Intercept and consume touch event when none of the childs can scroll in the swipe direction.
 */
class HScrollConsumerRV : RecyclerView {

	constructor(context: Context) : this(context, null)

	constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

	constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

	/**
	 * to prevent viewpager from taking horizontal swipes
	 *
	 * by default returns true if layoutManger have items to scroll else false
	 **/
	override fun canScrollHorizontally(direction: Int) = true
}