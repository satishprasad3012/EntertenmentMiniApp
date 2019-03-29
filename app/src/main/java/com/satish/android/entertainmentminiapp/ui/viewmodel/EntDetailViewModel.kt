package com.satish.android.entertainmentminiapp.ui.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.satish.android.entertainmentminiapp.model.EntDetail
import com.satish.android.entertainmentminiapp.network.ErrorResponse
import com.satish.android.entertainmentminiapp.repo.EntertainmentRepository

class EntDetailViewModel : ViewModel() {

    val entDetail = MutableLiveData<EntDetail>()
    val errorMsg = MutableLiveData<ErrorResponse>()

    fun enDetailAPI(
        imdbId: String
    ) {
        EntertainmentRepository.instance.getEntDetail(
            imdbId, entDetail, errorMsg
        )
    }


}