package testapp.android.com.echartslearn.push.push_jiguang;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import cn.jpush.android.api.JPushInterface;
import testapp.android.com.echartslearn.R;

public class JiGuangPushActivity extends AppCompatActivity implements View.OnClickListener{
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout root = new LinearLayout(this);
		root.setBackgroundColor(0xffffffff);
		root.setOrientation(LinearLayout.VERTICAL);
		root.setGravity(Gravity.CENTER);

		Button registrationID = new Button(this);
		registrationID.setTextColor(0xff333333);
		registrationID.setText("registrationID");
		registrationID.setId(R.id.push_registration_id);
		registrationID.setOnClickListener(this);
		LinearLayout.LayoutParams registrationIDLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		root.addView(registrationID, registrationIDLP);

		setContentView(root);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.push_registration_id:
				String rid = JPushInterface.getRegistrationID(getApplicationContext());
				if (!rid.isEmpty()) {
					Log.d("JiGuangPushActivity", "RegId:" + rid);
				} else {
					Toast.makeText(this, "Get registration fail, JPush init failed!", Toast.LENGTH_SHORT).show();
				}
				break;
		}
	}
}
