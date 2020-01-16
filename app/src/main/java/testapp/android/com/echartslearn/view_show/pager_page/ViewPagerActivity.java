package testapp.android.com.echartslearn.view_show.pager_page;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import testapp.android.com.echartslearn.R;

public class ViewPagerActivity extends AppCompatActivity {

	private ViewPager vp;
	private int dip30;
	private int dip80;
	private int dip250;
	private int dip300;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(createView());

		vp.setAdapter(new ImagePager());
		vp.setOffscreenPageLimit(2);
		OverlapPageTransformer transformer = new OverlapPageTransformer();
		transformer.setmOffset(dip80);
		vp.setPageTransformer(true, transformer);
	}

	private View createView() {

		dip30 = (int) getResources().getDimension(R.dimen.dip_30);
		dip80 = (int) getResources().getDimension(R.dimen.dip_80);
		dip250 = (int) getResources().getDimension(R.dimen.dip_250);
		dip300 = (int) getResources().getDimension(R.dimen.dip_300);

		LinearLayout root = new LinearLayout(this);
		root.setOrientation(LinearLayout.VERTICAL);
		root.setBackgroundColor(0xff00ffff);
		root.setGravity(Gravity.CENTER);

		vp = new ViewPager(this);
		LinearLayout.LayoutParams vpLP = new LinearLayout.LayoutParams(dip300, dip300);
		vpLP.topMargin = dip30;
		root.addView(vp, vpLP);

		return root;
	}

	class ImagePager extends PagerAdapter {
		private int[] imageIds = new int[]{R.mipmap.one, R.mipmap.two, R.mipmap.three, R.mipmap.four, R.mipmap.five,
				R.mipmap.six, R.mipmap.seven, R.mipmap.eight, R.mipmap.nine, R.mipmap.ten};

		@Override
		public int getCount() {
			return imageIds == null? 0 : imageIds.length;
		}

		@Override
		public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
			return view == object;
		}

		@Override
		public float getPageWidth(int position) {
			return 1f;
		}

		@NonNull
		@Override
		public Object instantiateItem(@NonNull ViewGroup container, int position) {
			ImageView image = new ImageView(ViewPagerActivity.this);
			image.setScaleType(ImageView.ScaleType.CENTER_CROP);
			image.setImageResource(imageIds[position]);
			ViewGroup.LayoutParams imageLP = new ViewGroup.LayoutParams(dip250, dip250);
//			ViewGroup.LayoutParams imageLP = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//			image.setLayoutParams(imageLP);
			container.addView(image);
			return image;
		}

		@Override
		public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
			container.removeView((View) object);
		}
	}
}
