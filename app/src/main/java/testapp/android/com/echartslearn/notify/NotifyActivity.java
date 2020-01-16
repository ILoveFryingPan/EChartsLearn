package testapp.android.com.echartslearn.notify;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import java.net.URISyntaxException;

import testapp.android.com.echartslearn.MainActivity;
import testapp.android.com.echartslearn.R;
import testapp.android.com.echartslearn.certification.NotifyService;
import testapp.android.com.echartslearn.show_app.ShowAPPActivity;

public class NotifyActivity extends AppCompatActivity {

    private int notifyID = 10;
    private int requestCode = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button show = new Button(this);
        show.setText("显示通知");
        show.setGravity(Gravity.CENTER);
        show.setTextSize(30);
        setContentView(show);

        ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        String activityName = am.getRunningTasks(1).get(0).topActivity.getClassName();

//        final Intent notifyIntent = new Intent();
//        notifyIntent.setPackage("testapp.android.com.echartslearn");
//        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//        notifyIntent.setComponent(new ComponentName("testapp.android.com.echartslearn", activityName));
//        Log.d("NotifyActivity", "uri：" + notifyIntent.toUri(Intent.URI_INTENT_SCHEME));
        Intent notifyIntent = null;
        try {
            notifyIntent = Intent.parseUri("intent:#Intent;action=com.jiaheng.agent.activity.HtmlShowActivity;component=com.jiaheng.agency_im/com.jiaheng.agent.activity.HtmlShowActivity;S.startSource=push;S.jty=1;S.url=https://m.fangxiaoer.com/news/78353.htm;S.share=2;S.title=8月份沈新房价格环比涨0.8%;S.taskId=GT_0918_6952bcd54602c6ed7a87c559d8ffc060;S.messageId=c087f854-8a9-16d40225abe-19588904701;end", Intent.URI_INTENT_SCHEME);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        final Intent finalNotifyIntent = notifyIntent;
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//                notifyIntent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                PendingIntent pi = PendingIntent.getActivity(NotifyActivity.this, requestCode++, finalNotifyIntent, PendingIntent.FLAG_CANCEL_CURRENT);

                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    //只在Android O之上需要渠道
                    @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel("标题", "描述", NotificationManager.IMPORTANCE_HIGH);
                    //如果这里用IMPORTANCE_NOENE就需要在系统的设置里面开启渠道， //通知才能正常弹出
                    mNotificationManager.createNotificationChannel(notificationChannel);
                }
                NotificationCompat.Builder builder = new NotificationCompat.Builder(NotifyActivity.this, "标题");
                builder.setSmallIcon(R.mipmap.one).setContentTitle("标题").setContentText("描述").setAutoCancel(true).setContentIntent(pi);
                Notification notification = builder.build();
                notification.flags = Notification.FLAG_AUTO_CANCEL;
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    builder
//                            .setGroupSummary(false)
//                            .setGroup("group");
//                }
                mNotificationManager.notify(notifyID++, notification);
//                Intent service = new Intent(NotifyActivity.this, NotifyService.class);
//                startService(service);
            }
        });
    }
}
