package testapp.android.com.echartslearn.echartline;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import testapp.android.com.echartslearn.R;

public class EchartLineActivity extends AppCompatActivity{

    private WebView lineView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_echart);
        lineView = findViewById(R.id.echart_line_webview);
        lineView.getSettings().setAllowFileAccess(true);
        lineView.getSettings().setJavaScriptEnabled(true);
        lineView.loadUrl("file:///android_asset/eCharts/myechart.html");
        Button mButton = findViewById(R.id.create_line);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lineView.loadUrl("javascript:createBarLineChart();");
            }
        });
    }

//    private void setWebView(){
//        //设置编码
//        lineView.getSettings().setDefaultTextEncodingName("utf-8");
//        //支持js
//        lineView.getSettings().setJavaScriptEnabled(true);
//
//        //设置背景颜色 透明
//        lineView.setBackgroundColor(Color.argb(0, 0, 0, 0));
//
//        //载入js
////        mWebView.loadUrl("file:///android_asset/echarts-kline.html");
////        lineView.loadUrl("file:///android_asset/candlestick33.html");
//
////        lineView.loadUrl("javascript:jsFunction('" + jsParam + "')");
//    }
//
//    private void loadFinish(){
//        lineView.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                //这里的这行代码是需要的
////                lineView.loadUrl("javascript:jsFunction('" + jsParam+ "')");
//                super.onPageFinished(view, url);
//            }
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//        });
//    }
}
