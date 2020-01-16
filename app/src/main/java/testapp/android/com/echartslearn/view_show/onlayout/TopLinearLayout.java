package testapp.android.com.echartslearn.view_show.onlayout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class TopLinearLayout extends RelativeLayout{

    private int Y;
    private int scrollY;

    public TopLinearLayout(Context context) {
        super(context);
    }

    public TopLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TopLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TopLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Y = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int diff = (int) event.getY();
                scrollY += (Y - diff);
                Y = diff;
                this.scrollTo(0,scrollY);
                Log.d("TopLinearLayout", "scrollY的值：" + scrollY);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
//        return super.onTouchEvent(event);
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.d("TopLinearLayout", "onLayout一直在响应");
        View childTop = getChildAt(0);
        childTop.layout(0, 0 - getMeasuredHeight(), getWidth(), 0);
        View child = getChildAt(1);
        child.layout(child.getLeft(), 0, child.getRight(), child.getMeasuredHeight());
    }
}
