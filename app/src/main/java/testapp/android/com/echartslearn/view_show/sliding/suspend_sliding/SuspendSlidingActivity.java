package testapp.android.com.echartslearn.view_show.sliding.suspend_sliding;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import testapp.android.com.echartslearn.R;

public class SuspendSlidingActivity extends AppCompatActivity implements View.OnClickListener{

	private int dip1;
	private int dip15;
	private int dip50;

	private RelativeLayout suspendRoot;
	private NestedScrollView suspendScroll;
	private View emptyLine;
	private RecyclerView suspendRL;
	private SuspendSlidingLayout suspendSlidingLayout;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sliding_suspend);
		initData();
		initView();
		initData2();
		getData();
	}

	private void initData() {
		dip1 = (int) getResources().getDimension(R.dimen.dip_1);
		dip15 = (int) getResources().getDimension(R.dimen.dip_15);
		dip50 = (int) getResources().getDimension(R.dimen.dip_50);
	}

	private void initView() {
		suspendRoot = findViewById(R.id.suspend_root);
		suspendScroll = findViewById(R.id.suspend_scroll);
		emptyLine = findViewById(R.id.empty_line);
		suspendRL = findViewById(R.id.suspend_rl);
		suspendSlidingLayout = findViewById(R.id.suspend_sliding_layout);

//		findViewById(R.id.motion_less).setOnClickListener(this);
//		findViewById(R.id.sliding_btn).setOnClickListener(this);
	}

	private void initData2() {
		suspendRL.setNestedScrollingEnabled(false);
		suspendRL.setLayoutManager(new LinearLayoutManager(this));

		suspendScroll.getViewTreeObserver().addOnGlobalLayoutListener(layoutListener);
//		suspendSlidingLayout.setSlidingLayoutTop(dip50 * 3);
	}

	ViewTreeObserver.OnGlobalLayoutListener layoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
		@Override
		public void onGlobalLayout() {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
				suspendScroll.getViewTreeObserver().removeOnGlobalLayoutListener(this);
			} else {
				suspendScroll.getViewTreeObserver().removeGlobalOnLayoutListener(this);
			}
			int[] location = new int[2];
			emptyLine.getLocationOnScreen(location);
			suspendSlidingLayout.setSlidingLayoutTop(location[1] + dip1 - getBarHeight());
		}
	};

	private void getData() {
		List<String> itemList = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			itemList.add("列表数据：" + i);
		}
		suspendRL.setAdapter(new TextAdapter(this, itemList));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
//			case R.id.motion_less:
//				suspendSlidingLayout.moveMotionLess(-10);
//				break;
//			case R.id.sliding_btn:
//				suspendSlidingLayout.moveSliding(-10);
//				break;
		}
	}

	private int getBarHeight() {
		int statusBarHeight = 0;
		int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			statusBarHeight = getResources().getDimensionPixelSize(resourceId);
		}
		return statusBarHeight;
	}

	private class TextAdapter extends RecyclerView.Adapter<TextAdapter.TextViewHolder> {

		private Context mContext;
		private List<String> itemList;

		public TextAdapter(Context mContext, List<String> itemList) {
			this.mContext = mContext;
			this.itemList = itemList;
		}

		@NonNull
		@Override
		public TextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			return new TextViewHolder(createItem());
		}

		@Override
		public void onBindViewHolder(@NonNull TextViewHolder holder, int position) {
			holder.itemText.setText(itemList.get(position));
		}

		@Override
		public int getItemCount() {
			return itemList == null? 0 : itemList.size();
		}

		public class TextViewHolder extends RecyclerView.ViewHolder {

			private final TextView itemText;

			public TextViewHolder(View itemView) {
				super(itemView);
				itemText = (TextView) ((ViewGroup) itemView).getChildAt(0);
			}
		}

		private View createItem() {
			RelativeLayout root = new RelativeLayout(mContext);
			root.setBackgroundColor(0xffffffff);
			ViewGroup.LayoutParams rootLP = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,dip50);
			root.setLayoutParams(rootLP);

			TextView text = new TextView(mContext);
			text.setTextSize(15);
			text.setTextColor(0xff333333);
			text.setPadding(dip15, 0, 0, 0);
			RelativeLayout.LayoutParams textLP = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			textLP.addRule(RelativeLayout.CENTER_VERTICAL);
			root.addView(text, textLP);

			View line = new View(mContext);
			line.setBackgroundColor(0xfff5f5f5);
			RelativeLayout.LayoutParams lineLP = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);
			lineLP.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			root.addView(line, lineLP);

			return root;
		}
	}
}
