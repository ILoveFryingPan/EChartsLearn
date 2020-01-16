package testapp.android.com.echartslearn.bundle_intent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PageAfterActivity extends AppCompatActivity {

    private TextView textText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(createView());
        Intent intent = getIntent();
        Bundle mBundle = intent.getExtras();
        String data1 = mBundle.getString("data1");
        String data2 = mBundle.getString("data2");
        String data3 = mBundle.getString("data3");
        String data4 = mBundle.getString("data4");
        String data5 = mBundle.getString("data5");
        String data6 = mBundle.getString("data6");
        textText.setText(data1 + "\n"
        + data2 + "\n"
        + data3 + "\n"
        + data4 + "\n"
        + data5 + "\n"
        + data6);
    }

    private View createView() {
        LinearLayout root = new LinearLayout(this);
        root.setBackgroundColor(0xfff0f0f0);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER);

        TextView titleText = new TextView(this);
        titleText.setTextSize(30);
        titleText.setTextColor(0xff000000);
        titleText.setText("接收到的数据：");
        titleText.setBackgroundColor(0xffffffff);
        LinearLayout.LayoutParams titleLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        titleLP.leftMargin = 100;
        titleLP.rightMargin = 100;
        root.addView(titleText, titleLP);

        textText = new TextView(this);
        textText.setTextSize(30);
        textText.setTextColor(0xff000000);
        textText.setBackgroundColor(0xffffffff);
        LinearLayout.LayoutParams textLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textLP.leftMargin = 100;
        textLP.rightMargin = 100;
        textLP.topMargin = 200;
        root.addView(textText, textLP);

        return root;
    }
}
