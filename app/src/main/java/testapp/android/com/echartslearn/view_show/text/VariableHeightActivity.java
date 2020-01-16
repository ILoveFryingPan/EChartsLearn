package testapp.android.com.echartslearn.view_show.text;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import testapp.android.com.echartslearn.R;

public class VariableHeightActivity extends AppCompatActivity{

    private TextView showText;
    private EditText input;
    private TextView inputEnter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_height_variable);
        showText = findViewById(R.id.show_text);
        input = findViewById(R.id.input_edit);
        inputEnter = findViewById(R.id.input_enter);
        inputEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showText.setText(input.getText().toString().trim());
                input.setText("");
            }
        });
    }
}
