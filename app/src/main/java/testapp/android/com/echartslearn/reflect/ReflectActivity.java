package testapp.android.com.echartslearn.reflect;

import android.content.Context;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import testapp.android.com.echartslearn.R;

public class ReflectActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edit[] = new EditText[4];
    private TextView showText;
    private Person person;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER_HORIZONTAL);
        root.setBackgroundColor(0xfff7f7f7);
        setContentView(root);
//        LinearLayout first = (LinearLayout) createView(LinearLayout.class, this, null, -1, R.id.reflect_first_layout, -1,
//                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.layout_height)),
//                root, null);
//        first.setOrientation(LinearLayout.HORIZONTAL);
//        first.setBackgroundColor(0xffffffff);
//        TextView nameText = (TextView) createView(TextView.class, this, "name:", getResources().getDimension(R.dimen.text_size), R.id.reflect_name_text, -1,
//                new ViewGroup.LayoutParams((int) getResources().getDimension(R.dimen.name_width), ViewGroup.LayoutParams.MATCH_PARENT),
//                first, null);
//        EditText nameEdit = (EditText) createView(EditText.class, this, "", getResources().getDimension(R.dimen.edit_size), R.id.reflect_name_edit, -1,
//                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT),
//                first, null);
        edit[0] = createSet("name:",R.id.reflect_first_layout, R.id.reflect_name_text, R.id.reflect_name_edit, root);
        edit[1] = createSet("sex:", R.id.reflect_second_layout, R.id.reflect_sex_text, R.id.reflect_sex_edit, root);
        edit[2] = createSet("age:", R.id.reflect_third_layout, R.id.reflect_age_text, R.id.reflect_age_edit, root);
        edit[3] = createSet("num:", R.id.reflect_four_layotu, R.id.reflect_num_text, R.id.reflect_num_edit, root);
        edit[2].setInputType(InputType.TYPE_CLASS_NUMBER);
        edit[3].setInputType(InputType.TYPE_CLASS_NUMBER);
        showText = (TextView) createView(TextView.class, this, "", getResources().getDimension(R.dimen.text_size), R.id.reflect_show_attri, -1,
                null, root, null);
        createView(Button.class, this, "getAttr", getResources().getDimension(R.dimen.text_size), R.id.reflect_test_attri, -1,
                null, root, this);
        person = new Person();
    }

    private EditText createSet(String text, int layoutId, int textId, int editId, ViewGroup root){
        LinearLayout first = (LinearLayout) createView(LinearLayout.class, this, null, -1, layoutId, -1,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.layout_height)),
                root, null);
        first.setOrientation(LinearLayout.HORIZONTAL);
        first.setBackgroundColor(0xffffffff);
        TextView nameText = (TextView) createView(TextView.class, this, text, getResources().getDimension(R.dimen.text_size), textId, -1,
                new ViewGroup.LayoutParams((int) getResources().getDimension(R.dimen.name_width), ViewGroup.LayoutParams.MATCH_PARENT),
                first, null);
        EditText editText = (EditText) createView(EditText.class, this, "", getResources().getDimension(R.dimen.edit_size), editId, -1,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT),
                first, null);
        return editText;
    }

    private <T extends View> View createView(Class<T> tClass, Context mContext, String text, float textSize, int id, int imageId,
                                             ViewGroup.LayoutParams lp, ViewGroup root, View.OnClickListener clickListener) {
        try {
            View view = null;
            Constructor<T> con = tClass.getConstructor(new Class[]{Context.class});
            if (tClass.getName().contains("Text") || tClass.getName().contains("Button")) {
                TextView textView = (TextView) con.newInstance(mContext);
                textView.setText(text == null ? "" : text);
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(textSize);
                view = textView;
            } else if (tClass.getName().contains("Image")) {
                ImageView imageView = (ImageView) con.newInstance(mContext);
                imageView.setImageResource(imageId);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                view = imageView;
            } else if (tClass.getName().contains("Layout")) {
                ViewGroup viewGroup = (ViewGroup) con.newInstance(mContext);
                view = viewGroup;
            }
            view.setId(id);
            if (root != null) {
                if (lp != null) {
                    root.addView(view, lp);
                } else {
                    root.addView(view);
                    lp = view.getLayoutParams();
                    lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    view.setLayoutParams(lp);
                }
            }
            int backGauge = (int) mContext.getResources().getDimension(R.dimen.back_gauge);
            view.setPadding(backGauge, backGauge, backGauge, backGauge);
            if (clickListener != null) {
                view.setOnClickListener(clickListener);
            }
            return view;

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reflect_test_attri:
                setPerson();
                Class classP = Person.class;
                try {
                    Field sexField = classP.getDeclaredField("sex");
                    sexField.setAccessible(true);
                    String sex = (String) sexField.get(person);
                    Field ageField = classP.getDeclaredField("age");
                    ageField.setAccessible(true);
                    int age = (int) ageField.get(person);
                    Field nameField = classP.getDeclaredField("name");
                    nameField.setAccessible(true);
                    String name = (String) nameField.get(person);
                    Field numField = classP.getDeclaredField("num");
                    numField.setAccessible(true);
                    int num = (int) numField.get(person);
                    showText.setText("name:" + name + "\t" + "sex:" + sex + "\t" + "age:" + age + "\t" + "num:" + num);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public void setPerson(){
        person.clear();
        person.setName(edit[0].getText().toString().trim());
        person.sex = edit[1].getText().toString().trim();
        person.age = TextUtils.isEmpty(edit[2].getText().toString().trim())? 0 : Integer.valueOf(edit[2].getText().toString().trim());
        person.setNum(TextUtils.isEmpty(edit[3].getText().toString().trim())? 0 : Integer.valueOf(edit[3].getText().toString().trim()));
    }
}
