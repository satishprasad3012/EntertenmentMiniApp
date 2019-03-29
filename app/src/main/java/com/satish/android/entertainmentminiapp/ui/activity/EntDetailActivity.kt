package com.satish.android.entertainmentminiapp.ui.activity

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.satish.android.entertainmentminiapp.R
import com.satish.android.entertainmentminiapp.analytics.EntAnalytics
import com.satish.android.entertainmentminiapp.databinding.EntdetailActivityBinding
import com.satish.android.entertainmentminiapp.model.EntDetail
import com.satish.android.entertainmentminiapp.ui.viewmodel.EntDetailViewModel
import com.satish.android.entertainmentminiapp.utility.*

class EntDetailActivity : BaseActivity() {

    private lateinit var binding: EntdetailActivityBinding
    private val entDetailVM by lazy { ViewModelProviders.of(this).get(EntDetailViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.entdetail_activity)
        setToolbar()
        observeData()
        initUi()
    }

    private fun initUi() {
        fadeIn(binding.loadingLay.loadingTv)
        val imdbId = intent?.extras?.getString(IMDB_ID, "").orEmpty()
        if (imdbId.isBlank()) {
            toast(getString(R.string.error))
            onBackPressed()
        }
        entDetailVM.enDetailAPI(imdbId)
    }

    private fun observeData() {
        // ent data handing
        entDetailVM.entDetail.observeNullable(this, {
            it?.let {
                binding.data = it
                binding.executePendingBindings()
                if(bookmarkedSet.contains(it.imdbID)) // update complete data for bookmark item
                     updateBookmarkEntIntDb(it)
            }
            fadeOut(binding.loadingLay.loadingTv)
        })

        // error handling
        entDetailVM.errorMsg.observeNonNull(this, {
            fadeIn(binding.errorLay.errorTv)
            binding.errorLay.errorTv.text = entDetailVM.errorMsg.value?.message
            fadeOut(binding.loadingLay.loadingTv)
        })
    }

    private fun updateBookmarkEntIntDb(ent:EntDetail){
        entDetailVM.updateBookmarkIntoDb(ent)
    }

    private fun setToolbar() {
        setToolbarHeading(intent?.extras?.getString(TITLE, "").orEmpty())
        setNavigationIcon(R.drawable.ic_back_svg)
    }

    companion object {
        private const val IMDB_ID = "IMDB_ID"
        private const val TITLE = "TITLE"

        fun startActivity(context: Context, imdbId: String, title: String) {
            val intent = Intent(context, EntDetailActivity::class.java)
            intent.putExtra(IMDB_ID, imdbId)
            intent.putExtra(TITLE, title)
            context.startActivity(intent)
        }
    }

    override val screenName = EntAnalytics.ENT_DETAIL_SCREEN
}