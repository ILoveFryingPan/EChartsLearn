package testapp.android.com.echartslearn.regular;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularTestActivity extends AppCompatActivity{
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String test = "\n\n\r\t我爱北京\n天安门\n\n\n";

		Log.d("RegularTestActivity", test);
		Log.d("RegularTestActivity", replaceBlank(test));
	}

	public String replaceBlank(String str){
		Pattern pt=Pattern.compile("^\\s*|\t|\r|\\s*$");
		Matcher mt=pt.matcher(str);
		str=mt.replaceAll("");
		return str;
	}
}
