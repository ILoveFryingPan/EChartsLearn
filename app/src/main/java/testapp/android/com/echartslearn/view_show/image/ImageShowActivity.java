package testapp.android.com.echartslearn.view_show.image;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import testapp.android.com.echartslearn.HttpHelper;

public class ImageShowActivity extends AppCompatActivity {

    private String gifUrl = "http://www.07073.com/uploads/120807/4822598_141907_3.gif";
//    private String gifUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/23/Cluster_diffusion_glide.gif/160px-Cluster_diffusion_glide.gif";
//    private String originUrl = "http://pic66.nipic.com/file/20150421/20873095_161409041000_2.jpg";
    private String originUrl = "https://imageicloud.fangxiaoer.com/ad/2019/09/23/094715756.jpg";

    private MyGifView gifView;
    private ImageView originImage;
    private ImageView gifImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(createView());

        Uri gifUri = Uri.parse(gifUrl);

        HttpHelper.getGifImage(this, originUrl, gifView);
        Glide.with(ImageShowActivity.this)
                .load(gifUrl)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(gifImage);

        Glide.with(ImageShowActivity.this)
                .load(originUrl)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(originImage);
    }

    private View createView() {
        LinearLayout rootLayout = new LinearLayout(this);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        rootLayout.setBackgroundColor(0xff00ffff);
        ViewGroup.LayoutParams rootLP = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rootLayout.setLayoutParams(rootLP);

        gifView = new MyGifView(this);
        gifView.setBackgroundColor(0xff00ff00);
        LinearLayout.LayoutParams gifViewLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 600);
        rootLayout.addView(gifView, gifViewLP);

        gifImage = new ImageView(this);
        gifImage.setBackgroundColor(0xffff0000);
        gifImage.setScaleType(ImageView.ScaleType.CENTER);
        LinearLayout.LayoutParams gifImageLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 600);
        rootLayout.addView(gifImage, gifImageLP);

        originImage = new ImageView(this);
        originImage.setScaleType(ImageView.ScaleType.CENTER);
        originImage.setBackgroundColor(0xff0000ff);
        LinearLayout.LayoutParams originImageLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 600);
        rootLayout.addView(originImage, originImageLP);

        return rootLayout;
    }
}
