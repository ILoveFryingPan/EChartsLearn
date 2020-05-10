package com.example.zxing.qrcode;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ToolHelper {

	//这个代码是生成控件的id的
	public static int generateViewId() {
		int ids;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			ids = View.generateViewId();
		} else {
			AtomicInteger sNextGeneratedId = new AtomicInteger(1);
			for (; ; ) {
				final int result = sNextGeneratedId.get();
				// aapt-generated IDs have the high byte nonzero; clamp to the range under that.
				int newValue = result + 1;
				if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
				if (sNextGeneratedId.compareAndSet(result, newValue)) {
					ids = result;
					break;
				}
			}
		}
		return ids;
	}

	//用于判断两个字符串是否一致
	//相同返回false，不同返回true
	public static boolean checkDifferent(String one, String two) {
		if (TextUtils.isEmpty(one) && TextUtils.isEmpty(two)) {
			return false;
		} else if (TextUtils.isEmpty(one)) {
			return true;
		} else if (one.equals(two)) {
			return false;
		} else if (TextUtils.isEmpty(two)) {
			return true;
		} else if (one.trim().equals(two.trim())) {
			return false;
		} else {
			return true;
		}
	}

	public static int getVirtualBarHeight(Context context) {
		int vh = 0;
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		DisplayMetrics dm = new DisplayMetrics();
		try {
			@SuppressWarnings("rawtypes") Class c = Class.forName("android.view.Display");
			@SuppressWarnings("unchecked") Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
			method.invoke(display, dm);
			vh = dm.heightPixels - display.getHeight();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (isMIUI()) {
			if (isFullScreen(context)) {
				vh = 0;
			}
		} else {
			if (!hasDeviceNavigationBar(context)) {
				vh = 0;
			}
		}
		return vh;
	}

	/**
	 * 获取是否有虚拟按键 * 通过判断是否有物理返回键反向判断是否有虚拟按键 * * @param context * @return
	 */
	public static boolean hasDeviceNavigationBar(Context context) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
			Point size = new Point();
			Point realSize = new Point();
			display.getSize(size);
			display.getRealSize(realSize);
			boolean result = realSize.y != size.y;
			return realSize.y != size.y;
		} else {
			boolean menu = ViewConfiguration.get(((Activity) context)).hasPermanentMenuKey();
			boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
			if (menu || back) {
				return false;
			} else {
				return true;
			}
		}
	}

	public static boolean isFullScreen(Context context) {
		// true 是手势，默认是 false // https://www.v2ex.com/t/470543
		return Settings.Global.getInt(context.getContentResolver(), "force_fsg_nav_bar", 0) != 0;
	}

	public static boolean isMIUI() {
		String manufacturer = Build.MANUFACTURER; // 这个字符串可以自己定义,例如判断华为就填写huawei,魅族就填写meizu
		if ("xiaomi".equalsIgnoreCase(manufacturer)) {
			return true;
		}
		return false;
	}

	private static String getPermissionName (String permission) {
		switch (permission) {
			case Manifest.permission.WRITE_EXTERNAL_STORAGE:
			case Manifest.permission.READ_EXTERNAL_STORAGE:
				return "存储";
			case Manifest.permission.CAMERA:
				return "相机";
			case Manifest.permission.CALL_PHONE:
				return "电话";
			case Manifest.permission.READ_PHONE_STATE:
				return "通讯录";
			case Manifest.permission.RECORD_AUDIO:
				return "麦克风";
			case Manifest.permission.ACCESS_COARSE_LOCATION:
			case Manifest.permission.ACCESS_FINE_LOCATION:
				return "位置";
				default:
					return permission;
		}
	}


	/**
	 * 获取当前本地apk的版本
	 *
	 * @param mContext
	 * @return
	 */
	public static int getVersionCode(Context mContext) {
		int versionCode = 0;
		try {
			//获取软件版本号，对应AndroidManifest.xml下android:versionCode
			versionCode = mContext.getPackageManager().
					getPackageInfo(mContext.getPackageName(), 0).versionCode;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}

	/**
	 * 把bitmap画到一个白底的newBitmap上,将newBitmap返回
	 * @param context 上下文
	 * @param bitmap 要绘制的位图
	 * @return Bitmap
	 */
	public static Bitmap drawableBitmapOnWhiteBg(Context context, Bitmap bitmap){
		if (null == bitmap) {
			return null;
		}
		Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(newBitmap);
		canvas.drawColor(context.getResources().getColor(android.R.color.white));
		Paint paint=new Paint();
		canvas.drawBitmap(bitmap, 0, 0, paint); //将原图使用给定的画笔画到画布上
		return newBitmap;
	}

	public static int getBarHeight(Context mContext) {
		int statusBarHeight = 0;
		int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			statusBarHeight = mContext.getResources().getDimensionPixelSize(resourceId);
		}
		return statusBarHeight;
	}

	public static void testLogShow(Context mContext) {
		Intent intent = new Intent();
		intent.setComponent(new ComponentName("com.jiaheng.agency_im", "com.jiaheng.agent.activity.BiddingActivity"));
		intent.setAction("com.jiaheng.agent.activity.BiddingActivity");
		intent.putExtra("startSource", "push");
		String uri = intent.toUri(Intent.URI_INTENT_SCHEME);
		Log.d("ToolHelper", "uri:" + uri);
//		String cid = PushManager.getInstance().getClientid(mContext);
//		Log.d("ToolHelper", "cid：" + cid);

		ActivityManager am = (ActivityManager)mContext.getSystemService(Context.ACTIVITY_SERVICE);
		String activityName = am.getRunningTasks(1).get(0).topActivity.getClassName();
		Log.d("ToolHelper", activityName);
	}
}
