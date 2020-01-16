package testapp.android.com.echartslearn.marquee;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import testapp.android.com.echartslearn.R;

public class MarqueeActivity extends AppCompatActivity {

    private int dip50;

    private MarqueeRecyclerLayout rl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(createView());
        initData();
    }

    private View createView() {
        rl = new MarqueeRecyclerLayout(this);
        rl.setBackgroundColor(0xffffffff);
        ViewGroup.LayoutParams rlLP = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rl.setLayoutParams(rlLP);

        return rl;
    }

    private void initData() {
        dip50 = (int) getResources().getDimension(R.dimen.dip_50);

        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dataList.add("列表数据：" + i + "号");
        }

        rl.setLayoutManager(LinearLayoutManager.HORIZONTAL);
        rl.setAdapter(new MarqueeTestAdapter(this, dataList));
        rl.setTimeInterval(3000);
        rl.setAnimalTimeInterval(100);
        rl.setOrder(MarqueeRecyclerLayout.MARQUEE_REVERSE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        rl.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        rl.end();
    }

    class NormalAdapter extends RecyclerView.Adapter<NormalAdapter.NormalViewHolder> {

        private Context mContext;
        private List<String> dataList;

        public NormalAdapter(Context mContext) {
            this.mContext = mContext;
        }

        public void setDataList(List<String> dataList) {
            this.dataList = dataList;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public NormalAdapter.NormalViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new NormalViewHolder(createItemView());
        }

        @Override
        public void onBindViewHolder(@NonNull NormalAdapter.NormalViewHolder normalViewHolder, int i) {
            normalViewHolder.itemText.setText(dataList.get(i));
        }

        @Override
        public int getItemCount() {
            return null == dataList ? 0 : dataList.size();
        }

        public class NormalViewHolder extends RecyclerView.ViewHolder {
            private TextView itemText;

            public NormalViewHolder(@NonNull View itemView) {
                super(itemView);
                itemText = (TextView) itemView;
            }
        }

        private View createItemView() {
            TextView itemText = new TextView(mContext);
            itemText.setTextSize(15);
            itemText.setTextColor(0xff333333);
            itemText.setGravity(Gravity.CENTER);
            ViewGroup.LayoutParams itemLP = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip50);
            itemText.setLayoutParams(itemLP);

            return itemText;
        }
    }
}
