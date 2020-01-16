package testapp.android.com.echartslearn.base_webview;

import android.util.Log;
import android.webkit.JavascriptInterface;

public class JavaScript {
    @JavascriptInterface
    public static void aa(){
        Log.d("JavaScript", "我是静态方法");
    }
}
