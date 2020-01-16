package testapp.android.com.echartslearn.shade;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import testapp.android.com.echartslearn.R;

public class ShadeActivity extends AppCompatActivity{

	private int dip10;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initData();

		LinearLayout root = new LinearLayout(this);
		root.setOrientation(LinearLayout.VERTICAL);
		root.setBackgroundColor(0xffffffff);
//		root.setBackgroundColor(0xff00ff00);
		root.setGravity(Gravity.CENTER_HORIZONTAL);
		setContentView(root);

		TextView test = createText(root, "测试阴影测试阴影\n测试阴影测试阴影");
		ImageView image = createImage(root, R.mipmap.exer);
		test.setBackgroundResource(R.drawable.back_shape_test_four);
		image.setBackgroundResource(R.drawable.back_shape_test_four);
	}

	private void initData() {
		dip10 = (int) getResources().getDimension(R.dimen.dip_10);
	}

	private TextView createText(LinearLayout layout, String text) {
		TextView textView = new TextView(this);
		textView.setTextColor(0xff333333);
		textView.setTextSize(15);
		textView.setText(text);

		LinearLayout.LayoutParams textLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		textLP.setMargins(dip10, dip10, dip10, dip10);
		layout.addView(textView, textLP);
		return textView;
	}

	private ImageView createImage(LinearLayout layout, int ids) {
		ImageView image = new ImageView(this);
		image.setScaleType(ImageView.ScaleType.CENTER);
		image.setImageResource(ids);
		image.setBackgroundColor(0xffffffff);

		LinearLayout.LayoutParams imageLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		imageLP.setMargins(dip10, dip10, dip10, dip10);
		layout.addView(image, imageLP);
		return image;
	}
}
