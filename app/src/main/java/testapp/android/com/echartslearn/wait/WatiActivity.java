package testapp.android.com.echartslearn.wait;


import java.util.Random;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class WatiActivity extends AppCompatActivity {
    //}
//public class Demo extends android.app.Activity {
    private class Token {
        private String flag;

        public Token() {
            Log.d("Token", Thread.currentThread().getName());
            setFlag(null);
        }

        public void start() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 20; i++) {
                        Log.d("Token", "token正在执行");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getFlag() {
            return flag;
        }
    }

    private Token token = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button btn = new Button(this);
        btn.setText("start");

        setContentView(btn);
//        Button btn = (Button) findViewById(R.id.button1);
        token = new Token();
        Log.d("WatiActivity", "oncreate:" + Thread.currentThread().getName());
        if (token.getFlag() == null)
            Log.v("A", "the token flag value is null");
        else
            Log.v("A", "the token flag value is" + token.getFlag());
        btn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                token.start();

                Log.d("WatiActivity", Thread.currentThread().getName());
                // TODO Auto-generated method stub
                WorkThread workthread = new WorkThread();
                workthread.start();
                Random r = new Random();
                Log.d("WatiActivity", "开始睡眠");
//                synchronized (token) {
//                    try {
//                        token.wait();
//                        Log.v("Main", "the value is " + token.getFlag());
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
                Log.d("WatiActivity", "可以继续向下执行");
                for (int i = 0; i < 10; i++) {
                    try {
                        int time = (r.nextInt(9) + 1) * 1000;
                        Log.d("WatiActivity", "睡眠时间");
                        Thread.sleep(time);        //增加不确定性,至少睡眠1秒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                    synchronized (token) {
//                        token.setFlag("wangmz" + Integer.toString(i));
                        token.notifyAll();
//                        Log.v("Main", Integer.toString(i));
                    }
            }
        });
    }


    private class WorkThread extends Thread {
        @Override
        public void run() {
            Random r = new Random();
            Log.d("WorkThread", Thread.currentThread().getName());
//            while (true) {
//              try {
//                  Thread.sleep((r.nextInt()+1)*1000);//可能在sleep的时候其他线程执行notify()。但此时对这个线程不起作用。所以结果不会按顺序出现
//              } catch (InterruptedException e1) {
//                  e1.printStackTrace();
//              }
            synchronized (token) {
                try {
                    token.wait();
                    Log.v("Main", "the value is " + token.getFlag());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            for (int i = 0; i < 10; i++) {
                try {
                    int time = (r.nextInt(9) + 1) * 1000;
                    Log.d("WatiActivity", "thread:" + "睡眠时间");
                    Thread.sleep(time);        //增加不确定性,至少睡眠1秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
                synchronized (token) {
//                    token.setFlag("wangmz" + Integer.toString(i));
                    token.notifyAll();
//                    Log.v("Main", Integer.toString(i));
                }
            Log.v("work", "while!!");
        }
    }
//    }
}
