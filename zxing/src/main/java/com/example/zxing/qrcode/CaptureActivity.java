package com.example.zxing.qrcode;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.zxing.R;
import com.example.zxing.qrcode.camera.CameraManager;
import com.example.zxing.qrcode.decoding.CaptureActivityHandler;
import com.example.zxing.qrcode.decoding.InactivityTimer;
import com.example.zxing.qrcode.decoding.RGBLuminanceSource;
import com.example.zxing.qrcode.view.ViewfinderView;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;


/**
 * Initial the camera
 *
 * @author Ryan.Tang
 */
public class CaptureActivity extends AppCompatActivity implements Callback {

    private static final int REQUEST_CODE_SCAN_GALLERY = 100;

    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private ImageView back;
    private LinearLayout llBack;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    private ProgressDialog mProgress;
    private Bitmap scanBitmap;

    //以下是临时代码
    private String base = "http://192.168.6.253:8083";
    private String sessionId;
    private String URL_HAS_SCAN = base + "/apiv1/base/hasScan";
    private String URL_CONFIRM_TOKEN = base + "/apiv1/base/confirmToken";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        CameraManager.init(getApplication());
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_content);
        back = (ImageView) findViewById(R.id.btn_back);
        llBack = (LinearLayout) findViewById(R.id.ll_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        testGetUserInfo();
    }

    //因为是在demo里面，所以临时在这个方法里登录
    private void testGetUserInfo() {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("telNumber", "13940550491");
        param.put("pwd", RequestJava.getMD5Code("111111"));
        param.put("loginWay", "3");
        param.put("loginVerion", Build.BRAND + "|" + Build.MODEL);
        param.put("memberType", "2");

        RequestJava.httpRequire(this, base + "/apiv1/base/login", param, new AllReplyClicklistener() {
            @Override
            public void reply(Object type) {
                String userInfo = (String) type;
                JsonParser parser = new JsonParser();
                JsonObject rootJson = (JsonObject) parser.parse(userInfo);
                JsonObject contentJson = rootJson.getAsJsonObject("content");
                sessionId = contentJson.get("sessionId").getAsString();
                Toast.makeText(CaptureActivity.this, "已经登录了", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_SCAN_GALLERY:
                    handleAlbumPic(data);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 处理选择的图片
     * 这个方法的作用是从本地选取一张二维码图片，然后识别这张二维码图片里面的二维码
     * 现在卖房通用不到，但是以防万一，先留着
     *
     * @param data
     */
    private void handleAlbumPic(Intent data) {
        //获取选中图片的路径
        final Uri uri = data.getData();

        mProgress = new ProgressDialog(CaptureActivity.this);
        mProgress.setMessage("正在扫描...");
        mProgress.setCancelable(false);
        mProgress.show();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Result result = scanningImage(uri);
                mProgress.dismiss();
                if (result != null) {
//                    Intent resultIntent = new Intent();
//                    Bundle bundle = new Bundle();
//                    bundle.putString(Keys.INTENT_EXTRA_KEY_QR_SCAN, result.getText());
//
//                    resultIntent.putExtras(bundle);
//                    CaptureActivity.this.setResult(RESULT_OK, resultIntent);
//                    finish();
                } else {
                    Toast.makeText(CaptureActivity.this, "识别失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 扫描二维码图片的方法
     *
     * @param uri
     * @return
     */
    public Result scanningImage(Uri uri) {
        if (uri == null) {
            return null;
        }
        Hashtable<DecodeHintType, String> hints = new Hashtable<>();
        hints.put(DecodeHintType.CHARACTER_SET, "UTF8"); //设置二维码内容的编码

        scanBitmap = BitmapUtil.decodeUri(this, uri, 500, 500);
        RGBLuminanceSource source = new RGBLuminanceSource(scanBitmap);
        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        try {
            return reader.decode(bitmap1, hints);
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (ChecksumException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.scanner_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    /**
     * Handler scan result
     *
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();     //获取二维码信息
        //FIXME
        if (TextUtils.isEmpty(resultString)) {
            Toast.makeText(CaptureActivity.this, "Scan failed!", Toast.LENGTH_SHORT).show();
        } else {
            Map<String, Object> scan = new HashMap<>();
            scan.put("token", resultString);
            scan.put("sessionId", sessionId);
            RequestJava.httpRequire(CaptureActivity.this, URL_HAS_SCAN, scan, new AllReplyClicklistener() {
                @Override
                public void reply(Object type) {

//                    startActivity(new Intent(CaptureActivity.this, AuthorizationLoginActivity.class));
                }
            });
        }
        CaptureActivity.this.finish();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    @SuppressLint("MissingPermission")
    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };
}