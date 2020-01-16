package testapp.android.com.echartslearn.certification;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import testapp.android.com.echartslearn.R;

public class CertificationActivity extends AppCompatActivity implements View.OnClickListener {

	private static final String url = "http://idcardcert.market.alicloudapi.com/idCardCert";
	private static final String appCode = "APPCODE 709741e5007a4ca9b02f8e5ee434f1a4";

	private EditText cardEdit;
	private EditText nameEdit;
	private TextView msgText;
	private Button btn;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(createView());
	}

	private View createView() {

		int dip5 = (int) getResources().getDimension(R.dimen.dip_5);
		int dip15 = (int) getResources().getDimension(R.dimen.dip_15);
		String itemOne = getString(R.string.item_one);

		LinearLayout root = new LinearLayout(this);
		root.setBackgroundColor(0xffffffff);
		root.setOrientation(LinearLayout.VERTICAL);
		root.setPadding(dip15, dip15, dip15, dip15);

		LinearLayout idCardLayout = new LinearLayout(this);
		idCardLayout.setOrientation(LinearLayout.HORIZONTAL);
		idCardLayout.setGravity(Gravity.CENTER_VERTICAL);
		LinearLayout.LayoutParams idCardLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		root.addView(idCardLayout, idCardLP);

		TextView cardText = new TextView(this);
		cardText.setTextColor(0xff000000);
		cardText.setTextSize(15);
		cardText.setText("身份证：");
		LinearLayout.LayoutParams cardTextLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		cardTextLP.rightMargin = dip5;
		idCardLayout.addView(cardText, cardTextLP);

		cardEdit = new EditText(this);
		cardEdit.setBackgroundColor(0xffffffff);
		cardEdit.setHint("身份证号");
		cardEdit.setTextSize(15);
		cardEdit.setTextColor(0xff000000);
		cardEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
		LinearLayout.LayoutParams cardEditLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		idCardLayout.addView(cardEdit, cardEditLP);

		LinearLayout nameLayout = new LinearLayout(this);
		nameLayout.setOrientation(LinearLayout.HORIZONTAL);
		nameLayout.setGravity(Gravity.CENTER_VERTICAL);
		LinearLayout.LayoutParams nameLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		nameLP.topMargin = dip15;
		root.addView(nameLayout, nameLP);

		TextView nameText = new TextView(this);
		nameText.setTextColor(0xff000000);
		nameText.setTextSize(15);
		nameText.setText("姓" + itemOne + "名：");
		LinearLayout.LayoutParams nameTextLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		nameTextLP.rightMargin = dip5;
		nameLayout.addView(nameText, nameTextLP);

		nameEdit = new EditText(this);
		nameEdit.setBackgroundColor(0xffffffff);
		nameEdit.setHint("姓名");
		nameEdit.setTextSize(15);
		nameEdit.setTextColor(0xff000000);
		LinearLayout.LayoutParams nameEditLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		nameLayout.addView(nameEdit, nameEditLP);

		msgText = new TextView(this);
		msgText.setTextColor(0xffff0000);
		msgText.setTextSize(12);
		msgText.setVisibility(View.GONE);
		LinearLayout.LayoutParams msgLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		msgLP.topMargin = dip15;
		root.addView(msgText, msgLP);

		btn = new Button(this);
		btn.setTextColor(0xff000000);
		btn.setTextSize(15);
		btn.setText("验证");
		btn.setGravity(Gravity.CENTER);
		btn.setOnClickListener(this);
		LinearLayout.LayoutParams btnLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		btnLP.topMargin = dip15;
		root.addView(btn, btnLP);

		return root;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == btn.getId()) {
			String cardString = cardEdit.getText().toString().trim();
			String nameString = nameEdit.getText().toString().trim();
			if (TextUtils.isEmpty(cardString) || cardString.length() != 18) {
				msgText.setVisibility(View.VISIBLE);
				msgText.setText("请输入正确的身份证号");
				return;
			}

			if (TextUtils.isEmpty(nameString)) {
				msgText.setVisibility(View.VISIBLE);
				msgText.setText("请输入姓名");
				return;
			}

			verify(cardString, nameString);
		}
	}

	private void verify(String idCard, String name) {
		String getUrl = url + "?idCard=" + idCard + "&name=" + name;
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
				.url(getUrl)
				.header("Authorization", appCode)
				.build();

		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {

			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				final String result = response.body().string();
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						msgText.setVisibility(View.VISIBLE);
						msgText.setText("请求的结果：\n" + result);
					}
				});
			}
		});
	}
}
