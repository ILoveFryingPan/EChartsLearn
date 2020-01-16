package testapp.android.com.echartslearn.view_show.text;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.widget.TextView;

//import com.baidu.navisdk.adapter.BNRoutePlanNode;

public class SpannerTextActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        android:autoLink="phone|email|web"
        TextView spannerText = new TextView(this);
        spannerText.setTextSize(20);
        spannerText.getPaint().linkColor = 0xff0000ff;
        setContentView(spannerText);
        SpannableString string = new SpannableString("293742893742837");
        spannerText.setText(string);
//        BNRoutePlanNode df;
    }
}
