package testapp.android.com.echartslearn;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Movie;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import testapp.android.com.echartslearn.application.MyApp;
import testapp.android.com.echartslearn.view_show.image.MyGifView;

public class HttpHelper {
    //public static final String URL_BASE = "http://10.7.0.61:8081";
    public static final String SESSIONID = "sessionId";
    public static final String CONTENT = "content";
    public static final String URL_BASE = "http://10.7.0.70";

    public HttpHelper(Context context, String url, Map<String, Object> param, String mothod) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        if (param != null && param.size() != 0) {
            Iterator<String> it = param.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                if (param.get(key) == null) {
                    continue;
                }
                params.add(key, param.get(key).toString());
            }
        }

        if (url == null) {
            return;
        }

        if ("post".equals(mothod)) {
            client.post(context, url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                    doSuccess(responseBody);
                }

                @Override
                public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                    doFail(responseBody);

                }
            });
        } else {
            client.get(context, url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                    doSuccess(responseBody);
                }

                @Override
                public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                    doFail(responseBody);
                }
            });
        }
    }

    public void doSuccess(byte[] response) {

    }

    public void doFail(byte[] response) {

    }

    private final static String[] strDigits = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public static void httpRequire(final Context context, Map<String, Object> param, final String url,
                                   final RequestCallback callback, final boolean showDialog) {
        httpRequire(context, param, url, callback, showDialog, "post");
    }

    public static void httpRequire(final Context context, Map<String, Object> param, final String url,
                                   final RequestCallback callback, final boolean showDialog, String method) {
//        final Dialog progress = PromptHelper.createLoadingDialog(context, "");
//        if (showDialog) {
//            progress.show();
//        }
        new HttpHelper(context, url, param, method) {
            @Override
            public void doSuccess(byte[] response) {
                if (showDialog) {
                    try {
//                        progress.cancel();
                    } catch (Exception e) {
                    }
                }
                String s = url;
                String jsonString = new String(response);
                Log.d("RequestHelper", "给iOS的字符串：" + jsonString);
                if (TextUtils.isEmpty(jsonString)) {
                    callback.doFail(0);
                    return;
                }
                Gson g = new Gson();
                Map<String, Object> jsonMapTmp = null;
                try {
                    jsonMapTmp = g.fromJson(jsonString, new TypeToken<Map<String, Object>>() {
                    }.getType());
                } catch (IllegalStateException e) {
                    callback.doFail(0);
                    return;
                } catch (JsonSyntaxException e) {
                    callback.doFail(0);
                    return;
                }
                //Logger.e(RequestHelper.class, "jsonMapTmp-->" + jsonMapTmp.toString());
                Integer status;
                try {
                    status = (int) Math.floor((Double) jsonMapTmp.get("status"));
                } catch (Exception e) {
                    try {
                        status = (int) Math.floor((Double) jsonMapTmp.get("resstate"));
                    } catch (Exception e1) {
                        status = 0;
                    }
                }
                if (status == 1) {
                    callback.doSuccess(jsonMapTmp);
                } else if (status == -1) {
//                    PromptHelper.displayAccountOccuptied(context);
                } else {
                    callback.doFail(0);
                    String msg = jsonMapTmp.get("msg") + "";
                    if (!TextUtils.isEmpty(msg) && !msg.contains("Exception")
                            && !msg.contains("session")) {
//                        if ("验证码错误".equals(msg) && context instanceof LoginActivity) {
//                            msg = "密码错误";
//                        }
//                        PromptHelper.displayMessage(context, msg);
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void doFail(byte[] response) {
                if (showDialog) {
                    try {
//                        progress.cancel();
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                }
                String failStr = new String(response);
                Log.d("HttpHelper", "失败原因：" + failStr);
//                PromptHelper.displayMessage(context, context.getString(R.string.net_error));
                callback.doFail(0);
            }
        };
    }

    public interface RequestCallback {
        void doSuccess(Map<String, Object> jsonMap);

        void doFail(int result);
    }

    public static void md5login(final Activity context, final String userName, final String MD5pwd,
                                final LoginListener listener, boolean showDialog) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("telNumber", userName);
        param.put("pwd", MD5pwd);
        param.put("loginWay", "3");
        param.put("loginVerion", Build.BRAND + "|" + Build.MODEL);
        param.put("memberType", "2");
        HttpHelper.httpRequire(context, param, URL_BASE + "/apiv1/base/login", new HttpHelper.RequestCallback() {
            @Override
            public void doSuccess(Map<String, Object> jsonMap) {

                Map<String, Object> map = (Map<String, Object>) jsonMap.get("content");
                MyApp.sessionId = (String) map.get("sessionId");
                MyApp.accid = (String) map.get("imAccid");
                MyApp.token = (String) map.get("imToken");
                listener.onSuccess();
//                handleLoginResult(context,jsonMap,userName,MD5pwd,listener);
            }

            @Override
            public void doFail(int result) {
                listener.onFailed();
            }
        }, showDialog);
    }

    public static void getGifImage(Context mContext, final String url, final MyGifView gifView) {
        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (200 == msg.what) {
//                    InputStream inputStream = (InputStream) msg.obj;
//                    try {
//                        Log.d("HttpHelper", "inputStream.available():" + inputStream.available());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    Movie movie = Movie.decodeStream(inputStream);
                    Movie movie = (Movie) msg.obj;
                    gifView.setMovie(movie);
                }
                return false;
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream inputStream = null;
                HttpClient httpClient = new AsyncHttpClient().getHttpClient();
                HttpGet httpGet = new HttpGet(url);
                try {
                    HttpResponse response = httpClient.execute(httpGet);
                    int code = response.getStatusLine().getStatusCode();
                    if (200 == code) {
                        HttpEntity entity = response.getEntity();
                        inputStream = entity.getContent();
                        Message message = new Message();
                        message.what = 200;
                        Log.d("HttpHelper", "inputStream.available():" + inputStream.available());
                        Movie movie = Movie.decodeStream(inputStream);
                        message.obj = movie;
                        handler.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public static void login(Activity context, String userName, String pwd, LoginListener listener) {
        if (TextUtils.isEmpty(userName) || !isMobileNO(userName)) {
//            PromptHelper.displayMessage(context, R.string.mobile_number_invalid);
            listener.onFailed();
            return;
        } else if (TextUtils.isEmpty(pwd) || pwd.length() < 6 || pwd.length() > 20) {
//            PromptHelper.displayMessage(context, "密码长度6-20位，必须由数字或字母组成");
            return;
        }
        String MD5pwd = GetMD5Code(pwd).toUpperCase();
        md5login(context, userName, MD5pwd, listener, true);
    }

    public interface LoginListener {
        void onSuccess();

        void onFailed();
    }

    public static boolean isMobileNO(String mobiles) {
        String strPattern = "^1\\d{10}$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static String GetMD5Code(String strObj) {
        String resultString = null;
        try {
            resultString = new String(strObj);
            MessageDigest md = MessageDigest.getInstance("MD5");
            // md.digest() �ú����ֵΪ��Ź�ϣֵ����byte����
            resultString = byteToString(md.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return resultString;
    }

    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        // System.out.println("iRet="+iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }
}
