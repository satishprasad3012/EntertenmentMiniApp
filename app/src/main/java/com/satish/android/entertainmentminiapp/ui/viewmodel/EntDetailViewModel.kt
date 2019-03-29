package com.satish.android.entertainmentminiapp.ui.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.satish.android.entertainmentminiapp.model.EntDetail
import com.satish.android.entertainmentminiapp.network.ErrorResponse
import com.satish.android.entertainmentminiapp.repo.EntertainmentRepository
import com.satish.android.entertainmentminiapp.utility.isNetworkAvailable
import kotlinx.coroutines.experimental.launch

class EntDetailViewModel : ViewModel() {

    val entDetail = MutableLiveData<EntDetail>()
    val errorMsg = MutableLiveData<ErrorResponse>()

    fun enDetailAPI(
        imdbId: String
    ) {
        if (isNetworkAvailable)
            EntertainmentRepository.instance.getEntDetail(
                imdbId, entDetail, errorMsg
            )
        else
            getBookmarkedItemFromDb(imdbId)
    }

    fun updateBookmarkIntoDb(ent: EntDetail) {
        launch {
            EntertainmentRepository.instance.updateBookmark(ent)
        }
    }

    fun getBookmarkedItemFromDb(imdbId:String) {
        launch {
            val b = EntertainmentRepository.instance.getBookmarkedItemFromDb(imdbId)
            entDetail.postValue(b)
        }
    }
}