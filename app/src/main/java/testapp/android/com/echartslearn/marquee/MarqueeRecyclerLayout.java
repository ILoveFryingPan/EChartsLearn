package testapp.android.com.echartslearn.marquee;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class MarqueeRecyclerLayout extends RecyclerView implements PagingScrollHelper.onPageChangeListener {

    private static final int EMPTY_MESSAGE = 50;
    public static final int MARQUEE_POSITIVE = 0x10;    //正序
    public static final int MARQUEE_REVERSE = 0x20;     //倒序

    private LinearLayoutManager layoutManager = null;
    private LinearLayoutManager hLayoutManager = null;
    private LinearLayoutManager vLayoutManager = null;
    private float animalTimeInterval = 0;
    private long timeInterval = 0;

    private PagingScrollHelper helper = new PagingScrollHelper();
    private int totalNum = 0;
    private int position = -1;
    private int order = MARQUEE_POSITIVE;


    public MarqueeRecyclerLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public MarqueeRecyclerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MarqueeRecyclerLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        helper.setUpRecycleView(this);
        helper.setOnPageChangeListener(this);
    }

    public void setTimeInterval(long timeInterval) {
        this.timeInterval = timeInterval;
        if (timeInterval <= animalTimeInterval + 200) {
            new RuntimeException("timeInterval的值不能小于animalTimeInterval + 200");
        }
    }

    public void setAnimalTimeInterval(float animalTimeInterval) {
        this.animalTimeInterval = animalTimeInterval;
        if (timeInterval <= animalTimeInterval + 200) {
            new RuntimeException("timeInterval的值不能小于animalTimeInterval + 200");
        }
    }

    public void setOrder(int order) {
        if (MARQUEE_POSITIVE != order && MARQUEE_REVERSE != order) {
            throw new RuntimeException("跑马灯的顺序只能是MARQUEE_POSITIVE或者MARQUEE_REVERSE");
        }
        this.order = order;
    }

    public void setLayoutManager(int orientation) {
        if (LinearLayoutManager.HORIZONTAL == orientation) {
            if (null == hLayoutManager) {
                hLayoutManager = new MarqueeLinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            }
            layoutManager = hLayoutManager;
        } else {
            if (null == vLayoutManager) {
                vLayoutManager = new MarqueeLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            }
            layoutManager = vLayoutManager;
        }
        if (null != layoutManager) {
            this.setLayoutManager(layoutManager);
            helper.updateLayoutManger();
        }
    }

    public void start() {
        if (null == getAdapter()) {
            new RuntimeException("适配器Adapter不能为空null");
        } else if (!(getAdapter() instanceof MarqueeAdapter)) {
            new RuntimeException("适配器Adapter必须是MarqueeAdapter的子类");
        } else {
            ((MarqueeAdapter) getAdapter()).setRecyclerLayout(this);
            if (0 >= getAdapter().getItemCount()) {
//                Log.d("MarqueeRecyclerLayout", "跑马灯数据为空，控件隐藏");
                MarqueeRecyclerLayout.this.setVisibility(GONE);
                return;
            } else {
                MarqueeRecyclerLayout.this.setVisibility(VISIBLE);
            }
        }

        if (0 >= timeInterval) {
            new RuntimeException("间隔时间timeInterval不能为0");
        }

        if (null == layoutManager) {
            setLayoutManager(LinearLayoutManager.VERTICAL);
        }

        post(new Runnable() {
            @Override
            public void run() {
                View itemView = getChildAt(0);
                if (null == itemView) {
//                    Log.d("MarqueeRecyclerLayout", "跑马灯数据为空，控件隐藏");
                    MarqueeRecyclerLayout.this.setVisibility(GONE);
                } else {
                    MarqueeRecyclerLayout.this.setVisibility(VISIBLE);
                    ViewGroup.LayoutParams layoutParams = MarqueeRecyclerLayout.this.getLayoutParams();
                    if (layoutManager.equals(hLayoutManager)) {
                        int itemWidth = itemView.getMeasuredWidth();
                        layoutParams.width = itemWidth;
                    } else {
                        int itemHeight = itemView.getMeasuredHeight();
                        layoutParams.height = itemHeight;
                    }
                    MarqueeRecyclerLayout.this.setLayoutParams(layoutParams);

                    post(new Runnable() {
                        @Override
                        public void run() {
                            totalNum = helper.getPageCount();
                            if (0 == totalNum) {
//                                Log.d("MarqueeRecyclerLayout", "跑马灯数据为空，控件隐藏");
                                MarqueeRecyclerLayout.this.setVisibility(GONE);
                                return;
                            } else if (1 < totalNum) {
                                if (-1 == position) {
                                    if (order == MARQUEE_POSITIVE) {
                                        position = 1;
                                    } else {
                                        position = totalNum - 2;
                                    }
                                    scrollToPosition(position);
                                }
                                handler.sendEmptyMessageDelayed(EMPTY_MESSAGE, timeInterval);
                            }
                        }
                    });
                }
            }
        });
    }

    public void end() {
        handler.removeMessages(EMPTY_MESSAGE);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            handler.removeMessages(EMPTY_MESSAGE);
//            Log.d("MarqueeRecyclerLayout", "当前位置：" + position);
            if (1 < totalNum || 0 < timeInterval) {
                if (order == MARQUEE_POSITIVE) {
                    next();
                } else {
                    last();
                }
            }
        }
    };

    private void next() {
        if (position >= totalNum - 1) {
            helper.updateLayoutManger();
            position = 1;
            scrollToPosition(position);
            handler.sendEmptyMessageDelayed(EMPTY_MESSAGE, (long) (timeInterval - animalTimeInterval - 200));
        } else if (position >= totalNum - 2) {
            smoothScrollToPosition(++position);
            handler.sendEmptyMessageDelayed(EMPTY_MESSAGE, (long) (animalTimeInterval + 200));
        } else {
            smoothScrollToPosition(++position);
            handler.sendEmptyMessageDelayed(EMPTY_MESSAGE, timeInterval);
        }
    }

    private void last() {
        if (0 == position) {
            helper.updateLayoutManger();
            position = totalNum - 2;
            scrollToPosition(position);
            handler.sendEmptyMessageDelayed(EMPTY_MESSAGE, (long) (timeInterval - animalTimeInterval - 200));
        } else if (1 == position) {
            smoothScrollToPosition(--position);
            handler.sendEmptyMessageDelayed(EMPTY_MESSAGE, (long) (animalTimeInterval + 200));
        } else {
            smoothScrollToPosition(--position);
            handler.sendEmptyMessageDelayed(EMPTY_MESSAGE, timeInterval);
        }
    }

    @Override
    public void onPageChange(int index) {
        if (order == MARQUEE_POSITIVE) {
            nextPage(index);
        } else {
            lastPage(index);
        }
    }

    private void nextPage(int index) {
        if (-1 > index) {
            position = totalNum + index - 1;
            while (0 > position) {
                position += totalNum - 2;
            }
            if (0 == position) {
                position = totalNum - 2;
                scrollToPosition(position);
            }
        } else {
            position = index + 1;
        }
//        Log.d("MarqueeRecyclerLayout", "index的值：" + index + "   position的值：" + position);
        if (1 < totalNum) {
            if (position >= totalNum - 1) {
                helper.updateLayoutManger();
                position = 1;
                scrollToPosition(position);
            } else if (0 >= position) {
                position = totalNum - 2;
                scrollToPosition(position);
//                Log.d("MarqueeRecyclerLayout", "移动位置：" + position);
            }
        }
    }

    private void lastPage(int index) {
        if (1 < index) {
            position = index;
            while (position > totalNum - 2) {
                position = position - totalNum + 2;
            }

            if (1 == position) {
                scrollToPosition(position);
            }
        } else {
            position = totalNum - 1 + index - 1;
        }
//        Log.d("MarqueeRecyclerLayout", "index的值：" + index + "   position的值：" + position);
        if (1 < totalNum) {
            if (position >= totalNum - 1) {
                position = 1;
                scrollToPosition(position);
//                Log.d("MarqueeRecyclerLayout", "移动位置：" + position);
            } else if (0 >= position) {
                helper.updateLayoutManger();
                position = totalNum - 2;
                scrollToPosition(position);
            }
        }
    }

    class MarqueeLinearLayoutManager extends LinearLayoutManager {

        public MarqueeLinearLayoutManager(Context context) {
            super(context);
        }

        public MarqueeLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        public MarqueeLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        @Override
        public void smoothScrollToPosition(RecyclerView recyclerView, State state, int position) {
//            super.smoothScrollToPosition(recyclerView, state, position);
            LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {
                @Override
                protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                    if (0 >= animalTimeInterval) {
                        return super.calculateSpeedPerPixel(displayMetrics);
                    } else {
                        return animalTimeInterval / displayMetrics.densityDpi;
                    }
                }
            };
            linearSmoothScroller.setTargetPosition(position);
            this.startSmoothScroll(linearSmoothScroller);
        }
    }
}
