package com.example.zxing.qrcode;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zxing.R;

public class AuthorizationLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private int dip5;
    private int dip13;
    private int dip22;
    private int dip19;
    private int dip45;
    private int dip50;
    private int dip108;
    private int dip270;

    private TextView enterText;
    private TextView cancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        setContentView(createView());
    }

    private void initData() {
        dip5 = (int) getResources().getDimension(R.dimen.dip_5);
        dip13 = (int) getResources().getDimension(R.dimen.dip_13);
        dip22 = (int) getResources().getDimension(R.dimen.dip_22);
        dip19 = (int) getResources().getDimension(R.dimen.dip_19);
        dip45 = (int) getResources().getDimension(R.dimen.dip_45);
        dip50 = (int) getResources().getDimension(R.dimen.dip_50);
        dip108 = (int) getResources().getDimension(R.dimen.dip_108);
        dip270 = (int) getResources().getDimension(R.dimen.dip_270);
    }

    private View createView() {
        RelativeLayout rootLayout = new RelativeLayout(this);
        rootLayout.setBackgroundColor(0xffffffff);
        ViewGroup.LayoutParams rootLP = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rootLayout.setLayoutParams(rootLP);

        LinearLayout bottomLayout = new LinearLayout(this);
        bottomLayout.setOrientation(LinearLayout.VERTICAL);
        bottomLayout.setId(ToolHelper.generateViewId());
        RelativeLayout.LayoutParams bottomLayoutLP = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        bottomLayoutLP.topMargin = dip50;
        bottomLayoutLP.bottomMargin = dip50;
        bottomLayoutLP.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        bottomLayoutLP.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        rootLayout.addView(bottomLayout, bottomLayoutLP);

        enterText = new TextView(this);
        enterText.setTextColor(0xffffffff);
        enterText.setTextSize(16);
        enterText.setGravity(Gravity.CENTER);
        enterText.setText("确定登录");
        enterText.setBackgroundResource(R.drawable.back_corner_solidff6f28);
        LinearLayout.LayoutParams enterTextLP = new LinearLayout.LayoutParams(dip270, dip45);
        bottomLayout.addView(enterText, enterTextLP);

        cancel = new TextView(this);
        cancel.setTextColor(0xff919496);
        cancel.setTextSize(14);
        cancel.setGravity(Gravity.CENTER);
        cancel.setText("取消登录");
        cancel.setPadding(0, dip13, 0, dip13);
        LinearLayout.LayoutParams cancelLP = new LinearLayout.LayoutParams(dip270, ViewGroup.LayoutParams.WRAP_CONTENT);
        bottomLayout.addView(cancel, cancelLP);

        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setGravity(Gravity.CENTER);
        RelativeLayout.LayoutParams mainLayoutLP = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mainLayoutLP.addRule(RelativeLayout.CENTER_HORIZONTAL);
        mainLayoutLP.addRule(RelativeLayout.ABOVE, bottomLayout.getId());
        rootLayout.addView(mainLayout, mainLayoutLP);

        ImageView mainImage = new ImageView(this);
        mainImage.setScaleType(ImageView.ScaleType.CENTER);
        mainImage.setImageResource(R.mipmap.page_smcg_img_cpt);
        LinearLayout.LayoutParams mainImageLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mainLayout.addView(mainImage, mainImageLP);

        TextView markTop = new TextView(this);
        markTop.setTextSize(15);
        markTop.setTextColor(0xff000000);
        markTop.setText("即将在电脑上登录");
        LinearLayout.LayoutParams markTopLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        markTopLP.topMargin = dip19;
        mainLayout.addView(markTop, markTopLP);

        TextView markBottom = new TextView(this);
        markBottom.setTextSize(12);
        markBottom.setTextColor(0xffb3b3b3);
        markBottom.setText("请确定是否本人操作");
        LinearLayout.LayoutParams markBottomLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        markBottomLP.topMargin = dip5;
        mainLayout.addView(markBottom, markBottomLP);

        return rootLayout;
    }

    @Override
    public void onClick(View v) {
        if (v.equals(enterText)) {

        } else if (v.equals(cancel)){

        }
    }
}
