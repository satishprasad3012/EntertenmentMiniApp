package com.satish.android.entertainmentminiapp.customrecyclercontrol;

/**
 *
 * @author satish
 * 27/03/2019
 *
 * **/

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import com.satish.android.entertainmentminiapp.R;
import com.satish.android.entertainmentminiapp.customrecyclercontrol.adapter.SingleItemRecycleAdapter;
import com.satish.android.entertainmentminiapp.customrecyclercontrol.helper.MultiListInterfaces;

public class RecycleMultiItemView {
	private final int PAGINATION_START = 1;
	private final Context mContext;
	private NestedRecyclerView recyclerView;
	private MultiListInterfaces.MultiListLoadMoreListner onLoadMoreListner;
	private View mView = null;
	private SwipeRefreshLayout mSwipeRefreshLayout = null;
	private GridLayoutManager gridLayoutManager;
	private SingleItemRecycleAdapter mAdapter;
	private Boolean isPullToRefreshCalled = false;
	private boolean isPullToRefreshEnabled = true;
	private MultiListInterfaces.OnPullToRefreshListener mPullToRefreshListener = null;
	private OnScrollListener onScrollListener;

	private int pastVisiblesItems, visibleItemCount, totalItemCount;
	private boolean loading = true;
	private int currentPage = PAGINATION_START;
	private boolean firstItemVisible;
	private int maxColoumCount;

	public RecycleMultiItemView(Context context) {
		mContext = context;
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = inflater.inflate(R.layout.view_multi_item_recycleview, null);
		mSwipeRefreshLayout = mView.findViewById(R.id.swiperefresh);
		recyclerView = mView.findViewById(R.id.recycleViewHome);
		recyclerView.setRecyclerListener(new RecyclerView.RecyclerListener() {
			@Override
			public void onViewRecycled(RecyclerView.ViewHolder holder) {

			}
		});
	}

	public void setNestedScrollingEnabled(boolean isNestedScrollingEnabled) {
		this.recyclerView.setNestedScrollingEnabled(isNestedScrollingEnabled);
	}

	private MultiListInterfaces.MultiListLoadMoreListner getOnLoadMoreListner() {
		return this.onLoadMoreListner;
	}

	public void setOnLoadMoreListner(MultiListInterfaces.MultiListLoadMoreListner onLoadMoreListner) {
		this.onLoadMoreListner = onLoadMoreListner;
		if (onLoadMoreListner != null) {
			loading = false;
		}
	}

	public void setAdapter(SingleItemRecycleAdapter adapter) {
		try {
			mAdapter = adapter;
			pullToRefreshComplete();
			final int maxNumberOfCols = (mAdapter).getMaxColumCount() > maxColoumCount ? mAdapter.getMaxColumCount() : maxColoumCount;
			gridLayoutManager = new GridLayoutManager(mContext, maxNumberOfCols);
			gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
				@Override
				public int getSpanSize(int position) {

					if (position >= mAdapter.getItemCount()) {
						Log.d("recycler", "position exceeding size : " + position + " size :" + mAdapter.getItemCount());
						return 0;
					}

					int numOfCol = mAdapter.getItem(position).getNumOfColumn();
					return (int) Math.ceil(maxNumberOfCols / numOfCol);
				}
			});
			recyclerView.setLayoutManager(gridLayoutManager);

			if (recyclerView != null)
			{
				recyclerView.setAdapter(adapter);
			}
			setPullRefreshListner();
			recyclerView.addOnScrollListener(new OnScrollListener() {

				@Override
				public void onScrollStateChanged(RecyclerView view, int scrollState) {
					if (onScrollListener != null) {
						onScrollListener.onScrollStateChanged(view, scrollState);
						if (scrollState == RecyclerView.SCROLL_STATE_DRAGGING) {
							(mAdapter).setScrolling(true);
						} else {
							(mAdapter).setScrolling(false);
						}

					}
				}

				@Override
				public void onScrolled(RecyclerView view, int dx, int dy) {

					boolean enable = false;
					if (recyclerView != null && recyclerView.getChildCount() > 0) {
						// check if the first item of the list is visible
						boolean firstItemVisible = gridLayoutManager.findFirstVisibleItemPosition() == 0;
						// check if the top of the first item is visible
						boolean topOfFirstItemVisible = recyclerView.getChildAt(0).getTop() == 0;
						boolean bottomOfFirstItemVisible = recyclerView.getChildAt(0).getBottom() >= 0;
						// enabling or disabling the refresh layout
						enable = firstItemVisible && topOfFirstItemVisible;
						RecycleMultiItemView.this.firstItemVisible = firstItemVisible && bottomOfFirstItemVisible;
					}
					mSwipeRefreshLayout.setEnabled(isPullToRefreshEnabled);

					visibleItemCount = gridLayoutManager.getChildCount();
					totalItemCount = gridLayoutManager.getItemCount();
					pastVisiblesItems = gridLayoutManager.findFirstVisibleItemPosition();
					//only if scrolling down
					if (dy >= 0) {
						onScroll();
					}
					if (onScrollListener != null) {
						onScrollListener.onScrolled(view, dx, dy);
					}
				}
			});

		} catch (Exception e) {
		}
	}

	public boolean isFirstItemVisible() {
		return firstItemVisible;
	}

	public void onScroll() {
		if (!loading && (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
			if (getOnLoadMoreListner() != null) {
				loading = true;
				getOnLoadMoreListner().loadMoreData(currentPage);
			} else {
				removeFooterLoader();
			}
		}
	}

	public void removeLoadMoreListener() {
		setOnLoadMoreListner(null);
		removeFooterLoader();
	}

	public void setScrollListner(OnScrollListener onScrollListener) {
		this.onScrollListener = onScrollListener;
	}

	public void pullToRefreshComplete() {
		if (isPullToRefreshCalled) {
			isPullToRefreshCalled = false;
			currentPage = PAGINATION_START;
			mSwipeRefreshLayout.setRefreshing(false);
		}
	}

	public void enablePullToRefresh(Boolean isEnabled) {
		isPullToRefreshEnabled = isEnabled;
		mSwipeRefreshLayout.setEnabled(isEnabled);
	}

	public RecyclerView getListView() {
		return recyclerView;
	}

	public GridLayoutManager getLayoutManager() {
		return gridLayoutManager;
	}

	public View getPopulatedView() {
		return mView;
	}

	public void removeFooterLoader() {
		loading = false;
		currentPage++;
	}

	public void resetPagination() {
		currentPage = PAGINATION_START;
		loading = false;

	}

	private void setPullRefreshListner() {
		mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				isPullToRefreshCalled = true;
				if (mPullToRefreshListener != null) {
					mPullToRefreshListener.onPulltoRefreshCalled();
				}
			}
		});

	}

	public void setPullToRefreshListener(MultiListInterfaces.OnPullToRefreshListener pullToRefreshListener) {
		this.mPullToRefreshListener = pullToRefreshListener;
	}

	public void removeAll() {
		if (mSwipeRefreshLayout != null) {
			mSwipeRefreshLayout.setOnRefreshListener(null);
		}
		onLoadMoreListner = null;
		mAdapter = null;
		gridLayoutManager = null;

		if (recyclerView != null) {
			recyclerView.addOnScrollListener(null);
		}

		recyclerView = null;
		mPullToRefreshListener = null;

	}

	/**
	 * In case of notifydatasetchanged coluoum count doesn't change so in case of future item contains more coloum set maxcolumcount
	 *
	 * @param coloumCount
	 */
	public void setMaxColoumCount(int coloumCount) {
		maxColoumCount = coloumCount;
	}

	/**
	 * Set recycled view count for viewType paas viewtype as hashcode of the view
	 *
	 * @param viewType
	 * @param max
	 */
	public void setMaxRecycledViews(int viewType, int max) {
		recyclerView.getRecycledViewPool().setMaxRecycledViews(viewType, max);

	}

	public void scrollToPosition(int position) {
		getListView().smoothScrollToPosition(position);
	}

}

