package testapp.android.com.echartslearn.media_player.vedio.video_list;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore.Video.Media;
import android.provider.MediaStore.Video.Thumbnails;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

//import org.apache.http.entity.mime.content.StringBody;
//import org.apache.http.entity.mime.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import testapp.android.com.echartslearn.AllReplyClicklistener;
import testapp.android.com.echartslearn.media_player.vedio.Constants;

public class VideoHelper {
    BitmapFactory.Options options = new BitmapFactory.Options();
    private static VideoHelper videoHelper = null;

    private VideoHelper() {
        options.inPreferredConfig = Bitmap.Config.ARGB_4444;
    }

    private Context mContext;
    private ContentResolver cr;
    private AllReplyClicklistener replyClicklistener = null;
    private Map<Integer, String> thumbnails = new HashMap<>();
    private Map<Integer, VideoBucket> bucketMap = new HashMap<>();
    private final int NO_QUERY = 1;
    private final int IN_QUERY = 2;
    private final int END_QUERY = 0;
    private int status = NO_QUERY;

    public static VideoHelper getInstance() {
        if (videoHelper == null) {
            synchronized (VideoHelper.class) {
                if (videoHelper == null) {
                    videoHelper = new VideoHelper();
                }
            }
        }
        return videoHelper;
    }

    public VideoHelper init(@NonNull Context mContext) {
        if (mContext != null) {
            this.mContext = mContext;
            cr = mContext.getContentResolver();
        } else {
            throw new RuntimeException("上下文不能为空");
        }
        return videoHelper;
    }

    public void getVideoBucketList(AllReplyClicklistener replyClicklistener) {
        if (status == END_QUERY) {
            replyClicklistener.reply(settleVideoBucket());
        } else {
            getVideo(replyClicklistener);
        }
    }

    private List<VideoBucket> settleVideoBucket() {
        List<VideoBucket> bucketList = new ArrayList<>();
        Iterator<Map.Entry<Integer, VideoBucket>> itr = bucketMap.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<Integer, VideoBucket> entry = itr.next();
            bucketList.add(entry.getValue());
        }
        return bucketList;
    }

    private void getVideoImages() {
        thumbnails.clear();
        if (cr != null) {
            String columns[] = new String[]{Thumbnails._ID, Thumbnails.DATA, Thumbnails.VIDEO_ID, Thumbnails.KIND};
            Cursor cursor = cr.query(Thumbnails.EXTERNAL_CONTENT_URI, columns, null, null, null);
            if (cursor.moveToFirst()) {

                int _id;
                String path;
                int videoId;
                int kind;

                int _idColumn = cursor.getColumnIndexOrThrow(Thumbnails._ID);
                int pathColumn = cursor.getColumnIndexOrThrow(Thumbnails.DATA);
                int videoIdColumn = cursor.getColumnIndexOrThrow(Thumbnails.VIDEO_ID);
                int kindColumn = cursor.getColumnIndexOrThrow(Thumbnails.KIND);
                do {
                    _id = cursor.getInt(_idColumn);
                    path = cursor.getString(pathColumn);
                    videoId = cursor.getInt(videoIdColumn);
                    kind = cursor.getInt(kindColumn);
                    thumbnails.put(videoId, path);
                } while (cursor.moveToNext());
            }
        } else {
            throw new RuntimeException("ContentResolver不能为空");
        }
    }

    private void getVideo(final AllReplyClicklistener replyClicklistener) {
        if (status == END_QUERY) {
            replyClicklistener.reply(settleVideoBucket());
        } else if (status == IN_QUERY) {
            return;
        } else if (status == NO_QUERY) {
            status = IN_QUERY;
        }

        //耗时动画开始

        new Thread(new Runnable() {
            @Override
            public void run() {
                bucketMap.clear();
                getVideoImages();
                if (cr != null) {
                    String columns[] = new String[]{Media._ID, Media.DATA, Media.DISPLAY_NAME, Media.SIZE, Media.DURATION, Media.MIME_TYPE, Media.BUCKET_ID, Media.BUCKET_DISPLAY_NAME};
                    //MP4视频的类型只有小写的mp4，这个类型查询出来的视频，大小写都包括了，相反大写的条件没有查到，即使有这个视频
//            Cursor cursor = cr.query(Media.EXTERNAL_CONTENT_URI, columns, Media.MIME_TYPE + "=? or " + Media.MIME_TYPE + "=?", new String[]{"video/mp4", "video/MP4"}, null);
                    Cursor cursor = cr.query(Media.EXTERNAL_CONTENT_URI, columns, Media.MIME_TYPE + "=?", new String[]{"video/mp4"}, null);
//            Cursor cursor = cr.query(Media.EXTERNAL_CONTENT_URI, columns, null, null, null);
                    if (cursor.moveToFirst()) {

                        int _id;
                        String path;
                        String videoName;
                        long videoSize;
                        long videoDuration;
                        String videoType;
                        int bucketId;
                        String bucketName;

                        int _idColumn = cursor.getColumnIndexOrThrow(Media._ID);
                        int pathColumn = cursor.getColumnIndexOrThrow(Media.DATA);
                        int videoNameColumn = cursor.getColumnIndexOrThrow(Media.DISPLAY_NAME);
                        int videoSizeColumn = cursor.getColumnIndexOrThrow(Media.SIZE);
                        int durationColumn = cursor.getColumnIndexOrThrow(Media.DURATION);
                        int mimeTypeColumn = cursor.getColumnIndexOrThrow(Media.MIME_TYPE);
                        int bucketIdColumn = cursor.getColumnIndexOrThrow(Media.BUCKET_ID);
                        int bucketNameColumn = cursor.getColumnIndexOrThrow(Media.BUCKET_DISPLAY_NAME);
                        do {
                            _id = cursor.getInt(_idColumn);
                            path = cursor.getString(pathColumn);
                            videoName = cursor.getString(videoNameColumn);
                            videoSize = cursor.getLong(videoSizeColumn);
                            videoDuration = cursor.getLong(durationColumn);
                            videoType = cursor.getString(mimeTypeColumn);
                            bucketId = cursor.getInt(bucketIdColumn);
                            bucketName = cursor.getString(bucketNameColumn);
                Log.d("VideoHelper", "name:" + videoName + "size:" + videoSize + "duration:" + videoDuration + "type:" + videoType + "\n" + "path:" + path);
                            Bitmap bmp = null;
                            if (TextUtils.isEmpty(thumbnails.get(_id))){
                                bmp = Thumbnails.getThumbnail(cr, _id, Thumbnails.MINI_KIND, options);
                            }
                            VideoBucket bucket = bucketMap.get(bucketId);
                            if (bucket == null) {
                                bucket = new VideoBucket();
                                bucket.bucketName = bucketName;
                                bucket.itemList = new ArrayList<>();
                                bucket.thumbnailPath = thumbnails.get(_id);
                                if (TextUtils.isEmpty(bucket.thumbnailPath)) {
                                    bucket.thumbnailBitmap = bmp;
                                }
                                bucketMap.put(bucketId, bucket);
                            }
                            bucket.count++;
                            VideoItem item = new VideoItem();
                            item.imagePath = thumbnails.get(_id);
                            if (TextUtils.isEmpty(item.imagePath)){
                                item.imageBitmap = bmp;
                            }
                            item.name = videoName;
                            item.path = path;
                            item.time = videoDuration;
                            item.videoSize = videoSize;
                            item.videoType = videoType;
                            bucket.itemList.add(item);

                        } while (cursor.moveToNext());
                        status = END_QUERY;
                        if (mContext != null && replyClicklistener != null) {
                            ((Activity) mContext).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //耗时动画结束
                                    replyClicklistener.reply(settleVideoBucket());
                                }
                            });
                        }
                    }
                } else {
                    throw new RuntimeException("ContentResolver不能为空");
                }
            }
        }).start();
    }



    public interface RequestCallback {
        void doSuccess(Map<String, Object> jsonMap);

        void doFail(int result);
    }


    public static void uploadFile(final Context mContext, File mFile, final String url, Map<String, Object> upMap,
                                  final ProgressBar progressBar, final RequestCallback callback){
        if (mFile != null && mFile.exists() && mFile.length() > 0) {
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
//            params.put("uploadfile", mFile);
            if (upMap != null){
                Iterator<Map.Entry<String, Object>> iterator = upMap.entrySet().iterator();
                while (iterator.hasNext()){
                    Map.Entry<String, Object> entry = iterator.next();
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    if (key != null && value != null){
                        params.put(key, value);
                    }
                }
            }
            // 上传文件

            client.post(url, params, new AsyncHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
//                    Toast.makeText(mContext, "上传成功", Toast.LENGTH_LONG).show();

//                    if (showDialog) {
//                        try {
//                            progress.cancel();
//                        } catch (Exception e) {
//                        }
//                    }
                    String jsonString = new String(responseBody);
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
                    }catch (JsonSyntaxException e) {
                        callback.doFail(0);
                        return;
                    }
                    //Logger.e(RequestHelper.class, "jsonMapTmp-->" + jsonMapTmp.toString());
                    Integer status;
                    try {
                        status = (int) Math.floor((Double) jsonMapTmp.get(Constants.STATUS));
                    } catch (Exception e) {
                        try {
                            status = (int) Math.floor((Double) jsonMapTmp.get(Constants.RESSTATE));
                        } catch (Exception e1) {
                            status = 0;
                        }
                    }
                    if (status == 1) {
                        callback.doSuccess(jsonMapTmp);
                        if (progressBar != null){
                            progressBar.setProgress(100);
                        }

                        Log.d("VideoHelper", "上传成功");

                    } else if (status == -1) {
//                        PromptHelper.displayAccountOccuptied(context);
                    } else {
                        callback.doFail(0);
                        String msg = jsonMapTmp.get("msg") + "";
                        if (!TextUtils.isEmpty(msg) && !msg.contains("Exception")
                                && !msg.contains("session")) {
//                            if ("验证码错误".equals(msg) && mContext instanceof LoginActivity) {
//                                msg = "密码错误";
//                            }
//                            PromptHelper.displayMessage(context, msg);
                        }
                    }
                }

                @Override
                public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                    Toast.makeText(mContext, "上传失败", Toast.LENGTH_LONG).show();
//                    if (showDialog) {
//                        try {
//                            progress.cancel();
//                        }catch (IllegalArgumentException e){
//                            e.printStackTrace();
//                        }
//                    }
//                    PromptHelper.displayMessage(context, context.getString(R.string.net_error));
                    callback.doFail(0);
                }

                @Override
                public void onProgress(long bytesWritten, long totalSize) {
                    super.onProgress(bytesWritten, totalSize);
                    int count = (int) ((bytesWritten * 1.0 / totalSize) * 100);
                    if (progressBar != null) {
                        progressBar.setProgress(count);
                    }
                }

                @Override
                public void onStart() {
                    super.onStart();
                    if (progressBar != null && progressBar.getVisibility() != View.VISIBLE) {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onRetry(int retryNo) {
                    // TODO Auto-generated method stub
                    super.onRetry(retryNo);
                    // 返回重试次数
                }

            });
        } else {
            Toast.makeText(mContext, "文件不存在", Toast.LENGTH_LONG).show();
        }

    }


}
