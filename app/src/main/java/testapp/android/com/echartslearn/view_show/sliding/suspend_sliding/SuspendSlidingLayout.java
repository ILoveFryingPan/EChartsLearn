package testapp.android.com.echartslearn.view_show.sliding.suspend_sliding;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import testapp.android.com.echartslearn.R;

public class SuspendSlidingLayout extends RelativeLayout{

	/**
	 * 该自定义控件至少有一个子控件，最多2个子控件
	 * 顶部默认不动的子控件下面称为子控件1
	 * 滑动子控件在下面称为子控件2，不管有没有子控件1，一直叫子控件2
	 */

	/**
	 * 表示子控件2布局距离顶部的距离
	 * 当值为-1的时候不绘制
	 */
	private int slidingLayoutTop = -1;
	/**
	 * 这个变量表示子控件2向上滑动最高可以到达的位置
	 * 当顶部有子控件1的时候，如果该子控件1不动，
	 * 则子控件2的最高位置就是子控件1的下面
	 * 如果子控件1可滑动，则子控件2滑动到子控件1
	 * 下面后整体继续滑动，则此时子控件2最高可到达
	 * 的位置就是父容器可视部分的顶部
	 */
	private int slidingMotionLessTop;
	/**
	 * 该参数是子控件1布局距离顶部的距离
	 * 目前没有过多的处理，默认顶部
	 */
	private int motionLessTop;
	/**
	 * 这个是子控件2向上滑动的距离，因为scrollBy向上滑动时值是大于0的，所以为正数
	 * 最大值等于slidingMotionLessTop的值
	 */
	private int scrollDistance = 0;
	/**
	 * 该参数是当子控件1可滑动时有效
	 * 代表的是该自定义控件整体向上滑动的距离
	 * 最大值是顶部子控件的高度
	 */
	private int motionLessScrollDistance = 0;
	/**
	 * 当isSlidingMotionLessView的值是false时表示子控件1不可滑动
	 * 否则可以滑动
	 */
	private boolean isSlidingMotionLessView = false;
	/**
	 * 该变量表示子控件不能再向上滑动的时候，外部的滑动监听继续向上滑动时的额外上滑距离
	 * 当该变量的值大于0时，子控件1和子控件2不可滑动，当该变量的值等于0是
	 */
	private int emptyScrollDistance = 0;

	public void setSlidingLayoutTop(int slidingLayoutTop) {
		SlidingChildLayout childLayout;
		if (getChildCount() == 2) {
			if (getChildAt(0).getMeasuredHeight() + motionLessTop > slidingLayoutTop) {
				this.slidingLayoutTop = getChildAt(0).getMeasuredHeight() + motionLessTop;
			} else {
				this.slidingLayoutTop = slidingLayoutTop;
			}
			childLayout = (SlidingChildLayout) getChildAt(1);
		} else {
			this.slidingLayoutTop = slidingLayoutTop;
			childLayout = (SlidingChildLayout) getChildAt(0);
		}
		childLayout.setSlidingLayoutTop(this.slidingLayoutTop);
		childLayout.setSlidingMotionLessTop(slidingMotionLessTop);
		childLayout.layout(0, 0, getWidth(), getHeight());
	}

	public void setSlidingMotionLessTop(int slidingMotionLessTop) {
		this.slidingMotionLessTop = slidingMotionLessTop;
	}

	public void setMotionLessTop(int motionLessTop) {
		this.motionLessTop = motionLessTop;
	}

	public void setSlidingMotionLessView(boolean slidingMotionLessView) {
		isSlidingMotionLessView = slidingMotionLessView;
	}

	public SuspendSlidingLayout(Context context) {
		super(context);
	}

	public SuspendSlidingLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initAttrs(context, attrs);
	}

	public SuspendSlidingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initAttrs(context, attrs);
	}

	private void initAttrs(Context mContext, AttributeSet attrs) {
		TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.SuspendSlidingLayout);
		if (ta != null)
			isSlidingMotionLessView = ta.getBoolean(R.styleable.SuspendSlidingLayout_isSlidingMotionLessView, false);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (getChildCount() < 1 || getChildCount() > 2) {
			throw new RuntimeException("至少有1个子控件，且不能超过2个");
		}

		if (isSlidingMotionLessView && getChildCount() != 2) {
			throw new RuntimeException("当isSlidingMotionLessView的值为true时，子控件的数量必须为2个");
		}

		if (getChildCount() == 2) {
			View childZero = getChildAt(0);
			if (childZero != null) {
				childZero.layout(0, motionLessTop, childZero.getMeasuredWidth(), motionLessTop + childZero.getMeasuredHeight());
				if (slidingLayoutTop != -1 && motionLessTop + childZero.getMeasuredHeight() > slidingLayoutTop) {
					slidingLayoutTop = motionLessTop + childZero.getMeasuredHeight();
				}
				slidingMotionLessTop = motionLessTop + childZero.getMeasuredHeight();
			}
			if (slidingLayoutTop != -1)
				layoutSlidingView(getChildAt(1));
		} else if (slidingLayoutTop != -1) {
			layoutSlidingView(getChildAt(0));
		}
	}

	private void layoutSlidingView(View slidingView) {
		if (slidingView != null) {
			if (!(slidingView instanceof SlidingChildLayout)) {
				throw new RuntimeException("滑动子控件必须是SlidingChildLayout或者SlidingChildLayout子控件的对象");
			}
			SlidingChildLayout childLayout = (SlidingChildLayout) slidingView;
			childLayout.setSlidingLayoutTop(slidingLayoutTop);
			childLayout.setSlidingMotionLessTop(slidingMotionLessTop);
			slidingView.layout(0, 0, getWidth(), getHeight());
		}
	}

	public void moveMotionLess(int distance) {
		if (getChildCount() < 2) {
			throw new RuntimeException("要求子控件的数量是2个");
		}

		View childZero = getChildAt(0);
		childZero.scrollBy(distance, 0);
	}

	public void moveSliding(int distance) {
		View slidingView;
		if (getChildCount() == 2) {
			slidingView = getChildAt(1);
		} else {
			slidingView = getChildAt(0);
		}
		slidingView.scrollBy(distance, 0);
	}

	public synchronized void onScrollChange(int scrollY, int oldScrollY){
		View childZero = null;
		SlidingChildLayout childLayout;
		if (getChildCount() == 2) {
			childZero = getChildAt(0);
			childLayout = (SlidingChildLayout) getChildAt(1);
		} else {
			childLayout = (SlidingChildLayout) getChildAt(0);
		}

		int offset = scrollY - oldScrollY;

		if (emptyScrollDistance > 0) {
			if (emptyScrollDistance + offset > 0) {
				emptyScrollDistance += offset;
				return;
			} else {
				offset += emptyScrollDistance;
				emptyScrollDistance = 0;
			}
		}

		if (offset > 0) {
			if (slidingLayoutTop - slidingMotionLessTop > scrollDistance) {
				if (scrollDistance + offset > slidingLayoutTop - slidingMotionLessTop) {
					childLayout.scrollBy(childLayout.getScrollX(), slidingLayoutTop - slidingMotionLessTop - scrollDistance);

					offset = offset + scrollDistance + slidingMotionLessTop - slidingLayoutTop;
					scrollDistance = slidingLayoutTop - slidingMotionLessTop;

					if (isSlidingMotionLessView) {
						if (offset != 0 && childZero != null && motionLessTop + childZero.getMeasuredHeight() > motionLessScrollDistance) {
							if (motionLessScrollDistance + offset > motionLessTop + childZero.getMeasuredHeight()) {
								scrollBy(getScrollX(), motionLessTop + childZero.getMeasuredHeight() - motionLessScrollDistance);

								emptyScrollDistance = motionLessScrollDistance + offset - motionLessTop - childZero.getMeasuredHeight();
								motionLessScrollDistance = motionLessTop + childZero.getMeasuredHeight();

							} else {
								scrollBy(getScrollX(), offset);
								motionLessScrollDistance += offset;
							}
						}
					} else {
						emptyScrollDistance += offset;
					}
				} else {
					childLayout.scrollBy(childLayout.getScrollX(), offset);
					scrollDistance += offset;
				}
			} else if (isSlidingMotionLessView) {
				if (childZero != null && motionLessTop + childZero.getMeasuredHeight() > motionLessScrollDistance) {
					if (motionLessScrollDistance + offset > motionLessTop + childZero.getMeasuredHeight()) {
						scrollBy(getScrollX(), motionLessTop + childZero.getMeasuredHeight() - motionLessScrollDistance);

						emptyScrollDistance = motionLessScrollDistance + offset - motionLessTop - childZero.getMeasuredHeight();
						motionLessScrollDistance = motionLessTop + childZero.getMeasuredHeight();

					} else {
						scrollBy(getScrollX(), offset);
						motionLessScrollDistance += offset;
					}
				}
			} else {
				emptyScrollDistance += offset;
			}
		} else if (offset < 0) {
			if (isSlidingMotionLessView && childZero != null && motionLessScrollDistance > 0) {
				if (motionLessScrollDistance + offset < 0) {
					scrollBy(getScrollX(), -motionLessScrollDistance);

					offset += motionLessScrollDistance;
					motionLessScrollDistance = 0;

					childLayout.scrollBy(childLayout.getScrollX(), offset);
					scrollDistance += offset;
				} else {
					scrollBy(getScrollX(), offset);
					motionLessScrollDistance += offset;
				}
			} else {
				childLayout.scrollBy(childLayout.getScrollX(), offset);
				scrollDistance += offset;
			}
		}
	}

	public interface SlidingScrollChangeListener{
		void onScrollChange(int scrollY, int oldScrollY);
	}
}
