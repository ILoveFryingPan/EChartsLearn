package testapp.android.com.echartslearn.view_show.pager_page;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

public class OverlapPageTransformer implements ViewPager.PageTransformer {

	private int mOffset = 0;

	public void setmOffset(int mOffset) {
		this.mOffset = mOffset;
	}

	@Override
	public void transformPage(@NonNull View page, float position) {
//		page.setAlpha(0.5f);
//		page.setTranslationX((-page.getWidth() * position));
		float scale = (page.getWidth() - mOffset * (position + 1)) / page.getWidth();
		page.setScaleX(scale);
		page.setScaleY(scale);
		page.setTranslationX(-page.getWidth() * position + mOffset * position);
	}
}
