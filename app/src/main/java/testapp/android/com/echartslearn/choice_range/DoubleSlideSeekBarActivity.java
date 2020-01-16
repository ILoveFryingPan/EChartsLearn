package testapp.android.com.echartslearn.choice_range;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import testapp.android.com.echartslearn.R;

public class DoubleSlideSeekBarActivity extends AppCompatActivity {

	private DoubleSlideSeekBar mDoubleslideWithrule;
	private DoubleSlideSeekBar mDoubleslideWithoutrule;
	private TextView mTvMinRule;
	private TextView mTvMaxRule;
	private TextView mTvMinWithoutRule;
	private TextView mTvMaxWithoutRule;


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_seekbar_slide);
		initView();
		setListener();
	}

	private void setListener() {
		// 用法
//		mDoubleslideWithrule.setOnRangeListener(new DoubleSlideSeekBar.onRangeListener() {
//			@Override
//			public void onRange(float low, float big) {
////				mTvMinRule.setText("最小值" + String.format("%.0f", low));
////				mTvMaxRule.setText("最大值" + String.format("%.0f", big));
//			}
//		});
		mDoubleslideWithoutrule.setOnRangeListener(new DoubleSlideSeekBar.onRangeListener() {
			@Override
			public void onRange(float low, float big) {
				mTvMinWithoutRule.setText("最小值" + String.format("%.0f", low));
				mTvMaxWithoutRule.setText("最大值" + String.format("%.0f", big));
			}
		});
	}

	private void initView() {
//		mDoubleslideWithrule = (DoubleSlideSeekBar) findViewById(R.id.doubleslide_withrule);
		mDoubleslideWithoutrule = (DoubleSlideSeekBar) findViewById(R.id.doubleslide_withoutrule);
//		mTvMinRule = (TextView) findViewById(R.id.tv_min_rule);
//		mTvMaxRule = (TextView) findViewById(R.id.tv_max_rule);
//		mTvMinWithoutRule = (TextView) findViewById(R.id.tv_min_without_rule);
//		mTvMaxWithoutRule = (TextView) findViewById(R.id.tv_max_without_rule);
	}
}
