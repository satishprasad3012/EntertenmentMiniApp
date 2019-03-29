package com.satish.android.entertainmentminiapp.repo

/**
 *
 * @author satish
 * 28/03/2019
 *
 * **/


import android.arch.lifecycle.MutableLiveData
import com.satish.android.entertainmentminiapp.model.EntertainmentDetail
import com.satish.android.entertainmentminiapp.network.ErrorResponse
import com.satish.android.entertainmentminiapp.network.RetrofitCallback
import com.satish.android.entertainmentminiapp.utility.Constants
import com.satish.android.entertainmentminiapp.utility.retroServices


class EntertainmentRepository {

    private object Holder {
        val INSTANCE = EntertainmentRepository()
    }

    fun getEntertainList(
        searchText: String, page: Int, entertainmentDetail: MutableLiveData<EntertainmentDetail>,
        errorMesg: MutableLiveData<ErrorResponse>
    ) {
        retroServices().getEntSearchList(searchText, page, Constants.API_KEY)
            .enqueue(object : RetrofitCallback<EntertainmentDetail>() {

                override fun onSuccess(response: EntertainmentDetail?) {
                    entertainmentDetail.value = response
                }

                override fun onFail(response: ErrorResponse?) {
                    errorMesg.value = response
                }
            })
    }

    companion object {
        val instance by lazy { EntertainmentRepository.Holder.INSTANCE }
    }
}