package testapp.android.com.echartslearn.view_show.zoom_view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;

import testapp.android.com.echartslearn.R;

public class ZoomViewActivity extends AppCompatActivity{
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_zoom);
		LinearLayout top = findViewById(R.id.zoom_top);
		LinearLayout bottom = findViewById(R.id.zoom_bottom);
		View topView = LayoutInflater.from(this).inflate(R.layout.zoom_view_left, null);
		final View bottomView = LayoutInflater.from(this).inflate(R.layout.zoom_view_left, null);
		top.addView(topView);
		bottom.addView(bottomView);
		ViewGroup.LayoutParams topLP = topView.getLayoutParams();
//		topLP.width = (int) (topView.getMeasuredWidth() * 0.5f);
//		topLP.height = (int) (topView.getMeasuredHeight() * 0.5f);
		topView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
		int width = (int) (topView.getMeasuredWidth() * 0.5f);
		int height = (int) (topView.getMeasuredHeight() * 0.5f);
		Log.d("ZoomViewActivity", "宽是：" + width);
		Log.d("ZoomViewActivity", "高是：" + height);
		topLP.width = width;
		topLP.height = height;
//		topView.setLayoutParams(new ViewGroup.LayoutParams(width, height));

		bottomView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				animation();
				ViewAnimationTool animationTool = new ViewAnimationTool(bottomView)
						.setDurationTime(5000)
						.setScaleX(0.5f)
						.setScaleY(0.5f)
						.setTranslateXStart(0)
						.setTranslateXEnd(200)
						.setTranslateYStart(0)
						.setTranslateYEnd(200);
				animationTool.startAnimation();
			}
		});

	}

	//这里的动画在结束后都会回到初始样式
	private void animation() {
		// 以自身为坐标点   参数： x轴的起始点,结束点   y轴的起始点,结束点   1：不缩放，这是缩放比例概念
		// ScaleAnimation sa = new ScaleAnimation(0,1,0,1);
		// 默认从左上角开始缩放   可以指定位置(在100,50的地方缩放)
		//ScaleAnimation sa = new ScaleAnimation(0,1,0,1,100,50);
//				ScaleAnimation sa = new ScaleAnimation(1, 0.5f, 1, 0.5f,
//						Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//				// 设置动画时长
//				sa.setDuration(2000);
//				// 启动动画
//				v.startAnimation(sa);
	}
}
