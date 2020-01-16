package testapp.android.com.echartslearn.media_player.vedio.video_list;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import testapp.android.com.echartslearn.AllReplyClicklistener;
import testapp.android.com.echartslearn.R;

/**
 * 视频
 * 列：0 = _id    主键。视频ID
 * 列：1 = _data  视频的绝对路径
 * 列：2 = _display_name  文件名
 * 列：3 = _size  文件大小，单位为byte
 * 列：4 = mime_type  视频类型
 * 列：5 = date_added
 * 列：6 = date_modified
 * 列：7 = title
 * 列：8 = duration   这个是视频时长？
 * 列：9 = artist
 * 列：10 = album  专辑名，一般为文件夹名
 * 列：11 = resolution
 * 列：12 = description
 * 列：13 = isprivate
 * 列：14 = tags
 * 列：15 = category
 * 列：16 = language
 * 列：17 = mini_thumb_data
 * 列：18 = latitude
 * 列：19 = longitude
 * 列：20 = datetaken
 * 列：21 = mini_thumb_magic  取小缩略图时生成的一个随机数，见 MediaThumbRequest
 * 列：22 = bucket_id 个人理解一个文件夹里的视频的ID都是一样的
 * 列：23 = bucket_display_name   直接包含视频的文件夹就是该图片的 bucket，就是文件夹名
 * 列：24 = bookmark
 * 列：25 = width
 * 列：26 = height
 * 列：27 = is_hw_privacy
 * 列：28 = is_hw_favorite
 * 列：29 = album_sort_index
 * 列：30 = bucket_display_name_alias
 */

/**
 * 视频缩略图
 * 列：0 = _id
 * 列：1 = _data  缩略图的绝对路径
 * 列：2 = video_id   缩略图所对应视频的ID，依赖于video表的_id字段
 * 列：3 = kind   缩略图类型，1是大图，视频只能取类型1
 * 列：4 = width
 * 列：5 = height
 */

public class SearchAndShowVedioActivity extends AppCompatActivity implements View.OnClickListener{

    private GridView gridView;
    private VideoBaseAdapter baseAdapter;
    private int FOLDER = 1;
    private int FILE = 2;
    private int type = FOLDER;
    private int position = 0;
    private RelativeLayout relativeLayout;
    private ProgressBar progressBar;
    private int level = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        relativeLayout = new RelativeLayout(this);
        setContentView(relativeLayout);
        gridView = createGridView(this);
        relativeLayout.addView(gridView);
        progressBar = createPregressBar(this);
        relativeLayout.addView(progressBar);
        progressBar.setVisibility(View.GONE);
        Intent mIntent = getIntent();
        type = mIntent.getIntExtra("type", FOLDER);
        position = mIntent.getIntExtra("position", 0);
        level = mIntent.getIntExtra("level", 0);
        VideoHelper videoHelper = VideoHelper.getInstance().init(this);
        videoHelper.getVideoBucketList(replyClicklistener);
    }

    private TextView createTextView(Context mContext){
        TextView text = new TextView(mContext);
        text.setText("fileManager");
        text.setTextSize(50);
        text.setGravity(Gravity.CENTER);
        text.setId(R.id.file_manager);
        text.setOnClickListener(this);
        return text;
    }

    private GridView createGridView(Context mContext){
        GridView gridView = new GridView(mContext);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        int margin = (int) getResources().getDimension(R.dimen.show_video_margin);
        int spacing = (int) getResources().getDimension(R.dimen.show_video_spacing);
        lp.setMargins(margin, margin, margin, margin);
        gridView.setHorizontalSpacing(spacing);
        gridView.setVerticalSpacing(spacing);
        gridView.setNumColumns(2);
        gridView.setVerticalScrollBarEnabled(false);
        gridView.setHorizontalScrollBarEnabled(false);
        gridView.setLayoutParams(lp);
        return gridView;
    }

    private ProgressBar createPregressBar(Context mContext){
        ProgressBar progressBar = new ProgressBar(mContext, null, android.R.attr.progressBarStyleHorizontal);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int top = (int) mContext.getResources().getDimension(R.dimen.show_video_spacing);
        lp.topMargin = top;
        progressBar.setLayoutParams(lp);
        progressBar.setMax(100);
        progressBar.setMinimumHeight(top);
        progressBar.setId(R.id.vedio_upload_progressbar);
        return progressBar;
    }

    private AllReplyClicklistener replyClicklistener = new AllReplyClicklistener() {
        @Override
        public void reply(final Object type) {
            final List<VideoBucket> bucketList = (List<VideoBucket>) type;
            if (baseAdapter == null){
                if (SearchAndShowVedioActivity.this.type == FOLDER) {
                    baseAdapter = new VideoBaseAdapter(SearchAndShowVedioActivity.this, bucketList);
                }else {
                    baseAdapter = new VideoBaseAdapter(bucketList.get(position).itemList, SearchAndShowVedioActivity.this);
                }
            }
            gridView.setAdapter(baseAdapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (SearchAndShowVedioActivity.this.type == FOLDER){
                        Intent mIntent = new Intent(SearchAndShowVedioActivity.this, SearchAndShowVedioActivity.class);
                        mIntent.putExtra("type", FILE);
                        mIntent.putExtra("position", position);
                        startActivityForResult(mIntent, 100);
                    }else {
                        VideoItem item = bucketList.get(SearchAndShowVedioActivity.this.position).itemList.get(position);
                        Intent data = new Intent();
                        data.putExtra("path", item.path);
                        setResult(Activity.RESULT_OK, data);
                        finish();
//                        long size = 100 * 1024 * 1024;
//                        if (item.time > 10000 && "video/mp4".equals(item.videoType) && item.videoSize < size) {
//                            Toast.makeText(SearchAndShowVedioActivity.this, "开始网络请求", Toast.LENGTH_SHORT).show();
//                            Map<String, Object> upMap = new HashMap<>();
//                            upMap.put("sessionId","0142e3b5e3ea30406816615fa6b2f7cc");
//                            VideoHelper.uploadFile(SearchAndShowVedioActivity.this, new File(item.path),
//                                    "http://10.7.0.62:8888/apiv1/house/videoupload", upMap, progressBar, new VideoHelper.RequestCallback() {
//                                @Override
//                                public void doSuccess(Map<String, Object> jsonMap) {
//
//                                }
//
//                                @Override
//                                public void doFail(int result) {
//
//                                }
//                            });
//                        }
                    }
                }
            });
        }
    };

    public void showVedioInfo(){
        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        cursor.moveToFirst();
        String columns[] = cursor.getColumnNames();
        for (String s: columns){
            Log.d("SearchAndShowVedioActiv", "列：" + cursor.getColumnIndexOrThrow(s) + " = " + s);
        }
        Log.d("SearchAndShowVedioActiv", "视频的数量：" + cursor.getCount());
    }

    public void showVedioImage(){
        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI, null, null, null, null);
        cursor.moveToFirst();
        String columns[] = cursor.getColumnNames();
        for (String s: columns){
            Log.d("SearchAndShowVedioActiv", "列：" + cursor.getColumnIndexOrThrow(s) + " = " + s);
        }
        Log.d("SearchAndShowVedioActiv", "视频预览图的数量：" + cursor.getCount());
    }

    public void getFileFromManager(){
        Intent mIntent = new Intent(Intent.ACTION_GET_CONTENT);
        mIntent.setType("video/mp4");
        mIntent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(mIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (100 == requestCode && Activity.RESULT_OK == resultCode && 0 < level) {
            setResult(Activity.RESULT_OK, data);
            finish();
        }
//        if (resultCode == Activity.RESULT_OK){
//            Uri uri = data.getData();
//            Log.d("SearchAndShowVedioActiv", "uri的值是：" + uri.getPath() + "\t" + uri.getScheme() + "\t" + uri.getAuthority());
//            String[] proj = {MediaStore.Video.Media.DATA};
//            Cursor cursor = getContentResolver().query(uri, proj, null, null, null);
//            String docId = DocumentsContract.getDocumentId(uri);
//            final String[] split = docId.split(":");
//            final String type = split[0];
//            Log.d("SearchAndShowVedioActiv", "type的值是：" + type);
////            if ("primary".equalsIgnoreCase(type)){
//                String paths = Environment.getExternalStorageDirectory() + "/" + split[1];
//                Log.d("SearchAndShowVedioActiv", "解析的路径是：" + paths);
////            }
//            if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
//                int path = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
//                String videoPath = cursor.getString(path);
//                Log.d("SearchAndShowVedioActiv", "路径是：" + videoPath);
//            }
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.file_manager:
                getFileFromManager();
                break;
        }
    }
}
