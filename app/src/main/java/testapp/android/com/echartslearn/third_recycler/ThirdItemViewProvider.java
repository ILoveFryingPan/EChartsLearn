package testapp.android.com.echartslearn.third_recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shizhefei.view.multitype.ItemViewProvider;

import java.util.Map;

import testapp.android.com.echartslearn.R;

public class ThirdItemViewProvider extends ItemViewProvider {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int providerType) {
        return new TestViewHolder(inflater.inflate(R.layout.provider_test, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, Object o) {
        TestViewHolder holder = (TestViewHolder) viewHolder;
        Map<String, Object> map = (Map<String, Object>) o;
        holder.itemText.setText((CharSequence) map.get("name"));
    }

    class TestViewHolder extends RecyclerView.ViewHolder {

        private final TextView itemText;

        public TestViewHolder(View itemView) {
            super(itemView);
            itemText = itemView.findViewById(R.id.provider_test_item_text);
        }
    }
}
