package testapp.android.com.echartslearn.base_webview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.annotation.Annotation;

import testapp.android.com.echartslearn.R;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public class BaseWebViewActivity extends AppCompatActivity implements OnClickListener{

    private static Context mContext;
    private TextView textShow;
    private WebView baseWeb;
    private Button btnNo;
    private Button btnHas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_webview_base);
        textShow = findViewById(R.id.text_show);
        baseWeb = findViewById(R.id.base_webview);
        btnNo = findViewById(R.id.action_from_native);
        btnHas = findViewById(R.id.action_from_native_with_param);
        setBaseWebOption();
        baseWeb.loadUrl("file:///android_asset/baseWeb/baseWeb.html");
    }

    public void setBaseWebOption(){
        baseWeb.getSettings().setJavaScriptEnabled(true);

        baseWeb.getSettings().setAllowFileAccess(true);

        baseWeb.addJavascriptInterface(BaseWebViewActivity.this, "baseWeb");
    }

    @JavascriptInterface
    public static void textShow(){
//        textShow.setText("我被调用了");
        Toast.makeText(mContext, "我被调用了", Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public static void textShow(String text){
//        textShow.setText(text);
        Toast.makeText(mContext, "接收的文字" + text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.action_from_native:
                baseWeb.loadUrl("javascript:actionFromNative()");
                break;
            case R.id.action_from_native_with_param:
                baseWeb.loadUrl("javascript:actionFromNativeWithParam(" + "'come from Native'" + ")");
                break;
        }
    }
}
