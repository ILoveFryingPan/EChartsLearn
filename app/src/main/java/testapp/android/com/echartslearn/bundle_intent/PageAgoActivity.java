package testapp.android.com.echartslearn.bundle_intent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class PageAgoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = createView();
        setContentView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle mBundle = new Bundle();
                mBundle.putString("data1", "message0");
                mBundle.putString("data2", "message1");
                mBundle.putString("data3", "message2");
                Intent mIntent = new Intent();
                mIntent.putExtra("data4", "message3");
                mIntent.putExtra("data5", "message4");
                mIntent.putExtra("data6", "message5");
                Intent intent = new Intent(PageAgoActivity.this, PageAfterActivity.class);
                intent.putExtras(mBundle);
                intent.putExtras(mIntent);
                startActivity(intent);
            }
        });
    }

    private View createView() {
        TextView textView = new TextView(this);
        textView.setText("跳转传值");
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(0xff000000);
        textView.setTextSize(30);
        return textView;
    }
}
