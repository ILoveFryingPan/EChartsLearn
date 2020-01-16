package testapp.android.com.echartslearn.array;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] arrayS = new String[5];
        arrayS[0] = "one";
        arrayS[1] = "two";
        arrayS[2] = "three";

        List<String> arraySlist = Arrays.asList(arrayS);
        Log.d("ArrayActivity", "arraySlist.size():" + arraySlist);

        List<String> sList = new ArrayList<>(5);
        sList.clear();
        sList.add("begin");
        for (int i = 2; i < 5; i++) {
            sList.add(null);
//            sList.set(i, "数据" + i);
        }
        sList.add("end");
//        sList.addAll(null);   addAll的参数不能为空，否则会报空指针异常
//        System.out.println(sList);
        Log.d("ArrayActivity", "sList:" + sList);
    }
}
