package testapp.android.com.echartslearn.third_recycler;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.shizhefei.view.multitype.ItemBinderFactory;
import com.shizhefei.view.multitype.MultiTypeAdapter;
import com.shizhefei.view.multitype.MultiTypeView;
import com.shizhefei.view.multitype.provider.FragmentData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import testapp.android.com.echartslearn.R;

public class ThirdFragmentOfListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        MultiTypeView multiTypeView = findViewById(R.id.test_rl);
        ItemBinderFactory itemBinderFactory = new ItemBinderFactory();
        itemBinderFactory.registerProvider(FragmentData.class, new FragmentChildDataProvider(getSupportFragmentManager()));
        itemBinderFactory.registerProvider(Map.class, new ThirdItemViewProvider());
        MultiTypeAdapter multiTypeAdapter = new MultiTypeAdapter(loadData(), itemBinderFactory);
        multiTypeView.setAdapter(multiTypeAdapter);
    }

    private List<Object> loadData() {
        List<Object> data = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("name", "阿里");
        map.put("id", 11);
        data.add(map);

        map = new HashMap<>();
        map.put("name", "阿里");
        map.put("id", 11);
        data.add(map);

        map = new HashMap<>();
        map.put("name", "阿里");
        map.put("id", 11);
        data.add(map);

        map = new HashMap<>();
        map.put("name", "阿里");
        map.put("id", 11);
        data.add(map);

        map = new HashMap<>();
        map.put("name", "阿里");
        map.put("id", 11);
        data.add(map);

        map = new HashMap<>();
        map.put("name", "阿里");
        map.put("id", 11);
        data.add(map);

        map = new HashMap<>();
        map.put("name", "阿里");
        map.put("id", 11);
        data.add(map);

        map = new HashMap<>();
        map.put("name", "阿里");
        map.put("id", 11);
        data.add(map);

        map = new HashMap<>();
        map.put("name", "阿里");
        map.put("id", 11);
        data.add(map);

        map = new HashMap<>();
        map.put("name", "阿里");
        map.put("id", 11);
        data.add(map);

        TextView textView = new TextView(this);
        textView.setTextColor(0xff00ffff);
        textView.setTextSize(15);
        textView.setText("我的item");
        data.add(textView);

        data.add(new FragmentData(ThirdListFragment.class, "testFragment"));
        return data;
    }
}
