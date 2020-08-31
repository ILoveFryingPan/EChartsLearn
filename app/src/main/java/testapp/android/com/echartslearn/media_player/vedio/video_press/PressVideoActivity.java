package testapp.android.com.echartslearn.media_player.vedio.video_press;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.vod.upload.VODUploadCallback;
import com.alibaba.sdk.android.vod.upload.VODUploadClientImpl;
import com.alibaba.sdk.android.vod.upload.model.UploadFileInfo;
import com.alibaba.sdk.android.vod.upload.model.VodInfo;
import com.iceteck.silicompressorr.SiliCompressor;
import com.mabeijianxi.smallvideorecord2.DeviceUtils;
import com.mabeijianxi.smallvideorecord2.JianXiCamera;
import com.mabeijianxi.smallvideorecord2.LocalMediaCompress;
import com.mabeijianxi.smallvideorecord2.model.AutoVBRMode;
import com.mabeijianxi.smallvideorecord2.model.LocalMediaConfig;
import com.mabeijianxi.smallvideorecord2.model.OnlyCompressOverBean;

import java.io.File;
import java.net.URISyntaxException;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import testapp.android.com.echartslearn.HttpHelper;
import testapp.android.com.echartslearn.R;
import testapp.android.com.echartslearn.media_player.vedio.video_list.SearchAndShowVedioActivity;

public class PressVideoActivity extends AppCompatActivity implements View.OnClickListener {

    private int dip10;
    private int dip20;
    private int dip50;

    private String path;
    private String outPath;
    private String filePath;

    private VODUploadClientImpl uploader;

    private TextView showMsg;
    private TextView selectVideo;
    private TextView pressText;
    private TextView pushVideo;
    private ProgressBar progressBar;
    private TextView pressPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String extral = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            extral = Environment.getExternalStorageDirectory().getPath();
        }
        if (TextUtils.isEmpty(extral)) {
            return;
        } else {
            outPath = extral + File.separator + "videoPress";
            File outFile = new File(outPath);
            if (!outFile.exists()) {
                outFile.mkdirs();
            }
        }
        initData();
        setContentView(createView());

        getAddress();
    }

    private void initData() {
        dip10 = (int) getResources().getDimension(R.dimen.dip_10);
        dip20 = (int) getResources().getDimension(R.dimen.dip_20);
        dip50 = (int) getResources().getDimension(R.dimen.dip_50);

        uploader = new VODUploadClientImpl(getApplicationContext());
        uploader.init(callback);
        uploader.setTranscodeMode(true);
        VodInfo info = new VodInfo();
    }

    private View createView() {

        RelativeLayout rootParentLayout = new RelativeLayout(this);
        rootParentLayout.setBackgroundColor(0xfff0f0f0);
        ViewGroup.LayoutParams parentLP = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rootParentLayout.setLayoutParams(parentLP);

        LinearLayout root = new LinearLayout(this);
        root.setBackgroundColor(0xfff0f0f0);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(0, dip20, 0, dip20);
        root.setGravity(Gravity.CENTER_HORIZONTAL);
        RelativeLayout.LayoutParams rootLP = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rootParentLayout.addView(root, rootLP);

        selectVideo = new TextView(this);
        selectVideo.setText("选取本地视频");
        selectVideo.setTextSize(15);
        selectVideo.setTextColor(0xff333333);
        selectVideo.setBackgroundColor(0xffffffff);
        selectVideo.setGravity(Gravity.CENTER);
        selectVideo.setOnClickListener(this);
        LinearLayout.LayoutParams selectLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip50);
        root.addView(selectVideo, selectLP);

        showMsg = new TextView(this);
        showMsg.setTextSize(15);
        showMsg.setTextColor(0xffff00ff);
        showMsg.setBackgroundColor(0xffffffff);
        showMsg.setText("输出目录：" + outPath);
        showMsg.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams showLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip50);
        showLP.topMargin = dip10;
        root.addView(showMsg, showLP);

        pressPath = new TextView(this);
        pressPath.setTextSize(15);
        pressPath.setTextColor(0xffff00ff);
        pressPath.setBackgroundColor(0xffffffff);
        pressPath.setText("压缩后视频输出目录：");
        pressPath.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams pressPathLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip50);
        pressPathLP.topMargin = dip10;
        root.addView(pressPath, pressPathLP);

        pressText = new TextView(this);
        pressText.setText("视频压缩");
        pressText.setTextSize(15);
        pressText.setTextColor(0xff00ffff);
        pressText.setBackgroundColor(0xffffffff);
        pressText.setGravity(Gravity.CENTER);
        pressText.setOnClickListener(this);
        LinearLayout.LayoutParams pressLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip50);
        pressLP.topMargin = dip10;
        root.addView(pressText, pressLP);

        pushVideo = new TextView(this);
        pushVideo.setText("视频上传");
        pushVideo.setTextSize(15);
        pushVideo.setTextColor(0xff008888);
        pushVideo.setBackgroundColor(0xffffffff);
        pushVideo.setGravity(Gravity.CENTER);
        pushVideo.setOnClickListener(this);
        LinearLayout.LayoutParams pushLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip50);
        pushLP.topMargin = dip10;
        root.addView(pushVideo, pushLP);

        progressBar = new ProgressBar(this);
        RelativeLayout.LayoutParams barLP = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        barLP.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        rootParentLayout.addView(progressBar, barLP);
        progressBar.setVisibility(View.GONE);

        return rootParentLayout;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (200 == requestCode && Activity.RESULT_OK == resultCode && null != data) {
            String path = data.getStringExtra("path");
            if (!TextUtils.isEmpty(path)) {
                this.path = path;
                showMsg.setText("原始视频路径：" + path);
            } else {
                showMsg.setText("视频路径无效");
            }
        }
    }

    public static void initSmallVideo() {
        // 设置拍摄视频缓存路径
        String outPutPath = Environment.getExternalStorageDirectory().getPath();
        outPutPath = outPutPath + File.separator + "pressCache" + File.separator;
        File mfile = new File(outPutPath);
        if (!mfile.exists()) {
            mfile.mkdirs();
        }
            JianXiCamera.setVideoCachePath(outPutPath);
        // 初始化拍摄，遇到问题可选择开启此标记，以方便生成日志
        JianXiCamera.initialize(false,null);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(selectVideo)) {
            startActivityForResult(new Intent(this, SearchAndShowVedioActivity.class).putExtra("level", 1), 200);
        } else if (v.equals(pressText)) {
            if (TextUtils.isEmpty(path)) {
                Toast.makeText(this, "视频路径无效", Toast.LENGTH_SHORT).show();
                return;
            }
            initSmallVideo();
            progressBar.setVisibility(View.VISIBLE);

            // TODO: 2019/9/12 视频压缩的点击事件
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // 选择本地视频压缩
                    Log.d("PressVideoActivity", "视频压缩开始：" + System.currentTimeMillis());
                    LocalMediaConfig.Buidler buidler = new LocalMediaConfig.Buidler();
                    LocalMediaConfig config = buidler
                            .setVideoPath(path)
                            .captureThumbnailsTime(1)
                            .doH264Compress(new AutoVBRMode())
                            .setFramerate(15)
                            .setScale(1.0f)
                            .build();
                    OnlyCompressOverBean onlyCompressOverBean = new LocalMediaCompress(config).startCompress();
                    onlyCompressOverBean.getVideoPath();

                    Message message = handler.obtainMessage();
                    if (null == message) {
                        message = new Message();
                    }
                    message.what = 50;
                    message.obj = onlyCompressOverBean.getVideoPath();
                    handler.sendMessage(message);
                    Log.d("PressVideoActivity", "视频压缩结束：" + System.currentTimeMillis());
//                    try {
//                        MediaMetadataRetriever retr = new MediaMetadataRetriever();
//                        retr.setDataSource(path);
//                        String height = retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT); // 视频高度
//                        String width = retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH); // 视频宽度
//                        String rotation = retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION); // 视频旋转方向
//                        int outWidth = Integer.parseInt(width);
//                        int outHeight = Integer.parseInt(height);
//                        if (outWidth % 2 != 0) {
//                            outWidth--;
//                        }
//                        if (outHeight % 2 != 0) {
//                            outHeight--;
//                        }
//
//                        filePath = SiliCompressor.with(PressVideoActivity.this).compressVideo(path, outPath, outWidth, outHeight, 0);
//                        Message message = handler.obtainMessage();
//                        if (null == message) {
//                            message = new Message();
//                        }
//                        message.what = 50;
//                        message.obj = filePath;
//                        handler.sendMessage(message);
////                showMsg.setText("压缩后视频路径：" + filePath);
//                    } catch (URISyntaxException e) {
//                        e.printStackTrace();
//                    }
                }
            }).start();
        } else if (v.equals(pushVideo)) {
            // TODO: 2019/9/12 上传视频的点击事件
            VodInfo info = new VodInfo();
            info.setTitle("测试标题");
            uploader.addFile(path, info);
            uploader.start();
        }
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 50:
                    filePath = (String) msg.obj;
                    if (!TextUtils.isEmpty(filePath)) {
                        pressPath.setText("压缩后视频路径：" + filePath);
                    } else {
                        pressPath.setText("压缩失败");
                    }
                    progressBar.setVisibility(View.GONE);
                    break;
            }
            return false;
        }
    });

    VODUploadCallback callback = new VODUploadCallback() {
        @Override
        public void onUploadSucceed(UploadFileInfo info) {
            super.onUploadSucceed(info);
            Toast.makeText(PressVideoActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
            Log.d("PressVideoActivity", "上传成功");
        }

        @Override
        public void onUploadFailed(UploadFileInfo info, String code, String message) {
            super.onUploadFailed(info, code, message);
            Toast.makeText(PressVideoActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
            Log.d("PressVideoActivity", "上传失败");
        }

        @Override
        public void onUploadProgress(UploadFileInfo info, long uploadedSize, long totalSize) {
            super.onUploadProgress(info, uploadedSize, totalSize);
            Log.d("PressVideoActivity", "已上传大小：" + uploadedSize);
            Log.d("PressVideoActivity", "需上传总大小：" + totalSize);
            Log.d("PressVideoActivity", "上传进度：" + (uploadedSize * 1.0f / totalSize * 100) + "%");
        }

        @Override
        public void onUploadTokenExpired() {
            super.onUploadTokenExpired();
            uploader.resumeWithAuth("");
        }

        @Override
        public void onUploadStarted(UploadFileInfo uploadFileInfo) {
            super.onUploadStarted(uploadFileInfo);
            Toast.makeText(PressVideoActivity.this, "开始上传", Toast.LENGTH_SHORT).show();
            Log.d("PressVideoActivity", "开始上传");
            uploader.setUploadAuthAndAddress(uploadFileInfo, "", "");
        }
    };

    private void getAddress() {
        Map<String, Object> param = new HashMap<>();
        param.put("Action", "CreateUploadVideo");
        param.put("Title", "测试视频标题");
        param.put("FileName", "测试视频名称.mp4");
        HttpHelper.httpRequire(this, param, "http://vod.cn-shanghai.aliyuncs.com/", new HttpHelper.RequestCallback() {
            @Override
            public void doSuccess(Map<String, Object> jsonMap) {

            }

            @Override
            public void doFail(int result) {

            }
        }, false, "get");
    }
}
