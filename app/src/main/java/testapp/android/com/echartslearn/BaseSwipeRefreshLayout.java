package testapp.android.com.echartslearn;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

//import com.jiaheng.agent.R;

/**
 * 对应recycleView上拉加载下拉刷新的View布局
 */
public class BaseSwipeRefreshLayout extends SuperSwipeRefreshLayout {
	private LinearLayout view_fresh_ll;
	private OnFreshOrMoreListener onFreshOrMoreListener;
	private int page = 1;
	private int headColor = Color.parseColor("#f7f7f7");

	public void showHeader(boolean isEnable) {
		super.setIsHeadEnable(isEnable);
	}

	public void showFooter(boolean isEnable) {
		super.setIsFootEnable(isEnable);
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public OnFreshOrMoreListener getOnFreshOrMoreListener() {
		return onFreshOrMoreListener;
	}

	public void setOnFreshOrMoreListener(OnFreshOrMoreListener onFreshOrMoreListener) {
		this.onFreshOrMoreListener = onFreshOrMoreListener;
	}

//    public void setHeadColor(int headColor) {
//        this.headColor = headColor;
//        super.setHeadColor(headColor);
//        view_fresh_ll.setBackgroundColor(headColor);
//    }

	private void init(Context context) {
		View headView = LayoutInflater.from(context).inflate(R.layout.view_fresh, null);
		view_fresh_ll = (LinearLayout) headView.findViewById(R.id.view_fresh_ll);
		this.setHeaderView(headView);
		this.setFooterView(LayoutInflater.from(context).inflate(R.layout.view_more, null));
		this.setOnPullRefreshListener(new OnPullRefreshListener() {
			@Override
			public void onRefresh() {
				if (onFreshOrMoreListener != null) {
					page = 1;
					onFreshOrMoreListener.OnFresh();
				}
			}

			@Override
			public void onPullDistance(int distance) {
			}

			@Override
			public void onPullEnable(boolean enable) {
			}
		});

		this.setOnPushLoadMoreListener(new OnPushLoadMoreListener() {
			@Override
			public void onLoadMore() {
				if (onFreshOrMoreListener != null) {
					++page;
					onFreshOrMoreListener.OnMore();
				}
			}

			@Override
			public void onPushDistance(int distance) {
			}

			@Override
			public void onPushEnable(boolean enable) {
			}
		});
	}

	public BaseSwipeRefreshLayout(Context context) {
		super(context);
		init(context);
	}

	public BaseSwipeRefreshLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public interface OnFreshOrMoreListener {
		void OnFresh();

		void OnMore();
	}
}
