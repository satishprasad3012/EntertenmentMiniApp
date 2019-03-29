package com.satish.android.entertainmentminiapp.ui.viewmodel

import android.content.Context
import android.databinding.Bindable
import android.databinding.Observable
import android.databinding.PropertyChangeRegistry
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import com.satish.android.entertainmentminiapp.BR
import com.satish.android.entertainmentminiapp.R
import com.satish.android.entertainmentminiapp.model.Entertainment

class EnBinder(val context: Context, val entertainment: Entertainment, val screenName: String)
    : Observable {

    private val mPropertyChangeRegistry = PropertyChangeRegistry()

    val title = entertainment.Title.orEmpty()
    val image = entertainment.Poster.orEmpty()
    val year = entertainment.Year.orEmpty()


    val bookmarkDrawable: Drawable?
        @Bindable
        get() = if (entertainment.bookmark) {
            ContextCompat.getDrawable(context, R.drawable.ic_bookmark_svg)
        } else {
            ContextCompat.getDrawable(context, R.drawable.ic_unbookmark_svg)
        }

    fun onBookmarkClick() {
        entertainment.bookmark = !entertainment.bookmark
        onBookMarked(entertainment.bookmark)
    }

    open fun onBookMarked(bookmarked: Boolean) {
        entertainment.bookmark = bookmarked
        mPropertyChangeRegistry.notifyChange(this, BR.bookmarkDrawable)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        mPropertyChangeRegistry.remove(callback)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        mPropertyChangeRegistry.add(callback)
    }
}
