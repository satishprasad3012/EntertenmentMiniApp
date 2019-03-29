package com.satish.android.entertainmentminiapp.customrecyclercontrol.helper;

/**
 *
 * @author satish
 * 27/03/2019
 *
 * **/

import android.databinding.ViewDataBinding;
import android.view.ViewGroup;
import com.satish.android.entertainmentminiapp.customrecyclercontrol.adapter.BaseHolder;


public class MultiListInterfaces {
	public interface OnRecycleViewHolderListner {
		BaseHolder onCreateHolder(ViewGroup parent, int position);

		void onBindViewHolder(ViewDataBinding viewDataBinding, Object object, boolean isScrolling);

		void onViewRecycled(BaseHolder holder);
	}

	public interface OnRecycleViewScrollPositionListner {
		void onMultiListItemAtFirstVisiblePosition(Object object, Boolean isHeaderToBeShown);
	}

	public interface OnPullToRefreshListener {
		void onPulltoRefreshCalled();
	}

	public interface MultiListLoadMoreListner {
		void loadMoreData(int pageNumberToBeLoaded);
	}

	public interface OnListDataLoadListener {
		void onListDataLoaded();
	}

	public interface HorizontalListLoadMoreListner {
		void loadMoreData(int pageNumberToBeLoaded, OnListDataLoadListener onDataLoad);
	}
}
