package com.satish.android.entertainmentminiapp.ui.activity

/**
 *
 * @author satish
 * 28/03/2019
 *
 * **/

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import com.satish.android.entertainmentminiapp.R
import com.satish.android.entertainmentminiapp.customrecyclercontrol.RecycleMultiItemView
import com.satish.android.entertainmentminiapp.customrecyclercontrol.adapter.SingleItemRecycleAdapter
import com.satish.android.entertainmentminiapp.customrecyclercontrol.helper.MultiListInterfaces
import com.satish.android.entertainmentminiapp.customrecyclercontrol.helpers.RecycleAdapterParams
import com.satish.android.entertainmentminiapp.databinding.HomeActivityBinding
import com.satish.android.entertainmentminiapp.model.Entertainment
import com.satish.android.entertainmentminiapp.model.EntertainmentDetail
import com.satish.android.entertainmentminiapp.model.ListCountModel
import com.satish.android.entertainmentminiapp.ui.viewmodel.EnListViewModel
import com.satish.android.entertainmentminiapp.ui.views.BookmarkHView
import com.satish.android.entertainmentminiapp.ui.views.EnItemLargeView
import com.satish.android.entertainmentminiapp.utility.Constants
import com.satish.android.entertainmentminiapp.utility.observeNullable
import com.satish.android.entertainmentminiapp.utility.orZero
import java.util.*
import kotlin.collections.ArrayList

class HomeActivity : BaseActivity(), MultiListInterfaces.OnPullToRefreshListener {

    private lateinit var binding: HomeActivityBinding
    private val enListVM by lazy { ViewModelProviders.of(this).get(EnListViewModel::class.java) }
    private lateinit var recycleMultiItemView: RecycleMultiItemView
    private lateinit var mMultiItemRowAdapter: SingleItemRecycleAdapter
    private lateinit var mArrListAdapterParam: ArrayList<RecycleAdapterParams>
    private val mNewArrListAdapterParam = arrayOfNulls<RecycleAdapterParams>(TOTAL_NO_OF_ADAPTERS)

    private lateinit var bookmarkAdapter: RecycleAdapterParams
    private lateinit var enListAdapter: RecycleAdapterParams
    private var enData: ArrayList<Entertainment> = ArrayList()
    private var bookmarkData: ArrayList<Entertainment> = ArrayList()


    private lateinit var bookmarkView: BookmarkHView

    private var searchText: String = "harry" // default

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.home_activity)
        setToolbarHeading(getString(R.string.app_name))
        initUi()
    }

    private fun initUi() {
        addAdapters()
        recycleMultiItemView = RecycleMultiItemView(this)
        recycleMultiItemView.setPullToRefreshListener(this)
        recycleMultiItemView.enablePullToRefresh(true)
        mMultiItemRowAdapter = SingleItemRecycleAdapter()
        mArrListAdapterParam = ArrayList()
        setAdapter()
        observeEnData()
        observeBookmarkData()
        enListVM.enListCallAPI(searchText, 1)
    }

    private fun addAdapters() {
        bookmarkView =
            BookmarkHView(this, bookmarkData, getString(R.string.bookmark), screenName)
        bookmarkAdapter =
            RecycleAdapterParams(ListCountModel(Constants.RECYCLER_ID_BOOKMARK, 0), bookmarkView)
        addAdapter(POS_BOOKMARK, bookmarkAdapter)

        enListAdapter = RecycleAdapterParams(enData, EnItemLargeView(this, screenName))
        addAdapter(POS_SEARCHED_EN, enListAdapter)
    }

    private fun addAdapter(index: Int, adapter: RecycleAdapterParams?) {
        mNewArrListAdapterParam[index] = adapter
    }

    private fun setAdapter() {
        mArrListAdapterParam.clear()
        mArrListAdapterParam.addAll(ArrayList(Arrays.asList<RecycleAdapterParams>(*mNewArrListAdapterParam)))
        mMultiItemRowAdapter.setAdapterParams(mArrListAdapterParam)
        recycleMultiItemView.setAdapter(mMultiItemRowAdapter)
        binding.llContainer.visibility = View.VISIBLE
        binding.llContainer.removeAllViews()
        binding.llContainer.addView(recycleMultiItemView.populatedView)
    }

    private fun observeEnData() {
        enListVM.entertainmentDetailMld.observeNullable(this, {
            it?.let {
                if (it.searchList != null && it.searchList.size > 0) {
                    if (enData.size > 0) {
                        enData.addAll(it.searchList)
                        mMultiItemRowAdapter.notifyDatahasChanged()
                    } else {
                        setData(it)
                        recycleMultiItemView.pullToRefreshComplete()
                    }
                    recycleMultiItemView.removeFooterLoader()
                }
            }
        })
    }

    private fun setData(response: EntertainmentDetail) {
        enData.clear()
        response.searchList?.let { enData.addAll(it) }
        mArrListAdapterParam[POS_SEARCHED_EN] = enListAdapter

        recycleMultiItemView.setOnLoadMoreListner { pageNumberToBeLoaded ->
            val totalRes: Int = response.totalResults?.toInt().orZero()
            if (enData.size <= totalRes) {
                enListVM.enListCallAPI(searchText, pageNumberToBeLoaded)
            } else {
                recycleMultiItemView.removeLoadMoreListener()
            }
        }
        mMultiItemRowAdapter.notifyDatahasChanged()
    }

    private fun setAdapter1() {
        mMultiItemRowAdapter.setAdapterParams(mArrListAdapterParam)
        recycleMultiItemView.setAdapter(mMultiItemRowAdapter)
        binding.progressBar.visibility = View.GONE
        binding.llContainer.visibility = View.VISIBLE
        binding.llContainer.removeAllViews()
        binding.llContainer.addView(recycleMultiItemView.populatedView)
    }

    private fun observeEnData1() {
        enListVM.entertainmentDetailMld.observeNullable(this, {
            it?.let {
                if (it.searchList != null && it.searchList.size > 0) {
                    if (enData.size > 0) {
                        enData.addAll(it.searchList)
                        mMultiItemRowAdapter.notifyDatahasChanged()
                    } else {
                        enData = it.searchList
                        enListAdapter = RecycleAdapterParams(enData, EnItemLargeView(this, screenName))
                        mArrListAdapterParam.add(enListAdapter)
                        setAdapter()
                        recycleMultiItemView.setOnLoadMoreListner { pageNumberToBeLoaded ->
                            val totalRes: Int = it.totalResults?.toInt().orZero()
                            if (enData.size <= totalRes) {
                                enListVM.enListCallAPI(searchText, pageNumberToBeLoaded)
                            } else {
                                recycleMultiItemView.removeLoadMoreListener()
                            }
                        }
                    }
                    recycleMultiItemView.removeFooterLoader()
                }
            }

        })
    }

    //TODO; testing purpose, need to fetch from db
    private fun observeBookmarkData() {
        enListVM.entertainmentDetailMld.observeNullable(this, {
            it?.let {
                if (it.searchList != null && it.searchList.size > 0) {
                    bookmarkData.addAll(it.searchList)
                    bookmarkAdapter.dataObject =
                        ListCountModel(Constants.RECYCLER_ID_BOOKMARK, it.searchList.size)
                    mMultiItemRowAdapter.notifyDatahasChanged()
                    bookmarkView.notifyDataChanged()
                }
            }
        })
    }

    override fun onPulltoRefreshCalled() {
        enData.clear()
        enListVM.enListCallAPI(searchText, 1)
    }

    companion object {
        private const val POS_BOOKMARK = 0
        private const val POS_SEARCHED_EN = 1
        private const val TOTAL_NO_OF_ADAPTERS = 2


        fun startActivity(context: Context) {
            context.startActivity(Intent(context, HomeActivity::class.java))
        }
    }

    override val screenName = ""
}