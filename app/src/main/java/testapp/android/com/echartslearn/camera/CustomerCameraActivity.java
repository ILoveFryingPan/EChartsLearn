package testapp.android.com.echartslearn.camera;

import android.content.res.Configuration;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hjq.toast.ToastUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import testapp.android.com.echartslearn.R;

public class CustomerCameraActivity extends AppCompatActivity implements View.OnClickListener{

    private int dip15;
    private int dip50;
    private int dip100;

    private CameraPreview preview;
    private ImageView lightImage;
    private ImageView cameraTypeImage;
    private TextView cancel;
    private ImageView shootImage;

    private int cameraId;
    private Camera camera;
    private RelativeLayout rootLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        setContentView(createView());
        initData2();
    }

    private void initData() {
        dip15 = (int) getResources().getDimension(R.dimen.dip_15);
        dip50 = (int) getResources().getDimension(R.dimen.dip_50);
        dip100 = (int) getResources().getDimension(R.dimen.dip_100);
    }

    private void initData2() {
        rootLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                //预览的大小是多少
                Camera.Size size = camera.getParameters().getPreviewSize();
                setCameraPreviewSize(preview.getWidth(), preview.getHeight(), size.width, size.height);
            }
        });
    }

    private View createView() {
        rootLayout = new RelativeLayout(this);
        rootLayout.setBackgroundColor(0xffffffff);
        ViewGroup.LayoutParams rootLP = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rootLayout.setLayoutParams(rootLP);

        preview = new CameraPreview(this);
        RelativeLayout.LayoutParams previewLP = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        previewLP.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
//        previewLP.addRule(RelativeLayout.ABOVE, bottomLayout.getId());
//        previewLP.addRule(RelativeLayout.BELOW, topLayout.getId());
        rootLayout.addView(preview, previewLP);

        RelativeLayout topLayout = new RelativeLayout(this);
        topLayout.setBackgroundColor(0xffffffff);
        topLayout.setId(R.id.custom_top_layout);
        RelativeLayout.LayoutParams topLayoutLP = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip50);
        rootLayout.addView(topLayout, topLayoutLP);

        lightImage = new ImageView(this);
        lightImage.setImageResource(R.mipmap.one);
        lightImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        lightImage.setOnClickListener(this);
        RelativeLayout.LayoutParams lightImageLP = new RelativeLayout.LayoutParams(dip50, dip50);
        lightImageLP.leftMargin = dip15;
        topLayout.addView(lightImage, lightImageLP);

        cameraTypeImage = new ImageView(this);
        cameraTypeImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        cameraTypeImage.setImageResource(R.mipmap.two);
        cameraTypeImage.setOnClickListener(this);
        RelativeLayout.LayoutParams cameraTypeLP = new RelativeLayout.LayoutParams(dip50, dip50);
        cameraTypeLP.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        cameraTypeLP.rightMargin = dip15;
        topLayout.addView(cameraTypeImage, cameraTypeLP);

        RelativeLayout bottomLayout = new RelativeLayout(this);
        bottomLayout.setId(R.id.custom_bottom_layout);
        bottomLayout.setBackgroundColor(0xffffffff);
        RelativeLayout.LayoutParams bottomLayoutLP = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip50);
        bottomLayoutLP.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        rootLayout.addView(bottomLayout, bottomLayoutLP);

        cancel = new TextView(this);
        cancel.setTextSize(15);
        cancel.setTextColor(0xff333333);
        cancel.setGravity(Gravity.CENTER);
        cancel.setText("取消");
        cancel.getPaint().setFakeBoldText(true);
        cancel.setOnClickListener(this);
        RelativeLayout.LayoutParams cancelLP = new RelativeLayout.LayoutParams(dip50, dip50);
//        cancelLP.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        cancelLP.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        cancelLP.leftMargin = dip15;
        bottomLayout.addView(cancel, cancelLP);

        shootImage = new ImageView(this);
        shootImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        shootImage.setImageResource(R.mipmap.three);
        shootImage.setOnClickListener(this);
        RelativeLayout.LayoutParams shootImageLP = new RelativeLayout.LayoutParams(dip100, dip100);
        shootImageLP.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        shootImageLP.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        bottomLayout.addView(shootImage, shootImageLP);

        return rootLayout;
    }

    @Override
    protected void onResume() {
        super.onResume();
        createCamera(0);
    }

    private void createCamera(int cameraId) {
        stopCamera();
        try {
            camera = Camera.open(cameraId);
            preview.setmCamera(camera);
            this.cameraId = cameraId;
        } catch (RuntimeException e) {
            e.printStackTrace();
            ToastUtils.getToast().setDuration(Toast.LENGTH_LONG);
            ToastUtils.show("相机正在使用中，无法使用该功能");
        }

        //得到照相机的参数
        Camera.Parameters parameters = camera.getParameters();
        //图片的格式
        parameters.setPictureFormat(ImageFormat.JPEG);
        //设置对焦模式，自动对焦
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(lightImage)) {
            //是否开启闪光灯
            if (null != camera) {
                Camera.Parameters parameters = camera.getParameters();
                String flashMode = parameters.getFlashMode();
                if (Camera.Parameters.FLASH_MODE_TORCH.equals(flashMode)) {
                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                } else {
                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                }
                camera.setParameters(parameters);
//                camera.release();     这行代码不能用，闪退
            }
        } else if (v.equals(cameraTypeImage)) {
            //选择前置摄像头还是后置摄像头
            int id = cameraId ^ 1;
            createCamera(id);
            //预览的大小是多少
            Camera.Size size = camera.getParameters().getPreviewSize();
            setCameraPreviewSize(preview.getWidth(), preview.getHeight(), size.width, size.height);
        } else if (v.equals(cancel)) {
            //取消
            finish();
        }else if (v.equals(shootImage)) {
            //拍摄
            camera.takePicture(null, null, mPictureCallback);
        }
    }

    //获取照片中的接口回调
    Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            FileOutputStream fos = null;
            String mFilePath = Environment.getExternalStorageDirectory().getPath() + File.separator + "tt001.png";
            //文件
            File tempFile = new File(mFilePath);
            try {
                //
                fos = new FileOutputStream(tempFile);
                fos.write(data);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                //实现连续拍多张的效果
                camera.startPreview();
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopCamera();
    }

    private void stopCamera() {
        if (camera != null) {
            camera.stopPreview();
            camera.setPreviewCallback(null);
            camera.release();
            camera = null;
        }
    }

    public void setCameraPreviewSize(int viewWidth, int viewHeight, int cameraWidth, int cameraHeight) {
        boolean bIsPortrait = (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);    // 判断水平/垂直状态

        if (bIsPortrait) {
            int tmp = cameraWidth;
            cameraWidth = cameraHeight;
            cameraHeight = tmp;
        }

        int destWidth = viewWidth;
        int destHeight = viewHeight;

        if (bIsPortrait) {
            destHeight = (int)(((double)cameraHeight / cameraWidth) * destWidth);
        } else {
            destWidth = (int)(((double)cameraWidth / cameraHeight) * destHeight);
        }

        RelativeLayout.LayoutParams preViewLP = (RelativeLayout.LayoutParams) preview.getLayoutParams();
        preViewLP.width = destWidth;
        preViewLP.height = destHeight;
        preview.setLayoutParams(preViewLP);
    }
}
