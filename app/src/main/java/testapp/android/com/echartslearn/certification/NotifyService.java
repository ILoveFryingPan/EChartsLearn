package testapp.android.com.echartslearn.certification;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import testapp.android.com.echartslearn.R;
import testapp.android.com.echartslearn.html.HtmlActivity;
import testapp.android.com.echartslearn.notify.NotifyActivity;

public class NotifyService extends Service {

    private int notifyID = 20;
    private int requestCode = 50;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Intent newActivity = new Intent(NotifyService.this, HtmlActivity.class);
        PendingIntent pi = PendingIntent.getActivity(NotifyService.this, requestCode++, newActivity, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            //只在Android O之上需要渠道
            @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel("标题", "描述", NotificationManager.IMPORTANCE_HIGH);
            //如果这里用IMPORTANCE_NOENE就需要在系统的设置里面开启渠道， //通知才能正常弹出
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "标题");
        builder.setSmallIcon(R.mipmap.one).setContentTitle("标题").setContentText("描述").setAutoCancel(true).setContentIntent(pi);
        mNotificationManager.notify(notifyID++, builder.build());

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public ComponentName startService(Intent service) {
        return super.startService(service);
    }
}
