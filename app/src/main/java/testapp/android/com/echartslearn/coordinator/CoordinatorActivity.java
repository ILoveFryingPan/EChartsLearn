package testapp.android.com.echartslearn.coordinator;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import testapp.android.com.echartslearn.R;

public class CoordinatorActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator);
        RecyclerView rl = findViewById(R.id.coordinator_rl);
        Toolbar toolbar = findViewById(R.id.coordinator_toolbar);

        setSupportActionBar(toolbar);
        rl.setLayoutManager(new LinearLayoutManager(this));
        rl.setAdapter(new TextAdapter(this));
    }

    class TextAdapter extends RecyclerView.Adapter<TextAdapter.TextViewHolder> {

        private Context mContext;

        public TextAdapter(Context mContext) {
            this.mContext = mContext;
        }

        @NonNull
        @Override
        public TextAdapter.TextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new TextViewHolder(createItemView());
        }

        @Override
        public void onBindViewHolder(@NonNull TextAdapter.TextViewHolder holder, int position) {
            holder.itemText.setText("测试数据");
        }

        @Override
        public int getItemCount() {
            return 44;
        }

        public class TextViewHolder extends RecyclerView.ViewHolder {

            TextView itemText;

            public TextViewHolder(View itemView) {
                super(itemView);
                itemText = (TextView) itemView;
            }
        }

        private View createItemView() {
            TextView item = new TextView(mContext);
            item.setTextSize(14);
            item.setTextColor(0xff333333);
            item.setBackgroundColor(0xffffffff);
            item.setGravity(Gravity.CENTER);
            ViewGroup.LayoutParams itemLP = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.dip_50));
            item.setLayoutParams(itemLP);
            return item;
        }
    }
}
