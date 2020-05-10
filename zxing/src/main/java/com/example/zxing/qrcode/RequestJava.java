package com.example.zxing.qrcode;

import android.app.Activity;
import android.content.Context;
import android.os.Build;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RequestJava {

    public static void main(String[] args) {
        requestData();
    }

    public static void requestData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 5000;
                while (i > 0) {
                    //这里写网络请求
                    testMethod();
                    i--;
                }
            }
        }).start();
    }

    public static void httpRequire(final Context mContext, final String url, final Map<String, Object> param, final AllReplyClicklistener clicklistener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String requestInfo = requestHttp(url, param);
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (null != clicklistener) {
                            clicklistener.reply(requestInfo);
                        }
                    }
                });
            }
        }).start();

    }

    public static void testMethod() {
        String base = "http://192.168.6.253:8083";

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("telNumber", "13940550491");
        param.put("pwd", getMD5Code("111111"));
        param.put("loginWay", "3");
        param.put("loginVerion", Build.BRAND + "|" + Build.MODEL);
        param.put("memberType", "2");
        String loginInfo = requestHttp(base + "/apiv1/base/login", param);
        writeFile("\n" + System.currentTimeMillis() + "    " + loginInfo);
        JsonParser parser = new JsonParser();
        JsonObject rootJson = (JsonObject) parser.parse(loginInfo);
        JsonObject contentJson = rootJson.getAsJsonObject("content");
        String sessionId = contentJson.get("sessionId").getAsString();

        Map<String, Object> count = new HashMap<>();
        count.put("sessionId", sessionId);

        String unlookInfo = requestHttp(base + "/apiv1/other/getDemandCountNumber", count);
        writeFile("\n" + System.currentTimeMillis() + "     " + unlookInfo);

        unlookInfo = requestHttp(base + "/apiv1/other/getPersonHouseCountNumber", count);
        writeFile("\n" + System.currentTimeMillis() + "     " + unlookInfo);

        unlookInfo = requestHttp(base + "/apiv1/other/getAskInfoCountNumber", count);
        writeFile("\n" + System.currentTimeMillis() + "     " + unlookInfo);

        unlookInfo = requestHttp(base + "/apiv1/other/getAnnouncementCountNumber", count);
        writeFile("\n" + System.currentTimeMillis() + "     " + unlookInfo);

        unlookInfo = requestHttp(base + "/apiv1/other/getJobsCountNumber", count);
        writeFile("\n" + System.currentTimeMillis() + "     " + unlookInfo);
    }

    public static String requestHttp(String urlString, Map<String, Object> param) {
        InputStream inputStream = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            //下面两行是添加头文件
            connection.setRequestProperty("user-agent", System.getProperty("http.agent"));
            connection.setRequestProperty("fxr-client", "Android");
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.connect();

            if (null != param && 0 < param.size()) {
                Iterator<Map.Entry<String, Object>> iterator = param.entrySet().iterator();
                StringBuffer sb = new StringBuffer();
                while (iterator.hasNext()) {
                    Map.Entry<String, Object> entry = iterator.next();
                    sb.append(entry.getKey())
                            .append("=")
                            .append(entry.getValue())
                            .append("&");
                }
                if (0 < sb.length()) {
                    sb.deleteCharAt(sb.length() - 1);
                }
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
                writer.write(sb.toString());
                writer.close();
            }
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = connection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer getSb = new StringBuffer();
                String line = null;
                while ((line = br.readLine()) != null) {
                    getSb.append(line);
                }
                String result = getSb.toString();
                return result;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (null != connection) {
                connection.disconnect();
            }
        }
        return null;
    }

    public static void writeFile(String content) {
        File file = new File("F:/workation/java/requestSession/request.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        try {
            FileOutputStream fos = new FileOutputStream(file.getPath(), true);
            fos.write(content.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getMD5Code(String strObj) {
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

    private final static String[] strDigits = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
}
