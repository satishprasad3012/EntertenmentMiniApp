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
import com.satish.android.entertainmentminiapp.analytics.EntAnalytics
import com.satish.android.entertainmentminiapp.customrecyclercontrol.RecycleMultiItemView
import com.satish.android.entertainmentminiapp.customrecyclercontrol.adapter.SingleItemRecycleAdapter
import com.satish.android.entertainmentminiapp.customrecyclercontrol.helper.MultiListInterfaces
import com.satish.android.entertainmentminiapp.customrecyclercontrol.helpers.RecycleAdapterParams
import com.satish.android.entertainmentminiapp.databinding.HomeActivityBinding
import com.satish.android.entertainmentminiapp.model.Entertainment
import com.satish.android.entertainmentminiapp.model.EntertainmentRes
import com.satish.android.entertainmentminiapp.model.ListCountModel
import com.satish.android.entertainmentminiapp.ui.listeners.EntActionListener
import com.satish.android.entertainmentminiapp.ui.viewmodel.EnListViewModel
import com.satish.android.entertainmentminiapp.ui.views.BookmarkHView
import com.satish.android.entertainmentminiapp.ui.views.EnItemLargeView
import com.satish.android.entertainmentminiapp.ui.views.HomeEmptyView
import com.satish.android.entertainmentminiapp.utility.Constants
import com.satish.android.entertainmentminiapp.utility.bookmarkedSet
import com.satish.android.entertainmentminiapp.utility.observeNullable
import com.satish.android.entertainmentminiapp.utility.orZero
import java.util.*
import kotlin.collections.ArrayList


class HomeActivity : BaseActivity(), MultiListInterfaces.OnPullToRefreshListener,
    EntActionListener {

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
    private var isPullToRefresh: Boolean = false


    private lateinit var bookmarkView: BookmarkHView

    private var searchText: String = "harry"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.home_activity)
        setToolbarHeading(getString(R.string.app_name))
        initUi()
    }

    private fun initUi() {
        searchText = getString(R.string.default_search_value)
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
                    updateBookmarkedInList(it.searchList)
                    if (enData.size > 0) {
                        if (isPullToRefresh) { // handle for avoid unnecessary load
                            enData = it.searchList
                            recycleMultiItemView.pullToRefreshComplete()
                        } else
                            enData.addAll(it.searchList)
                        mMultiItemRowAdapter.notifyDatahasChanged()
                    } else {
                        setData(it)
                        recycleMultiItemView.pullToRefreshComplete()
                        binding.progressBar.visibility = View.GONE
                    }
                    recycleMultiItemView.removeFooterLoader()
                } else {
                    if (enData.size < 1) {
                        setEmptyView()
                        recycleMultiItemView.pullToRefreshComplete()
                        binding.progressBar.visibility = View.GONE
                    }
                }
                isPullToRefresh = false
            }
        })
    }

    private fun updateBookmarkedInList(ents: ArrayList<Entertainment>) {
        ents.forEach {
            if (bookmarkedSet.contains(it.imdbID))
                it.bookmark = true
        }
    }

    private fun setData(response: EntertainmentRes) {
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

    private fun observeBookmarkData() {
        enListVM.bookmarkList.observeNullable(this, {
            bookmarkData.clear()
            it?.let {
                bookmarkData.addAll(it)
                bookmarkAdapter.dataObject =
                    ListCountModel(Constants.RECYCLER_ID_BOOKMARK, it.size)
            }
            mMultiItemRowAdapter.notifyDatahasChanged()
            bookmarkView.notifyDataChanged()
        })
    }

    override fun onPulltoRefreshCalled() {
        // enData.clear()
        isPullToRefresh = true
        enListVM.enListCallAPI(searchText, 1)
    }

    override fun onBookmark(ent: Entertainment) {
        enListVM.bookmarkEnt(ent)
        if (ent.bookmark) {
            bookmarkData.add(ent)
        } else
            removeUnBookmarkedFromList(ent.imdbID.orEmpty())
        bookmarkAdapter.dataObject =
            ListCountModel(Constants.RECYCLER_ID_BOOKMARK, bookmarkData.size)
        mMultiItemRowAdapter.notifyDatahasChanged()
        bookmarkView.notifyDataChanged()
    }

    override fun onSearch(str: String) {
        isPullToRefresh = false
        searchText = str
        enData.clear()
        enListVM.enListCallAPI(searchText, 1)
    }

    private fun removeUnBookmarkedFromList(id: String) {
        val itr = bookmarkData.iterator()
        while (itr.hasNext()) {
            val ent = itr.next()
            if (ent.imdbID == id)
                itr.remove()
        }
    }

    private fun setEmptyView() {
        val adapterParams =
            RecycleAdapterParams(null, HomeEmptyView(this))
        mArrListAdapterParam[POS_SEARCHED_EN] = adapterParams
        mMultiItemRowAdapter.notifyDatahasChanged()
    }

    companion object {
        private const val POS_BOOKMARK = 0
        private const val POS_SEARCHED_EN = 1
        private const val TOTAL_NO_OF_ADAPTERS = 2

        fun startActivity(context: Context) {
            context.startActivity(Intent(context, HomeActivity::class.java))
        }
    }

    override val screenName = EntAnalytics.ENT_LIST_SCREEN
}