package testapp.android.com.echartslearn.third_recycler;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.shizhefei.view.multitype.provider.FragmentData;
import com.shizhefei.view.multitype.provider.FragmentDataProvider;

public class FragmentChildDataProvider extends FragmentDataProvider {

    private FragmentData fragmentData;
    private static int dd = 100;

    public FragmentChildDataProvider(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, FragmentData fragmentData) {
        super.onBindViewHolder(viewHolder, fragmentData);
        this.fragmentData = fragmentData;
        Log.d("TestFragmentDataProvide", "bind了一次");
//        ThirdListFragment fragment = (ThirdListFragment) fragmentData.getFragment();
//        fragment.setName("我的名字");
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder viewHolder) {
        super.onViewAttachedToWindow(viewHolder);
        if (fragmentData != null && fragmentData.getFragment() != null) {
            ThirdListFragment fragment = (ThirdListFragment) fragmentData.getFragment();
            fragment.setName("我的名字" + (dd++));
        }
    }
}
