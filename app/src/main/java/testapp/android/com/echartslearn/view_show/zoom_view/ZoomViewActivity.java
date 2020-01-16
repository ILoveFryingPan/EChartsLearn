package testapp.android.com.echartslearn.view_show.zoom_view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
		View bottomView = LayoutInflater.from(this).inflate(R.layout.zoom_view_left, null);
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

	}
}
