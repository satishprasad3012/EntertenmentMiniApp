package com.satish.android.entertainmentminiapp.ui.listeners

import com.satish.android.entertainmentminiapp.model.Entertainment

interface EntActionListener {

    fun onBookmark(ent: Entertainment)

    fun onSearch(str:String)
}