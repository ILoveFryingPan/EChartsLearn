package testapp.android.com.echartslearn.view_show.text;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import testapp.android.com.echartslearn.R;

public class TestTypefaceActivity extends AppCompatActivity{
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout root = new LinearLayout(this);
		root.setBackgroundColor(0xffffffff);
		root.setOrientation(LinearLayout.VERTICAL);
		root.setGravity(Gravity.CENTER);
		createText(root);
		TextView typeText = createText(root);
		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/huakangshaonvti.ttf");
		typeText.setTypeface(tf);
		setContentView(R.layout.activity_typeface_test);
		TextView textView = findViewById(R.id.test_typeface);
		textView.setTypeface(tf);
	}

	private TextView createText(LinearLayout root) {
		TextView textView = new TextView(this);
		textView.setTextColor(0xff333333);
		textView.setTextSize(30);
		textView.setText("华康少女体");
		textView.setGravity(Gravity.CENTER);
		LinearLayout.LayoutParams textLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) getResources().getDimension(R.dimen.dip_50));
		textLP.topMargin = (int) getResources().getDimension(R.dimen.dip_15);
		root.addView(textView, textLP);
		return textView;
	}
}
