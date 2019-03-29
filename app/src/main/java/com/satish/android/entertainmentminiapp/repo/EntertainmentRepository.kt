package com.satish.android.entertainmentminiapp.repo

/**
 *
 * @author satish
 * 28/03/2019
 *
 * **/


import android.arch.lifecycle.MutableLiveData
import com.satish.android.entertainmentminiapp.app.EntertainmentApplication
import com.satish.android.entertainmentminiapp.database.entities.EntItemEntity
import com.satish.android.entertainmentminiapp.model.EntDetail
import com.satish.android.entertainmentminiapp.model.Entertainment
import com.satish.android.entertainmentminiapp.model.EntertainmentRes
import com.satish.android.entertainmentminiapp.network.ErrorResponse
import com.satish.android.entertainmentminiapp.network.RetrofitCallback
import com.satish.android.entertainmentminiapp.utility.Constants
import com.satish.android.entertainmentminiapp.utility.bookmarkedSet
import com.satish.android.entertainmentminiapp.utility.retroServices
import kotlinx.coroutines.experimental.launch


class EntertainmentRepository {

    private object Holder {
        val INSTANCE = EntertainmentRepository()
    }

    private val bookmarkDao by lazy {
        EntertainmentApplication.instance.appDatabase.bookmarkItemDao()
    }

    fun getEntertainList(
        searchText: String, page: Int, entertainmentDetail: MutableLiveData<EntertainmentRes>,
        errorMesg: MutableLiveData<ErrorResponse>
    ) {
        retroServices().getEntSearchList(searchText, page, Constants.API_KEY)
            .enqueue(object : RetrofitCallback<EntertainmentRes>() {

                override fun onSuccess(response: EntertainmentRes?) {
                    entertainmentDetail.value = response
                }

                override fun onFail(response: ErrorResponse?) {
                    errorMesg.value = response
                }
            })
    }

    fun getEntDetail(
        imdbId: String, entDetail: MutableLiveData<EntDetail>,
        errorMesg: MutableLiveData<ErrorResponse>
    ) {
        retroServices().getEntDetail(imdbId, Constants.API_KEY)
            .enqueue(object : RetrofitCallback<EntDetail>() {

                override fun onSuccess(response: EntDetail?) {
                    entDetail.value = response
                }

                override fun onFail(response: ErrorResponse?) {
                    errorMesg.value = response
                }
            })

    }

    fun bookmarkEnt(ent: Entertainment) {
        if (ent.imdbID.isNullOrBlank()) return
        if (ent.bookmark) {
            bookmarkDao.insert(
                EntItemEntity(
                    ent.imdbID, ent.Title.orEmpty(), ent.Year.orEmpty(),
                    ent.Type.orEmpty(), ent.Poster.orEmpty(), "", "", "", ""
                )
            )
            bookmarkedSet.add(ent.imdbID)
        } else {
            bookmarkDao.deleteBookmark(ent.imdbID)
            bookmarkedSet.remove(ent.imdbID)
        }
    }

    fun updateBookmark(imdbId: String) {

    }

    suspend fun getAllBookmark(): ArrayList<Entertainment>? {
        var bookmarkList = bookmarkDao.getAllBookmarkItems()
        if (bookmarkList.isNullOrEmpty()) return null
        val bookmarks = arrayListOf<Entertainment>()
        bookmarkList.forEach {
            val b =
                Entertainment(
                    it.imdbID, it.title, it.Year, it.Type, it.Poster
                )
            bookmarks.add(b)
            bookmarkedSet.add(it.imdbID)
        }
        return bookmarks
    }

    companion object {
        val instance by lazy { EntertainmentRepository.Holder.INSTANCE }
    }
}