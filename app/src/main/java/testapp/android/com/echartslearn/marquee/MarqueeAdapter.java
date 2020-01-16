package testapp.android.com.echartslearn.marquee;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class MarqueeAdapter<T> extends RecyclerView.Adapter {

    private Context mContext;
    private List<T> dataList;

    private MarqueeRecyclerLayout recyclerLayout;

    public MarqueeAdapter(Context mContext) {
        this.mContext = mContext;
        initAdapter();
    }

    public MarqueeAdapter(Context mContext, List<T> dataList) {
        this.dataList = new ArrayList<>();
        this.mContext = mContext;
        this.dataList.addAll(dataList);
        if (1 < dataList.size()) {
            this.dataList.add(this.dataList.get(0));
            this.dataList.add(0, this.dataList.get(this.dataList.size() - 2));
        }
        initAdapter();
    }

    public Context getmContext() {
        return mContext;
    }

    public void initAdapter() {

    }

    public void setDataList(List<T> dataList) {
        if (null == dataList) {
            return;
        }
        if (null == this.dataList) {
            this.dataList = new ArrayList<>();
        } else {
            this.dataList.clear();
        }
        this.dataList.addAll(dataList);
        if (1 < dataList.size()) {
            this.dataList.add(this.dataList.get(0));
            this.dataList.add(0, this.dataList.get(this.dataList.size() - 2));
        }
        recyclerLayout.end();
        recyclerLayout.start();
    }

    public void setRecyclerLayout(MarqueeRecyclerLayout recyclerLayout) {
        this.recyclerLayout = recyclerLayout;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;
        if (-1 != getLayoutId()) {
            view = LayoutInflater.from(mContext).inflate(getLayoutId(), viewGroup, false);
        } else {
            view = getItemView();
        }
        return getViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        marqueeBindHolder(viewHolder, dataList.get(i));
    }

    public @LayoutRes int getLayoutId() {
        return -1;
    }

    public View getItemView() {
        return null;
    }

    public abstract void marqueeBindHolder(@NonNull RecyclerView.ViewHolder viewHolder, T itemData);

    public abstract RecyclerView.ViewHolder getViewHolder(View itemView);

    @Override
    public int getItemCount() {
        return null == dataList ? 0 : dataList.size();
    }
}
