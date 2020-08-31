package testapp.android.com.echartslearn.filter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;
import java.util.Map;

/**
 * 这个是筛选列表的基类，就是把筛选的控制接口的逻辑写进去，具体的列表样式没有变化
 * @param <VH>
 */
public abstract class FilterBaseAdapter<VH extends FilterBaseAdapter.FilterBaseViewHolder> extends RecyclerView.Adapter<VH> {

    private FilterControl filterControl;
    private List<Map<String, Object>> itemList;

    public FilterBaseAdapter(FilterControl filterControl) {
        this.filterControl = filterControl;
        if (null == filterControl) {
            throw new RuntimeException("筛选的控制接口FilterControl的对象不能为空");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        ((FilterBaseViewHolder) vh).clickListener.setPosition(i);
    }

    class FilterBaseViewHolder extends RecyclerView.ViewHolder {

        private final FilterBaseClickListener clickListener;

        public FilterBaseViewHolder(@NonNull View itemView) {
            super(itemView);
            clickListener = new FilterBaseClickListener();
            itemView.setOnClickListener(clickListener);
        }

        class FilterBaseClickListener implements View.OnClickListener {

            private int position;

            public void setPosition(int position) {
                this.position = position;
            }

            @Override
            public void onClick(View v) {
                filterControl.filterClick(itemList.get(position), itemView);
            }
        }
    }
}
