package testapp.android.com.echartslearn.view_show.sliding.suspend_sliding;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class SlidingChildLayout extends RelativeLayout{

	private int slidingLayoutTop;
	private int slidingMotionLessTop;

	public void setSlidingLayoutTop(int slidingLayoutTop) {
		this.slidingLayoutTop = slidingLayoutTop;
	}

	public void setSlidingMotionLessTop(int slidingMotionLessTop) {
		this.slidingMotionLessTop = slidingMotionLessTop;
	}

	public SlidingChildLayout(Context context) {
		super(context);
	}

	public SlidingChildLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SlidingChildLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (getChildCount() != 1) {
			throw new RuntimeException("SlidingChildLayout的自控件只能有一个");
		}

		View childZero = getChildAt(0);
		if (childZero != null) {
			childZero.layout(0, slidingLayoutTop, childZero.getMeasuredWidth(), slidingLayoutTop + childZero.getMeasuredHeight());
		}
	}

	private int getChildWidth(int width, int measureWidth) {
		int childWidth = 0;
		switch (width) {
			case LayoutParams.MATCH_PARENT:
				childWidth = getWidth();
				break;
			case LayoutParams.WRAP_CONTENT:
				childWidth = measureWidth;
				break;
			default:
				childWidth = width;
				break;
		}
		return childWidth;
	}

	private int getChildHeight(int height, int measureHeight) {
		int childHeight = 0;
		switch (height) {
			case LayoutParams.MATCH_PARENT:
				childHeight = getHeight();
				break;
			case LayoutParams.WRAP_CONTENT:
				childHeight = measureHeight;
				break;
			default:
				childHeight = height;
				break;
		}
		return childHeight;
	}
}
