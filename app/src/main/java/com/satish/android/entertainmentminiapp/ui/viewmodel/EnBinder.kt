package com.satish.android.entertainmentminiapp.ui.viewmodel

import android.content.Context
import android.databinding.Bindable
import android.databinding.Observable
import android.databinding.PropertyChangeRegistry
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.view.View
import com.satish.android.entertainmentminiapp.BR
import com.satish.android.entertainmentminiapp.R
import com.satish.android.entertainmentminiapp.model.Entertainment
import com.satish.android.entertainmentminiapp.ui.activity.EntDetailActivity
import com.satish.android.entertainmentminiapp.ui.listeners.EntActionListener
import com.satish.android.entertainmentminiapp.utility.isNetworkAvailable
import com.satish.android.entertainmentminiapp.utility.toast

class EnBinder(val context: Context, val entertainment: Entertainment, val screenName: String) : Observable {

    private val mPropertyChangeRegistry = PropertyChangeRegistry()
    private var bookmarkListener: EntActionListener? = null

    init {
        if (context is EntActionListener)
            bookmarkListener = context
    }

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
        bookmarkListener?.onBookmark(entertainment)
        onBookMarked(entertainment.bookmark)
    }

    fun onItemClick() = View.OnClickListener {
        if (entertainment.imdbID.isNullOrBlank()) return@OnClickListener
        if (isNetworkAvailable(context) || entertainment.bookmark) {
            EntDetailActivity.startActivity(
                context, entertainment.imdbID,
                entertainment.Title.orEmpty()
            )
        } else {
            context.toast(R.string.no_internet)
        }
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
