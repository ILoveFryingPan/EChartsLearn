package testapp.android.com.echartslearn.view_show.zoom_view;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;

import java.util.Timer;

public class ViewAnimationTool {

    private View mView;
    private int animationUnderWay = 0;  //默认值是0，表示动画没开始，1表示动画开始了，不会再次执行

    private int translateXStart;        //控件横向平移的开始位置，一般为控件的当前位置，其值为0
    private int translateXEnd;          //控件横向平移的终止位置
    private float translateXStep;         //控件横向单位时间平移距离
    private int translateYStart;        //控件纵向平移的开始位置，一般为控件的当前位置，其值为0
    private int translateYEnd;          //控件纵向平移的终止位置
    private float translateYStep;         //控件纵向单位时间平移距离
    private long durationTime;           //控件做的一系列动画的时间间隔
    private float scaleX = 1;               //控件横向的缩放比例， 默认是1
    private float scaleXStep;           //控件横向单位时间的缩放比例
    private float scaleY = 1;               //控件纵向的缩放比例， 默认是1
    private float scaleYStep;           //控件纵向单位时间的缩放比例

    public ViewAnimationTool(View mView) {
        this.mView = mView;
        if (null == mView) {
            throw new RuntimeException("进行动画变换的控件不能为空");
        }
    }

    public ViewAnimationTool setTranslateXStart(int translateXStart) {
        this.translateXStart = translateXStart;
        return this;
    }

    public ViewAnimationTool setTranslateXEnd(int translateXEnd) {
        this.translateXEnd = translateXEnd;
        return this;
    }

    public ViewAnimationTool setTranslateYStart(int translateYStart) {
        this.translateYStart = translateYStart;
        return this;
    }

    public ViewAnimationTool setTranslateYEnd(int translateYEnd) {
        this.translateYEnd = translateYEnd;
        return this;
    }

    public ViewAnimationTool setDurationTime(int durationTime) {
        this.durationTime = durationTime;
        if (0 == durationTime) {
            throw new RuntimeException("控件变换所需的时间不能为0");
        }
        return this;
    }

    public ViewAnimationTool setScaleX(float scaleX) {
        this.scaleX = scaleX;
        if (0 > scaleX) {
            throw new RuntimeException("横向缩放比例不能小于0");
        }
        return this;
    }

    public ViewAnimationTool setScaleY(float scaleY) {
        this.scaleY = scaleY;
        if (0 > scaleY) {
            throw new RuntimeException("纵向缩放比例不能小于0");
        }
        return this;
    }

    public void startAnimation() {
        if (1 == animationUnderWay) {
            return;
        }
        animationUnderWay = 1;
        int time = (int) (durationTime / 25);
        final int translateX = translateXEnd - translateXStart;
        int translateY = translateYEnd - translateYStart;
        translateXStep = (float) translateX / time;
        translateYStep = (float) translateY / time;
        scaleXStep = (scaleX - 1) / time;
        scaleYStep = (scaleY - 1) / time;

        Log.d("ViewAnimationTool", "time:" + time + "  translateXStep:" + translateXStep + "  translateYStep:" + translateYStep + "  scaleXStep:" + scaleXStep + "  scaleYStep:" + scaleYStep);

        CountDownTimer timer = new CountDownTimer(durationTime, 25) {
            @Override
            public void onTick(long millisUntilFinished) {
                int time = (int) ((durationTime - millisUntilFinished) / 25);
                mView.setTranslationX(time * translateXStep + translateXStart);
                mView.setTranslationY(time * translateYStep + translateYStart);
                mView.setScaleX(time * scaleXStep + 1);
                mView.setScaleY(time * scaleYStep + 1);
            }

            @Override
            public void onFinish() {
                mView.setTranslationX(ViewAnimationTool.this.translateXEnd);
                mView.setTranslationY(ViewAnimationTool.this.translateYEnd);
                mView.setScaleX(ViewAnimationTool.this.scaleX);
                mView.setScaleY(ViewAnimationTool.this.scaleY);
                animationUnderWay = 0;
            }
        };
        timer.start();
    }
}
