package com.satish.android.entertainmentminiapp.ui.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.satish.android.entertainmentminiapp.model.Entertainment
import com.satish.android.entertainmentminiapp.model.EntertainmentRes
import com.satish.android.entertainmentminiapp.network.ErrorResponse
import com.satish.android.entertainmentminiapp.repo.EntertainmentRepository
import kotlinx.coroutines.experimental.launch

class EnListViewModel : ViewModel() {

    val errorMsg = MutableLiveData<ErrorResponse>()
    val entertainmentDetailMld = MutableLiveData<EntertainmentRes>()
    val bookmarkList = MutableLiveData<ArrayList<Entertainment>>()
    val bookmark = MutableLiveData<Entertainment>()
    var bookmarkedSet =  HashSet<String>()

    init {
        getBookmarksFromDb()
    }

    fun enListCallAPI(
        searchText: String, page: Int
    ) {
        EntertainmentRepository.instance.getEntertainList(
            searchText, page, entertainmentDetailMld,errorMsg,bookmark)
    }

    fun bookmarkEnt(en:Entertainment){
        launch {
            EntertainmentRepository.instance.bookmarkEnt(en)
        }
    }

    private fun getBookmarksFromDb(){
        launch {
            val bookmarks = EntertainmentRepository.instance.getAllBookmark()
            bookmarkList.postValue(bookmarks)
        }
    }
}