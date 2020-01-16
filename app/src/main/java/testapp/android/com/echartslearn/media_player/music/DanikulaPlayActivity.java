package testapp.android.com.echartslearn.media_player.music;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.danikula.videocache.HttpProxyCacheServer;

import java.io.IOException;

import testapp.android.com.echartslearn.R;
import testapp.android.com.echartslearn.application.MyApp;

public class DanikulaPlayActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener{

	private String[] urls = new String[]{
			"http://icloud.fangxiaoer.com/fangxiaoermp3/musicl/2018/12/13150617418.mp3?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=R3MO8J8XMRW2O5JVHHEE%2F20190414%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20190414T064846Z&X-Amz-Expires=86400&X-Amz-SignedHeaders=host&X-Amz-Signature=c86b80eb91fd8be07f2b912cccbe84a953f7e6d211414bcfd57048c6611ea093",
			"http://videos.fangxiaoer.com/web/sy/mp3/2019XESF/0412.mp3",
			"http://videos.fangxiaoer.com/web/sy/mp3/2019XESF/0411.mp3",
			"http://videos.fangxiaoer.com/web/sy/mp3/2019XESF/0410.mp3",
			"http://videos.fangxiaoer.com/web/sy/mp3/2019XESF/0409.mp3",
			"http://videos.fangxiaoer.com/web/sy/mp3/2019XESF/0408.mp3",
			"http://videos.fangxiaoer.com/web/sy/mp3/2019XESF/0404.mp3",
			"http://videos.fangxiaoer.com/web/sy/mp3/2019XESF/0403.mp3",
			"http://videos.fangxiaoer.com/web/sy/mp3/2019XESF/0402.mp3",
			"http://videos.fangxiaoer.com/web/sy/mp3/2019XESF/0401.mp3",
			"http://videos.fangxiaoer.com/web/sy/mp3/2019XESF/0329.mp3"};

	private MediaPlayer mediaPlayer;
	private int position = 0;
	private HttpProxyCacheServer proxy;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (mediaPlayer == null) {
			if (mediaPlayer == null) {
				mediaPlayer = new MediaPlayer();
				mediaPlayer.setOnCompletionListener(this);
			}
		}

		MyApp myApplication = (MyApp) getApplication();
		proxy = myApplication.getProxy(DanikulaPlayActivity.this);
		String proxyUrl = proxy.getProxyUrl(urls[position]);
//		try {
//			mediaPlayer.reset();
//			mediaPlayer.setDataSource(proxyUrl);
//			mediaPlayer.prepare();
//			mediaPlayer.start();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		LinearLayout root = new LinearLayout(this);
		root.setOrientation(LinearLayout.VERTICAL);
		root.setBackgroundColor(0xffffffff);

		LinearLayout btnLayout = new LinearLayout(this);
		btnLayout.setOrientation(LinearLayout.HORIZONTAL);
		btnLayout.setGravity(Gravity.CENTER);
		root.setBackgroundColor(0xff00ff00);
		LinearLayout.LayoutParams btnLayoutLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		root.addView(btnLayout, btnLayoutLP);

		Button ago = new Button(this);
		ago.setText("ago");
		ago.setTextColor(0xff00ffff);
		LinearLayout.LayoutParams agoLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		btnLayout.addView(ago, agoLP);

		Button play = new Button(this);
		play.setTextColor(0xff00ffff);
		play.setText("play");
		LinearLayout.LayoutParams playLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		playLP.leftMargin = (int) getResources().getDimension(R.dimen.dip_30);
		btnLayout.addView(play, playLP);

		Button next = new Button(this);
		next.setText("next");
		next.setTextColor(0xff00ffff);
		LinearLayout.LayoutParams nextLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		nextLP.leftMargin = playLP.leftMargin;
		btnLayout.addView(next, nextLP);

		ListView listView = new ListView(this);
		LinearLayout.LayoutParams listLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		root.addView(listView, listLP);

		setContentView(root);

		ago.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				position--;
				if (position < 0) {
					position = urls.length - 1;
				}
				playMusic(mediaPlayer);
			}
		});

		play.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (position >= urls.length) {
					position = 0;
				}
				playMusic(mediaPlayer);
			}
		});

		next.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				position++;
				if (position >= urls.length) {
					position = 0;
				}
				playMusic(mediaPlayer);
			}
		});

		listView.setAdapter(new MusicPlayAdapter());
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (position >= 0 && position < urls.length) {
					DanikulaPlayActivity.this.position = position;
					playMusic(mediaPlayer);
				}
			}
		});
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		if (!mp.isLooping()) {
			position++;
			if (position >= urls.length) {
				position = 0;
			}
			try {
				mediaPlayer.reset();
				String url = proxy.getProxyUrl(urls[position]);
				mp.setDataSource(url);
				mp.prepare();
				mp.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void playMusic(MediaPlayer mp) {
		try {
			mediaPlayer.reset();
//			String url = proxy.getProxyUrl(urls[position]);
			Uri url = Uri.parse("android.resource://testapp.android.com.echartslearn/raw/msg");
//			mp.setDataSource(url);
			mp.setDataSource(DanikulaPlayActivity.this, url);
			mp.prepare();
			mp.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	class MusicPlayAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return urls == null? 0 : urls.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = createView();
			}

			((TextView) convertView).setText(String.valueOf(position));
			return convertView;
		}

		private View createView() {
			TextView itemText = new TextView(DanikulaPlayActivity.this);
			itemText.setTextSize(15);
			itemText.setTextColor(0xff444444);
			itemText.setGravity(Gravity.CENTER);

			ViewGroup.LayoutParams itemLP = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.dip_50));
			itemText.setLayoutParams(itemLP);

			return itemText;
		}
	}

	@Override
	public void onBackPressed() {
		if (mediaPlayer != null && mediaPlayer.isPlaying()) {
			mediaPlayer.stop();
			mediaPlayer.release();
		}
		super.onBackPressed();
	}
}
