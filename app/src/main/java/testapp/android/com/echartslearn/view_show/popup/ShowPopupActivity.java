package testapp.android.com.echartslearn.view_show.popup;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import testapp.android.com.echartslearn.R;

public class ShowPopupActivity extends AppCompatActivity implements View.OnClickListener {

    private int dip15;
    private int dip200;

    private LinearLayout rootLayout;
    private View line;

    private List<View> views = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();

        setContentView(createBaseView());

        createButton("show_popup");
    }

    private void initData() {
        dip15 = (int) getResources().getDimension(R.dimen.dip_15);
        dip200 = (int) getResources().getDimension(R.dimen.dip_200);
    }

    private View createBaseView() {
        RelativeLayout viewRoot = new RelativeLayout(this);
        viewRoot.setBackgroundColor(0xffffffff);
        ViewGroup.LayoutParams viewRootLP = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        viewRoot.setLayoutParams(viewRootLP);

        ScrollView scrollView = new ScrollView(this);
        scrollView.setBackgroundColor(0xffffffff);
        scrollView.setFillViewport(true);
        RelativeLayout.LayoutParams scrollLP = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        viewRoot.addView(scrollView, scrollLP);

        rootLayout = new LinearLayout(this);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        rootLayout.setGravity(Gravity.CENTER);
        ViewGroup.LayoutParams rootLP = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        scrollView.addView(rootLayout, rootLP);

        line = new View(this);
        RelativeLayout.LayoutParams lineLP = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
        lineLP.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        viewRoot.addView(line, lineLP);

        return viewRoot;
    }

    private void createButton(String text) {
        Button btn = new Button(this);
        btn.setTextSize(15);
        btn.setTextColor(0xff333333);
        btn.setText(text);
        views.add(btn);
        btn.setOnClickListener(this);
        LinearLayout.LayoutParams btnLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        btnLP.topMargin = dip15;
        rootLayout.addView(btn, btnLP);
    }

    @Override
    public void onClick(View v) {
        int i = 0;
        for (; i < views.size(); i++) {
            if (v.equals(views.get(i))) {
                break;
            }
        }
        if (i >= views.size()) {
            return;
        }

        switch (i) {
            case 0:
                showPopup();
                break;
        }
    }

    private void showPopup() {
        LinearLayout popupRootLayout = new LinearLayout(this);
        popupRootLayout.setOrientation(LinearLayout.VERTICAL);
        popupRootLayout.setBackgroundColor(0xffff00ff);
        popupRootLayout.setGravity(Gravity.CENTER);
        ViewGroup.LayoutParams popupRootLP = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupRootLayout.setLayoutParams(popupRootLP);

        TextView popupText = new TextView(this);
        popupText.setTextSize(15);
        popupText.setTextColor(0xff00ffff);
        popupText.setText("Hello World!!");
        LinearLayout.LayoutParams popupTextLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupRootLayout.addView(popupText, popupTextLP);

        int width;
        int heights;
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();//屏幕宽度
        int height = wm.getDefaultDisplay().getHeight();

        PopupWindow window = new PopupWindow(popupRootLayout, width, dip200);
        window.setFocusable(false);
        window.setOutsideTouchable(false);
        window.setAnimationStyle(R.style.popwin_anim_bottom_up);
        window.showAtLocation(line, Gravity.BOTTOM, 0, 0);

        backgroundAlpha(0.5f);

        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1);
            }
        });
    }

    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }
}
