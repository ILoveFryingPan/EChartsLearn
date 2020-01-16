package testapp.android.com.echartslearn.third_recycler;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ThirdListFragment extends Fragment {

    private String name;
    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return createView();
    }

    public void setName(String name) {
        this.name = name;
        if (textView != null) {
            textView.setText(name);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!TextUtils.isEmpty(name)) {
            textView.setText(name);
        }
    }

    private View createView() {
        textView = new TextView(getActivity());
        textView.setTextColor(0xff333333);
        textView.setTextSize(30);
        if (!TextUtils.isEmpty(name)) {
            textView.setText(name);
        } else {
            textView.setText("test_Fragment");
        }
        return textView;
    }
}
