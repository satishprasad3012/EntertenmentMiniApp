package com.satish.android.entertainmentminiapp.ui.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.satish.android.entertainmentminiapp.model.Entertainment
import com.satish.android.entertainmentminiapp.model.EntertainmentDetail
import com.satish.android.entertainmentminiapp.network.ErrorResponse
import com.satish.android.entertainmentminiapp.repo.EntertainmentRepository

class EnListViewModel : ViewModel() {

    val errorMsg = MutableLiveData<ErrorResponse>()
    val entertainmentDetailMld = MutableLiveData<EntertainmentDetail>()

    fun enListCallAPI(
        searchText: String, page: Int
    ) {
        EntertainmentRepository.instance.getEntertainList(
            searchText, page, entertainmentDetailMld,errorMsg)
    }
}