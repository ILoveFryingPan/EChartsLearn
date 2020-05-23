package testapp.android.com.echartslearn.swift_list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;

import testapp.android.com.echartslearn.R;

public class SwiftListActivity extends AppCompatActivity {

    private int dip44;
    private int dip50;
    private int dip62;

    private List<String> dataList = new ArrayList<>();

    private SwipeRecyclerView srl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        setContentView(createView());

        for (int i = 0; i < 30; i++) {
            dataList.add("本地数据第" + i + "个");
        }
        srl.setSwipeMenuCreator(menuCreator);
        srl.setOnItemMenuClickListener(menuClickListener);
        srl.setLayoutManager(new LinearLayoutManager(this));
        srl.setAdapter(new TextAdapter());
    }

    private void initData() {
        dip44 = (int) getResources().getDimension(R.dimen.dip_44);
        dip50 = (int) getResources().getDimension(R.dimen.dip_50);
        dip62 = (int) getResources().getDimension(R.dimen.dip_62);
    }

    //创建菜单
    SwipeMenuCreator menuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {
            SwipeMenuItem editMenu = new SwipeMenuItem(SwiftListActivity.this);
            editMenu.setBackgroundColor(0xffff6f28);
            editMenu.setWidth(dip44);
            editMenu.setHeight(dip50);
            editMenu.setText("编辑");
            rightMenu.addMenuItem(editMenu);

            SwipeMenuItem delMenu = new SwipeMenuItem(SwiftListActivity.this);
            delMenu.setBackgroundColor(0xff00ffff);
            delMenu.setWidth(dip44);
            delMenu.setHeight(dip50);
            delMenu.setText("删除");
            rightMenu.addMenuItem(delMenu);
        }
    };

    OnItemMenuClickListener menuClickListener = new OnItemMenuClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge, int adapterPosition) {
            menuBridge.closeMenu();     //官方要求：任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            int direction = menuBridge.getDirection();
            int menuPosition = menuBridge.getPosition();
            Log.d("SwiftListActivity", "direction:" + direction);
            Log.d("SwiftListActivity", "menuPosition:" + menuPosition);
            Log.d("SwiftListActivity", "adapterPosition:" + adapterPosition);
        }
    };

    private View createView() {
        LinearLayout rootLayout = new LinearLayout(this);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        rootLayout.setBackgroundColor(0xffffffff);
        ViewGroup.LayoutParams rootLP = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rootLayout.setLayoutParams(rootLP);

        srl = new SwipeRecyclerView(this);
        LinearLayout.LayoutParams srlLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rootLayout.addView(srl, srlLP);

        return rootLayout;
    }

    class TextAdapter extends RecyclerView.Adapter<TextAdapter.TextViewHolder> {

        @NonNull
        @Override
        public TextViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new TextViewHolder(createItemView());
        }

        @Override
        public void onBindViewHolder(@NonNull TextViewHolder textViewHolder, int i) {
            textViewHolder.itemText.setText(dataList.get(i));
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public class TextViewHolder extends RecyclerView.ViewHolder {

            private final TextView itemText;

            public TextViewHolder(@NonNull View itemView) {
                super(itemView);
                LinearLayout itemRootLayout = (LinearLayout) itemView;
                itemText = (TextView) itemRootLayout.getChildAt(0);
            }
        }

        private View createItemView() {
            LinearLayout itemRootLayout = new LinearLayout(SwiftListActivity.this);
            itemRootLayout.setOrientation(LinearLayout.VERTICAL);
            itemRootLayout.setBackgroundColor(0xffffffff);
            ViewGroup.LayoutParams itemRootLP = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            itemRootLayout.setLayoutParams(itemRootLP);

            TextView itemText = new TextView(SwiftListActivity.this);
            itemText.setTextSize(15);
            itemText.setTextColor(0xff333333);
            itemText.setGravity(Gravity.CENTER_VERTICAL);
            LinearLayout.LayoutParams itemTextLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip50);
            itemRootLayout.addView(itemText, itemTextLP);

            View line = new View(SwiftListActivity.this);
            line.setBackgroundColor(0xffe0e0e0);
            LinearLayout.LayoutParams lineLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
            itemRootLayout.addView(line, lineLP);

            return itemRootLayout;
        }
    }
}
