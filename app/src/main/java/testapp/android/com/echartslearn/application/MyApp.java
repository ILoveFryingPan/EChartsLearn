package testapp.android.com.echartslearn.application;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.danikula.videocache.HttpProxyCacheServer;
import com.danikula.videocache.file.FileNameGenerator;
import com.hjq.toast.ToastUtils;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.util.NIMUtil;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.jpush.android.api.JPushInterface;
import testapp.android.com.echartslearn.R;
import testapp.android.com.echartslearn.im.NeteaseIMActivity;

public class MyApp extends Application{
    public static String sessionId = null;
    public static String token = null;
    public static String accid = null;

	private HttpProxyCacheServer proxy;

	public static HttpProxyCacheServer getProxy(Context context) {
		MyApp myApplication = (MyApp) context.getApplicationContext();
		return myApplication.proxy == null ? (myApplication.proxy = myApplication.newProxy()) : myApplication.proxy;
	}

	private HttpProxyCacheServer newProxy() {
		return new HttpProxyCacheServer.Builder(this)
				.maxCacheFilesCount(20)
//				.cacheDirectory(Utils.getVideoCacheDir(this))
				.cacheDirectory(new File(getExternalFilesDir("music"), "audio-cache"))
				.fileNameGenerator(new MyFileNameGenerator())
				.build();
	}

	public class MyFileNameGenerator implements FileNameGenerator {
		// Urls contain mutable parts (parameter 'sessionToken') and stable video's id (parameter 'videoId').
		// e. g. http://example.com?guid=abcqaz&sessionToken=xyz987
		public String generate(String url) {
//			Uri uri = Uri.parse(url);
//			String audioId = uri.getQueryParameter("guid");
			String audioId = getId(url);
			return audioId + ".mp3";
		}

		private String getId(String url) {
			String regEx = "[^0-9]";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(url);
			return m.replaceAll("").trim();
		}
	}

    @Override
    public void onCreate() {
        super.onCreate();
        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        // SDK初始化（启动后台服务，若已经存在用户登录信息， SDK 将完成自动登录）
        NIMClient.init(this, loginInfo(), options());

        // ... your codes
        if (NIMUtil.isMainProcess(this)) {
//             注意：以下操作必须在主进程中进行
//             1、UI相关初始化操作
//             2、相关Service调用
        }

        //极光推送
		JPushInterface.setDebugMode(true);
		JPushInterface.init(this);
//		吐司初始化
        ToastUtils.init(this);
    }

    // 如果返回值为 null，则全部使用默认参数。
    private SDKOptions options() {
        SDKOptions options = new SDKOptions();
        // 如果将新消息通知提醒托管给 SDK 完成，需要添加以下配置。否则无需设置。
        StatusBarNotificationConfig config = new StatusBarNotificationConfig();
//        config.notificationEntrance = WelcomeActivity.class; // 点击通知栏跳转到该Activity
        config.notificationEntrance = NeteaseIMActivity.class; // 点击通知栏跳转到该Activity
//        config.notificationSmallIconId = R.drawable.ic_stat_notify_msg;
        config.notificationSmallIconId = R.mipmap.exer;
        // 呼吸灯配置
        config.ledARGB = Color.GREEN;
        config.ledOnMs = 1000;
        config.ledOffMs = 1500;
        // 通知铃声的uri字符串
        config.notificationSound = "android.resource://com.netease.nim.demo/raw/msg";
        options.statusBarNotificationConfig = config;

        // 配置保存图片，文件，log 等数据的目录
        // 如果 options 中没有设置这个值，SDK 会使用采用默认路径作为 SDK 的数据目录。
        // 该目录目前包含 log, file, image, audio, video, thumb 这6个目录。
//        String sdkPath = getAppCacheDir(context) + "/nim"; // 可以不设置，那么将采用默认路径
        // 如果第三方 APP 需要缓存清理功能， 清理这个目录下面个子目录的内容即可。
//        options.sdkStorageRootPath = sdkPath;

        // 配置是否需要预下载附件缩略图，默认为 true
        options.preloadAttach = true;

        // 配置附件缩略图的尺寸大小。表示向服务器请求缩略图文件的大小
        // 该值一般应根据屏幕尺寸来确定， 默认值为 Screen.width / 2
//        options.thumbnailSize = ${Screen.width} / 2;
        options.thumbnailSize = getScreenWidth(this) / 2;

        // 用户资料提供者, 目前主要用于提供用户资料，用于新消息通知栏中显示消息来源的头像和昵称
//        options.userInfoProvider = new UserInfoProvider() {
//            @Override
//            public UserInfo getUserInfo(String account) {
//                return null;
//            }
//
////            @Override
////            public int getDefaultIconResId() {
//////                return R.drawable.avatar_def;
////                return R.mipmap.exer;
////            }
////
////            @Override
////            public Bitmap getTeamIcon(String tid) {
////                return null;
////            }
////
////            @Override
////            public Bitmap getAvatarForMessageNotifier(String account) {
////                return null;
////            }
//
//            @Override
//            public String getDisplayNameForMessageNotifier(String account, String sessionId,
//                                                           SessionTypeEnum sessionType) {
//                return null;
//            }
//
//            @Override
//            public Bitmap getAvatarForMessageNotifier(SessionTypeEnum sessionType, String sessionId) {
//                return ;
//            }
//        };
        return options;
    }

    // 如果已经存在用户登录信息，返回LoginInfo，否则返回null即可
    private LoginInfo loginInfo() {
        return null;
    }

    /** * 获取屏幕的宽 * * @param context * @return */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }


//    作者：酸甜小黄瓜
//    链接：https://www.jianshu.com/p/a1ab688d7ef8
//    來源：简书
//    简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。
}
