package testapp.android.com.echartslearn.marquee;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import testapp.android.com.echartslearn.R;

public class MarqueeTestAdapter extends MarqueeAdapter<String>{

    private int dip50;

    public MarqueeTestAdapter(Context mContext, List<String> dataList) {
        super(mContext, dataList);
    }

    @Override
    public void initAdapter() {
        dip50 = (int) getmContext().getResources().getDimension(R.dimen.dip_50);
    }

    @Override
    public void marqueeBindHolder(@NonNull RecyclerView.ViewHolder viewHolder, String itemData) {
        ((TestViewHolder) viewHolder).itemText.setText(itemData);
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(View itemView) {
        return new TestViewHolder(itemView);
    }

    @Override
    public View getItemView() {
        TextView itemText = new TextView(getmContext());
        itemText.setTextSize(15);
        itemText.setTextColor(0xff333333);
        itemText.setGravity(Gravity.CENTER);
        ViewGroup.LayoutParams itemLP = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip50);
        itemText.setLayoutParams(itemLP);

        return itemText;
    }

    class TestViewHolder extends RecyclerView.ViewHolder {

        private TextView itemText;

        public TestViewHolder(@NonNull View itemView) {
            super(itemView);
            itemText = (TextView) itemView;
        }
    }
}
