package testapp.android.com.echartslearn.camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import testapp.android.com.echartslearn.R;

public class LaunchModeCameraActivity extends AppCompatActivity implements View.OnClickListener {

    private int dip15;
    private int dip50;

    private TextView invisiableCameraText;
    private TextView visiableCameraText;
    private TextView customCameraText;
    private ImageView imageView;

    private String mFilePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        setContentView(createView());
    }

    private void initData() {
        dip15 = (int) getResources().getDimension(R.dimen.dip_15);
        dip50 = (int) getResources().getDimension(R.dimen.dip_50);
    }

    private View createView() {
        LinearLayout rootLayout = new LinearLayout(this);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        rootLayout.setGravity(Gravity.CENTER);
        rootLayout.setBackgroundColor(0xffffffff);
        ViewGroup.LayoutParams rootLP = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rootLayout.setLayoutParams(rootLP);

        invisiableCameraText = new TextView(this);
        invisiableCameraText.setTextSize(15);
        invisiableCameraText.setTextColor(0xff333333);
        invisiableCameraText.setGravity(Gravity.CENTER);
        invisiableCameraText.setOnClickListener(this);
        invisiableCameraText.setBackgroundColor(0xff999999);
        invisiableCameraText.setText("隐式调用");
        LinearLayout.LayoutParams invisiableLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip50);
        invisiableLP.setMargins(dip15, 0, dip15, dip15);
        rootLayout.addView(invisiableCameraText, invisiableLP);

        visiableCameraText = new TextView(this);
        visiableCameraText.setTextSize(15);
        visiableCameraText.setTextColor(0xff333333);
        visiableCameraText.setGravity(Gravity.CENTER);
        visiableCameraText.setOnClickListener(this);
        visiableCameraText.setBackgroundColor(0xff999999);
        visiableCameraText.setText("显示调用");
        LinearLayout.LayoutParams visiableCameraLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip50);
        visiableCameraLP.setMargins(dip15, 0, dip15, dip15);
        rootLayout.addView(visiableCameraText, visiableCameraLP);

        customCameraText = new TextView(this);
        customCameraText.setTextSize(15);
        customCameraText.setTextColor(0xff333333);
        customCameraText.setGravity(Gravity.CENTER);
        customCameraText.setOnClickListener(this);
        customCameraText.setBackgroundColor(0xff999999);
        customCameraText.setText("自定义页面");
        LinearLayout.LayoutParams customCameraLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip50);
        customCameraLP.setMargins(dip15, 0, dip15, dip15);
        rootLayout.addView(customCameraText, customCameraLP);

        imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        LinearLayout.LayoutParams imageLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rootLayout.addView(imageView, imageLP);

        return rootLayout;
    }

    @Override
    public void onClick(View v) {
        if (v.equals(invisiableCameraText)) {
            //隐式调用相机
            Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
            startActivityForResult(getImageByCamera, 0);
        } else if (v.equals(visiableCameraText)) {
            //显示调用相机
            mFilePath = Environment.getExternalStorageDirectory().getPath() + File.separator + "allens.png";
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri uri = Uri.fromFile(new File(mFilePath));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, 1);
        } else if (v.equals(customCameraText)) {
            //自定义相机页面
            startActivity(new Intent(this, CustomerCameraActivity.class));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (0 == requestCode) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(bitmap);
        } else if (requestCode == 1) {
            FileInputStream mFis = null;
            try {
                mFis = new FileInputStream(mFilePath);
                Bitmap bitmap = BitmapFactory.decodeStream(mFis);
                imageView.setImageBitmap(bitmap);
//                textView2.setText("width--->" + bitmap.getWidth() + "\n" + "height--->" + bitmap.getHeight() + "\n" + "byte--->" + bitmap.getByteCount() / 1024);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (mFis != null)
                        mFis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
