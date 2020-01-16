package testapp.android.com.echartslearn.show_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import java.net.URISyntaxException;

public class ShowAPPActivity extends AppCompatActivity {

    private final String messageUri = "intent:#Intent;action=com.jiaheng.agent.function_package.house.house_log.MessageCenterActivity;component=com.jiaheng.agency_im/com.jiaheng.agent.function_package.house.house_log.MessageCenterActivity;S.startSource=push;end";
    private final String robUri = "intent:#Intent;action=com.jiaheng.agent.function_package.mainpage.fourmodule.rob.RobCustomerResourceActivity;component=com.jiaheng.agency_im/com.jiaheng.agent.function_package.mainpage.fourmodule.rob.RobCustomerResourceActivity;S.startSource=push;end";
    private final String dzb = "intent:com.jiaheng.fangxiaoer_public://splash/open#Intent;category=android.intent.category.DEFAULT;category=android.intent.category.BROWSABLE;end";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button showBtn = new Button(this);
        showBtn.setGravity(Gravity.CENTER);
        showBtn.setTextSize(30);
        showBtn.setTextColor(0xff000000);
        showBtn.setText("显示APP");
        setContentView(showBtn);

        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showApp = null;
                try {
                    showApp = Intent.parseUri(dzb, Intent.URI_INTENT_SCHEME);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
//                showApp.setAction("intent:#Intent;action=com.jiaheng.agent.function_package.mainpage.fourmodule.rob.RobCustomerResourceActivity;component=com.jiaheng.agency_im/com.jiaheng.agent.function_package.mainpage.fourmodule.rob.RobCustomerResourceActivity;S.startSource=push;end");
//                showApp.setAction("intent:#Intent;action=com.jiaheng.agent.function_package.house.house_log.MessageCenterActivity;component=com.jiaheng.agency_im/com.jiaheng.agent.function_package.house.house_log.MessageCenterActivity;S.startSource=push;end");

                startActivity(showApp);
            }
        });
    }
}
