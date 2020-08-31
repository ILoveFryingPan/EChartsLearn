package testapp.android.com.echartslearn.view_show.progress;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import testapp.android.com.echartslearn.R;

public class ProgressBarActivity extends AppCompatActivity {

    private int mTotalProgress;
    private int mCurrentProgress;
    private RoundProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_progress);
        initView();
        initVariable();

        new Thread(new ProgressRunable()).start();
    }

    private void initVariable() {
        mTotalProgress = 100;
        mCurrentProgress = 0;
    }

    private void initView() {
        ProgressBar first = findViewById(R.id.first_bar);
        ProgressBar second = findViewById(R.id.second_bar);
//        ProgressBar third = findViewById(R.id.third_bar);
        progressBar = findViewById(R.id.roundProgressBar04_id);

        first.setVisibility(View.VISIBLE);
        second.setVisibility(View.VISIBLE);
    }

    class ProgressRunable implements Runnable {

        @Override
        public void run() {
            while (true) {
                if (mCurrentProgress >= mTotalProgress) {
                    mCurrentProgress = 0;
                }
                mCurrentProgress += 1;

                progressBar.setProgress(mCurrentProgress);

                try {
                    Thread.sleep(100);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
