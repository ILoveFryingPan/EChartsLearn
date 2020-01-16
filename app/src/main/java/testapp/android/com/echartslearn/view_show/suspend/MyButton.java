package testapp.android.com.echartslearn.view_show.suspend;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import testapp.android.com.echartslearn.R;

public class MyButton extends AbastractDragFloatActionButton {
	public MyButton(Context context) {
		super(context);
	}

	public MyButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

//	@Override
//	public int getLayoutId() {
//		return -1;//拿到你自己定义的悬浮布局
//	}


	@Override
	public View getLayoutView() {
		TextView suspendText = new TextView(getContext());
		suspendText.setText("悬浮悬浮\n悬浮悬浮");
		suspendText.setTextColor(0xff333333);
		suspendText.setTextSize(15);
		suspendText.setId(R.id.suspend_layout_text);
		RelativeLayout.LayoutParams susLP = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//		suspendText.setLayoutParams(susLP);
		return suspendText;
	}

	@Override
	public void renderView(View view) {
		//初始化那些布局
		addView(view);

//		view.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Toast.makeText(getContext(), "点击了悬浮的按钮", Toast.LENGTH_SHORT).show();
//			}
//		});
	}
}

