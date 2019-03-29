package com.satish.android.entertainmentminiapp.utility

/**
 *
 * @author satish
 * 28/03/2019
 *
 * **/

import android.view.View
import android.view.animation.AnimationUtils

fun fadeOut(view: View?) {
    if (view == null || view.visibility == View.GONE) return
    view.startAnimation(AnimationUtils.loadAnimation(view.context, android.R.anim.fade_out))
    view.visibility = View.GONE
}

fun fadeIn(view: View?) {
    if (view == null) return
    view.visibility = View.VISIBLE
    view.startAnimation(AnimationUtils.loadAnimation(view.context, android.R.anim.fade_in))
}
