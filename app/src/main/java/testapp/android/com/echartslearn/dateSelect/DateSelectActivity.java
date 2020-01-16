package testapp.android.com.echartslearn.dateSelect;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

import testapp.android.com.echartslearn.R;

public class DateSelectActivity extends AppCompatActivity{
	private TextView timeShow;

	//定义一个TextView控件对象,显示得到的时间日期

	private Button btn_date;

	private Button btn_time;

	DateFormat format =  DateFormat.getDateTimeInstance();

	//获取日期格式器对象

	Calendar calendar = Calendar.getInstance(Locale.CHINA);

	//获取日期格式器对象

	@Override

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

//		setContentView(R.layout.activity_main_module);

		LinearLayout root = new LinearLayout(this);
		root.setOrientation(LinearLayout.VERTICAL);

		timeShow = new TextView(this);
		timeShow.setTextSize(15);
		timeShow.setTextColor(0xff333333);
		timeShow.setGravity(Gravity.CENTER_VERTICAL);
		LinearLayout.LayoutParams timeShowLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		root.addView(timeShow, timeShowLP);

//		timeShow = ((TextView) findViewById(R.id.time));

		LinearLayout childLayout = new LinearLayout(this);
		childLayout.setOrientation(LinearLayout.HORIZONTAL);
		LinearLayout.LayoutParams layoutLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		root.addView(childLayout, layoutLP);

		btn_date = new Button(this);
		btn_date.setText("btn_date");
		LinearLayout.LayoutParams dateLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		childLayout.addView(btn_date, dateLP);

//		btn_date = ((Button) findViewById(R.id.btn_date));

		//得到页面设定日期的按钮控件对象

		btn_time = new Button(this);
		btn_time.setText("btn_time");
		LinearLayout.LayoutParams timeLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		childLayout.addView(btn_time, timeLP);
//		btn_time = ((Button) findViewById(R.id.btn_time));
		setContentView(root);
		updateTimeShow();

		//将页面TextView的显示更新为最新时间

		btn_date.setOnClickListener(new View.OnClickListener() {

			//设置按钮的点击事件监听器

			@Override

			public void onClick(View v) {

				//生成一个DatePickerDialog对象，并显示。显示的DatePickerDialog控件可以选择年月日，并设置

				DatePickerDialog datePickerDialog = new DatePickerDialog(DateSelectActivity.this, new DatePickerDialog.OnDateSetListener() {

					@Override

					public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

						//修改日历控件的年，月，日

						//这里的year,monthOfYear,dayOfMonth的值与DatePickerDialog控件设置的最新值一致

						calendar.set(Calendar.YEAR,year);

						calendar.set(Calendar.MONTH,month);

						calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

						updateTimeShow();

					}

				}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

				datePickerDialog.show();

				updateTimeShow();

				//将页面TextView的显示更新为最新时间

			}
		});

		btn_time.setOnClickListener(new View.OnClickListener() {

			@Override

			public void onClick(View v) {

				TimePickerDialog timePickerDialog = new TimePickerDialog(DateSelectActivity.this, new TimePickerDialog.OnTimeSetListener() {

					@Override

					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

						//同DatePickerDialog控件

						calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);

						calendar.set(Calendar.MINUTE,minute);

					}

				},calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true);

				timePickerDialog.show();

				updateTimeShow();

				//将页面TextView的显示更新为最新时间

			}

		});

	}

	private void updateTimeShow(){

		//将页面TextView的显示更新为最新时间

		timeShow.setText(format.format(calendar.getTime()));

	}
}
