package testapp.android.com.echartslearn.view_show.suspend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SuspendActivity extends AppCompatActivity{
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout root = new LinearLayout(this);
		root.setOrientation(LinearLayout.HORIZONTAL);
		root.setBackgroundColor(0xffffffff);
//		ViewGroup.LayoutParams rootLP = new ViewGroup.LayoutParams(400, 400);
//		root.setLayoutParams(rootLP);

		MyButton button = new MyButton(this);
		LinearLayout.LayoutParams buttonLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		root.addView(button);
		setContentView(root);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(SuspendActivity.this, "点击了悬浮的按钮aaaaaa", Toast.LENGTH_SHORT).show();
			}
		});

		View view = button.getChildAt(0);
		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(SuspendActivity.this, "子控件点击事件", Toast.LENGTH_SHORT).show();
			}
		});
	}
}
