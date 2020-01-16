package testapp.android.com.echartslearn.view_show.sliding.suspend_sliding;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;


import java.util.HashMap;
import java.util.Map;

import testapp.android.com.echartslearn.AllReplyClicklistener;

public class MyScrollView extends ScrollView {

	private OnScrollChangedListener scrollViewListener = null;
	private AllReplyClicklistener clicklistener;
	private int scrolY;
	private int childScrollY = -1;

	public int getScrolY() {
		return scrolY;
	}

	public void setScrolY(int scrolY) {
		this.scrolY = scrolY;
	}

	public MyScrollView(Context context) {
		super(context);
	}

	public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setScrollViewListener(OnScrollChangedListener scrollViewListener) {
		this.scrollViewListener = scrollViewListener;
	}

	public void setClicklistener(AllReplyClicklistener clicklistener) {
		this.clicklistener = clicklistener;
	}

	@Override
	protected void onScrollChanged(int x, int y, int oldx, int oldy) {
		super.onScrollChanged(x, y, oldx, oldy);
		setScrolY(y);
		if (scrollViewListener != null) {
			scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		Map<String, Integer> size = new HashMap<>();
		size.put("w", w);
		size.put("h", h);
		size.put("oldw", oldw);
		size.put("oldh", oldh);
		if (clicklistener != null)
			clicklistener.reply(size);
	}

	public interface OnScrollChangedListener {
		void onScrollChanged(View who, int l, int t, int oldl, int oldt);
	}

	public interface OnScrollListener {
		void onScroll(int y);
	}

	@Override
	protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
		super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
		Log.d("MyScrollView", "scrolly:" + scrollY);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		try {
			return super.onTouchEvent(ev);
		} catch (NullPointerException e) {
			e.printStackTrace();
			return true;
		}
	}
}
