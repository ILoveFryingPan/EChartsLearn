package testapp.android.com.echartslearn.base_fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import testapp.android.com.echartslearn.R;

public class BaseFragment extends Fragment{
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return createView();
	}

	private View createView() {
		LinearLayout root = new LinearLayout(getActivity());
		root.setBackgroundColor(0xffffffff);
		root.setGravity(Gravity.CENTER);
		root.setOrientation(LinearLayout.VERTICAL);

		TextView oneText = new TextView(getActivity());
		oneText.setTextColor(0xff333333);
		oneText.setTextSize(15);
		oneText.setGravity(Gravity.CENTER);
		oneText.setText("我爱北京天安门");
		LinearLayout.LayoutParams oneLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) getActivity().getResources().getDimension(R.dimen.dip_80));
		oneLP.topMargin = (int) getActivity().getResources().getDimension(R.dimen.dip_20);
		root.addView(oneText, oneLP);

		TextView twoText = new TextView(getActivity());
		twoText.setTextColor(0xff333333);
		twoText.setTextSize(15);
		twoText.setGravity(Gravity.CENTER);
		twoText.setText("我爱北京天安门");
		LinearLayout.LayoutParams twoLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) getActivity().getResources().getDimension(R.dimen.dip_80));
		twoLP.topMargin = (int) getActivity().getResources().getDimension(R.dimen.dip_20);
		root.addView(twoText, twoLP);
		return root;
	}
}
