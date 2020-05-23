package testapp.android.com.echartslearn;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.hjq.toast.ToastUtils;
import com.samluys.pullfilterviewdemo.MainModuleActivity;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Random;

import testapp.android.com.echartslearn.array.ArrayActivity;
import testapp.android.com.echartslearn.base_fragment.BaseFragmentActivity;
import testapp.android.com.echartslearn.base_webview.BaseWebViewActivity;
import testapp.android.com.echartslearn.bundle_intent.PageAgoActivity;
import testapp.android.com.echartslearn.camera.LaunchModeCameraActivity;
import testapp.android.com.echartslearn.certification.CertificationActivity;
import testapp.android.com.echartslearn.choice_range.DoubleSlideSeekBarActivity;
import testapp.android.com.echartslearn.coordinator.CoordinatorActivity;
import testapp.android.com.echartslearn.dateSelect.DateSelectActivity;
import testapp.android.com.echartslearn.echartline.EchartLineActivity;
import testapp.android.com.echartslearn.html.Html2Activity;
import testapp.android.com.echartslearn.html.HtmlActivity;
import testapp.android.com.echartslearn.im.NeteaseIMActivity;
import testapp.android.com.echartslearn.marquee.MarqueeActivity;
import testapp.android.com.echartslearn.media_player.music.DanikulaPlayActivity;
import testapp.android.com.echartslearn.media_player.vedio.video_press.PressVideoActivity;
import testapp.android.com.echartslearn.notify.NotifyActivity;
import testapp.android.com.echartslearn.permission.PermissionActivity;
import testapp.android.com.echartslearn.swift_list.SwiftListActivity;
import testapp.android.com.echartslearn.view_show.onlayout.OnLayoutActivity;
import testapp.android.com.echartslearn.view_show.pager_page.ViewPagerActivity;
import testapp.android.com.echartslearn.push.push_jiguang.JiGuangPushActivity;
import testapp.android.com.echartslearn.reflect.ReflectActivity;
import testapp.android.com.echartslearn.regular.RegularTestActivity;
import testapp.android.com.echartslearn.shade.ShadeActivity;
import testapp.android.com.echartslearn.show_app.ShowAPPActivity;
import testapp.android.com.echartslearn.view_show.popup.ShowPopupActivity;
import testapp.android.com.echartslearn.view_show.sliding.SlidingActivity;
import testapp.android.com.echartslearn.view_show.sliding.suspend_sliding.SuspendSlidingActivity;
import testapp.android.com.echartslearn.view_show.suspend.SuspendActivity;
import testapp.android.com.echartslearn.view_show.text.SpannerTextActivity;
import testapp.android.com.echartslearn.view_show.text.TestTypefaceActivity;
import testapp.android.com.echartslearn.view_show.text.VariableHeightActivity;
import testapp.android.com.echartslearn.media_player.vedio.video_list.SearchAndShowVedioActivity;
import testapp.android.com.echartslearn.third_recycler.ThirdFragmentOfListActivity;
import testapp.android.com.echartslearn.view_show.image.ImageShowActivity;
import testapp.android.com.echartslearn.wait.WatiActivity;
import testapp.android.com.echartslearn.view_show.zoom_view.ZoomViewActivity;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout rootLayout;
    private Random random = new Random();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        int badgeCount = 10;
//        ShortcutBadger.applyCount(this, badgeCount); //for 1.1.4+
        double f = 3.1315;
        BigDecimal b = new BigDecimal(String.valueOf(f));
        double f1 = b.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,}, 100);

        ScrollView scrollView = new ScrollView(this);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        scrollView.setLayoutParams(lp);
        scrollView.setFillViewport(true);

        rootLayout = new LinearLayout(this);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        rootLayout.setGravity(Gravity.CENTER);
        scrollView.addView(rootLayout);

        setContentView(scrollView);
        createButton(R.id.start, "start");
        createButton(R.id.start_next, "startnext");
        createButton(R.id.echart_line, "echartLine");
        createButton(R.id.to_html, "toHtml");
        createButton(R.id.to_html2, "toHtml2");
        createButton(R.id.echarts_template, "template");
        createButton(R.id.sliding_test, "sliding");
        createButton(R.id.variable_height, "Variable Height Edit");
        createButton(R.id.reflect_button, "reflect activity");
        createButton(R.id.vedio_activity, "VedioActivity");
        createButton(R.id.base_webview, "BASE_WEBVIEW");
        createButton(R.id.netease_IM, "neteaseIm");
        createButton(R.id.array_learn, "arrayLearn");
        createButton(R.id.wait_test, "WaitTest");
        createButton(R.id.onlayout, "onLayout");
        createButton(R.id.spannerText, "spannerText");
        createButton(R.id.to_select_date, "dateSelect");
        createButton(R.id.to_zoom_view, "ZoomView");
        createButton(R.id.to_suspend, "suspend");
        createButton(R.id.to_shade, "shade");
        createButton(R.id.to_test_typeface, "typefaceTest");
        createButton(R.id.to_fragment_base, "baseFragment");
        createButton(R.id.to_double_seek, "doubleSeekBar");
        createButton(R.id.to_test_regular, "testRegular");
        createButton(R.id.to_view_pager, "ViewPager");
        createButton(R.id.to_danikula_play, "danakulaPlay");
        createButton(R.id.to_suspend_sliding, "suspendSliding");
        createButton(R.id.to_banner, "To_Banner");
        createButton(R.id.to_jiguang_push, "to_jiguang_push");
        createButton(R.id.certification_idcard, "Certification_IdCard");
        createButton(R.id.notify_show, "Notify_Show");
        createButton(R.id.show_app, "showAPP");
        createButton(R.id.coordinator_appbar_learn, "CoordinatorLearn");
        createButton(R.id.press_video, "PRESS_Video");
        createButton(R.id.test_activity, "test");
        createButton(R.id.verify_bundle_intent, "bundle_intent");
        createButton(R.id.marquee_custom, "marquee_custom");
        createButton(R.id.image_show, "image_show");
        createButton(R.id.show_popup, "show_popup");
        createButton(R.id.filter_app, "filter_app");
        createButton(R.id.permission_helper, "permission_helper");
        createButton(R.id.swift_list, "swift_list");
        createButton(R.id.camera_launch_mode, "camera_launch_mode");

        testMethod();
    }

    private Button createButton(int id, String text) {
        Button button = new Button(this);
        button.setId(id);
        button.setText(text);
        rootLayout.addView(button);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) button.getLayoutParams();
        lp.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        button.setLayoutParams(lp);
        button.setOnClickListener(this);
        return button;
    }

    //这是发送广播刷新的方法，但不可用
    public void scanFile(Context context, Uri uri) {
        Log.d("MainModuleActivity", uri.getPath());
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(uri);
        context.sendBroadcast(scanIntent);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                ToastUtils.getToast().setDuration(Toast.LENGTH_LONG);
                ToastUtils.show("我是吐司:开始我是吐司:开始我是吐司:开始" + random.nextInt());
                break;
            case R.id.start_next:
                ToastUtils.getToast().setDuration(Toast.LENGTH_SHORT);
                ToastUtils.show("我是吐司" + random.nextInt());
                break;
            case R.id.echart_line:
                startActivity(new Intent(this, EchartLineActivity.class));
                break;
            case R.id.to_html:
                startActivity(new Intent(this, HtmlActivity.class));
                break;
            case R.id.to_html2:
                startActivity(new Intent(this, Html2Activity.class));
                break;
            case R.id.echarts_template:
                startActivity(new Intent(this, testapp.android.com.echartslearn.androidtemplate.MainActivity.class));
                break;
            case R.id.sliding_test:
                startActivity(new Intent(this, SlidingActivity.class));
                break;
            case R.id.variable_height:
                startActivity(new Intent(this, VariableHeightActivity.class));
                break;
            case R.id.reflect_button:
                startActivity(new Intent(this, ReflectActivity.class));
                break;
            case R.id.vedio_activity:
                startActivity(new Intent(this, SearchAndShowVedioActivity.class));
                break;
            case R.id.base_webview:
                startActivity(new Intent(this, BaseWebViewActivity.class));
                break;
            case R.id.netease_IM:
                startActivity(new Intent(this, NeteaseIMActivity.class));
                break;
            case R.id.array_learn:
                startActivity(new Intent(this, ArrayActivity.class));
                break;
            case R.id.wait_test:
                startActivity(new Intent(this, WatiActivity.class));
                break;
            case R.id.onlayout:
                startActivity(new Intent(this, OnLayoutActivity.class));
                break;
            case R.id.spannerText:
                startActivity(new Intent(this, SpannerTextActivity.class));
                break;
            case R.id.to_select_date:
                startActivity(new Intent(this, DateSelectActivity.class));
                break;
            case R.id.to_zoom_view:
                startActivity(new Intent(this, ZoomViewActivity.class));
                break;
            case R.id.to_suspend:
                startActivity(new Intent(this, SuspendActivity.class));
                break;
            case R.id.to_shade:
                startActivity(new Intent(this, ShadeActivity.class));
                break;
            case R.id.to_test_typeface:
                startActivity(new Intent(this, TestTypefaceActivity.class));
                break;
            case R.id.to_fragment_base:
                startActivity(new Intent(this, BaseFragmentActivity.class));
                break;
            case R.id.to_double_seek:
                startActivity(new Intent(this, DoubleSlideSeekBarActivity.class));
                break;
            case R.id.to_test_regular:
                startActivity(new Intent(this, RegularTestActivity.class));
                break;
            case R.id.to_view_pager:
                startActivity(new Intent(this, ViewPagerActivity.class));
                break;
            case R.id.to_danikula_play:
                startActivity(new Intent(this, DanikulaPlayActivity.class));
                break;
            case R.id.to_suspend_sliding:
                startActivity(new Intent(this, SuspendSlidingActivity.class));
                break;
            case R.id.to_jiguang_push:
                startActivity(new Intent(this, JiGuangPushActivity.class));
                break;
            case R.id.certification_idcard:
                startActivity(new Intent(this, CertificationActivity.class));
                break;
            case R.id.notify_show:
                startActivity(new Intent(this, NotifyActivity.class));
                break;
            case R.id.show_app:
                startActivity(new Intent(this, ShowAPPActivity.class));
                break;
            case R.id.coordinator_appbar_learn:
                startActivity(new Intent(this, CoordinatorActivity.class));
                break;
            case R.id.press_video:
                startActivity(new Intent(this, PressVideoActivity.class));
                break;
            case R.id.test_activity:
                startActivity(new Intent(this, ThirdFragmentOfListActivity.class));
                break;
            case R.id.verify_bundle_intent:
                startActivity(new Intent(this, PageAgoActivity.class));
                break;
            case R.id.marquee_custom:
                startActivity(new Intent(this, MarqueeActivity.class));
                break;
            case R.id.image_show:
                startActivity(new Intent(this, ImageShowActivity.class));
                break;
            case R.id.show_popup:
                startActivity(new Intent(this, ShowPopupActivity.class));
                break;
            case R.id.filter_app:
                startActivity(new Intent(this, MainModuleActivity.class));
                break;
            case R.id.permission_helper:
                startActivity(new Intent(this, PermissionActivity.class));
                break;
            case R.id.swift_list:
                startActivity(new Intent(this, SwiftListActivity.class));
                break;
            case R.id.camera_launch_mode:
                startActivity(new Intent(this, LaunchModeCameraActivity.class));
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults[0] == PERMISSION_GRANTED) {
                Log.d("MainModuleActivity", "申请成功");
            }
        }
    }

    private void testMethod() {

        HttpHelper.httpRequire(this, null, "http://ltapi.fangxiaoer.com/apiv1/house/getSubPriceFilter", new HttpHelper.RequestCallback() {
            @Override
            public void doSuccess(Map<String, Object> jsonMap) {

            }

            @Override
            public void doFail(int result) {

            }
        }, false);
    }
}
