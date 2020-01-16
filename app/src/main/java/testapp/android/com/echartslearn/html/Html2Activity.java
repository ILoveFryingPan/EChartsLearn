package testapp.android.com.echartslearn.html;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import testapp.android.com.echartslearn.R;

public class Html2Activity extends AppCompatActivity{

    private WebView mWebView;
    private Button androidCallJSBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html_2);
        initView();
        fuwenbenShow();
    }

    private void initView() {

        mWebView = (WebView) findViewById(R.id.webview);
        WebSettings mWebSettings = mWebView.getSettings();

        mWebSettings.setJavaScriptEnabled(true);   //加上这句话才能使用javascript方法
		mWebSettings.setAllowFileAccess(true);

        mWebView.addJavascriptInterface(new Object() {//增加接口方法,让html页面调用
            @JavascriptInterface
            public void callJavaMethod() {
                Toast.makeText(getApplicationContext(), "JS调用Android成功", Toast.LENGTH_LONG).show();
            }

        }, "index");
        mWebView.loadUrl("file:///android_asset/index.html");  //加载页面

        androidCallJSBtn = (Button) findViewById(R.id.androidCallJSBtn);
        androidCallJSBtn.setOnClickListener(new Button.OnClickListener() {  //给button添加事件响应,执行JavaScript的fillContent()方法
            public void onClick(View v) {
                mWebView.loadUrl("javascript:callJavaScriptMethod()");
            }
        });

        Button openFangxiaoer = findViewById(R.id.open_fangxiaoer);
        openFangxiaoer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				Intent intent = getPackageManager().getLaunchIntentForPackage("com.jiaheng.fangxiaoer_public");
//				Intent intent = new Intent();
//				intent.setData(Uri.parse("xiaoer_public://splash"));
//				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				startActivity(intent);


				Intent intent = new Intent();
				intent.setAction("android.intent.action.VIEW");
				intent.addCategory("android.intent.category.DEFAULT");
				intent.addCategory("android.intent.category.BROWSABLE");
				intent.setData(Uri.parse("xiaoer_public://splash/open"));
				intent.setPackage("com.jiaheng.fangxiaoer_public");
				Log.d("Html2Activity","URL : "+intent.toUri(Intent.URI_ANDROID_APP_SCHEME));
				startActivity(intent);
			}
		});
    }

    private void fuwenbenShow() {
        TextView fuwenbenShow = findViewById(R.id.fuwenben_text);
        fuwenbenShow.setText(Html.fromHtml("<div>" +
                "<span style=\"background:#ff0000;\">" +
                "<font color=\"#00ffff\" size=\"5\">我爱平底锅</font>" +
                "<font size=\"20\" style=\"background:#0000ff;\">我爱北京天安门</font>" +
                "<span style=\"background:#00ff00;\"><size-60>我爱平底锅</size-60></span>" +
                "<font color=\"#ffff00\"><size-30>最爱平底锅</size-30></font>" +
                "</span>" +
                "</div>", null, new SizeLabel(this,30)));
    }
}
