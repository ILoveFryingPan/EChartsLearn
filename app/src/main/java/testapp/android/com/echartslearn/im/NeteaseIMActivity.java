package testapp.android.com.echartslearn.im;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.HeaderViewListAdapter;
import android.widget.Toast;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;

import java.util.HashMap;
import java.util.Map;

import testapp.android.com.echartslearn.HttpHelper;
import testapp.android.com.echartslearn.application.MyApp;

public class NeteaseIMActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (TextUtils.isEmpty(MyApp.sessionId)){
//            HttpHelper.login(this, "huitailang", "1234567890", new HttpHelper.LoginListener() {
//                @Override
//                public void onSuccess() {
//                    Toast.makeText(NeteaseIMActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
////                    creatIMId();
//                    LoginInfo info = new LoginInfo("13898194127", MyApp.token);
//                    NIMClient.getService(AuthService.class).login(info)
//                            .setCallback(callback);
//                }
//
//                @Override
//                public void onFailed() {
//
//                }
//            });
//        }else {
////            creatIMId();
//            LoginInfo info = new LoginInfo(MyApp.accid, MyApp.token);
//            NIMClient.getService(AuthService.class).login(info)
//                    .setCallback(callback);
//        }
        //token的值使用密码
        LoginInfo info = new LoginInfo("HuiTaiLang", "1234567890");
        NIMClient.getService(AuthService.class).login(info)
                .setCallback(callback);
    }

    //创建即使通讯账号
    public void creatIMId(){
        Map<String, Object> map = new HashMap<>();
        map.put(HttpHelper.SESSIONID, MyApp.sessionId);

        HttpHelper.httpRequire(this, map, HttpHelper.URL_BASE + "/apiv1/im/createImUser", new HttpHelper.RequestCallback() {
            @Override
            public void doSuccess(Map<String, Object> jsonMap) {
                Map<String, Object> IDMap = (Map<String, Object>) jsonMap.get(HttpHelper.CONTENT);
            }

            @Override
            public void doFail(int result) {
                getToken();
            }
        }, false);
    }

    public void getToken(){
        Map<String, Object> map = new HashMap<>();
        map.put(HttpHelper.SESSIONID, MyApp.sessionId);
        HttpHelper.httpRequire(this, map, HttpHelper.URL_BASE + "/apiv1/im/refreshToken", new HttpHelper.RequestCallback() {
            @Override
            public void doSuccess(Map<String, Object> jsonMap) {
                Map<String, Object> IDMap = (Map<String, Object>) jsonMap.get(HttpHelper.CONTENT);
                MyApp.token = (String) IDMap.get("token");
                MyApp.accid = (String) IDMap.get("accid");
            }

            @Override
            public void doFail(int result) {

            }
        }, false);

    }

    RequestCallback<LoginInfo> callback = new RequestCallback<LoginInfo>() {
        @Override
        public void onSuccess(LoginInfo param) {
            Log.d("NeteaseIMActivity", "登录成功");
            Log.d("NeteaseIMActivity", "account" + param.getAccount());
            Log.d("NeteaseIMActivity", "token" + param.getToken());
            Log.d("NeteaseIMActivity", "appKey" + param.getAppKey());
        }

        @Override
        public void onFailed(int code) {
            Log.d("NeteaseIMActivity", "登录失败" + code);
        }

        @Override
        public void onException(Throwable exception) {
            Log.d("NeteaseIMActivity", "登录异常：" + exception.getMessage());
        }
    };
}
