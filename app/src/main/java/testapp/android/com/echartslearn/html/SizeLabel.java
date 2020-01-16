package testapp.android.com.echartslearn.html;

import android.content.Context;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;

import org.xml.sax.XMLReader;

public class SizeLabel implements Html.TagHandler {
    private int size;
    private int startIndex = 0;
    private int stopIndex = 0;

    private Context mContext;

    public SizeLabel(Context mContext, int size) {
        this.mContext = mContext;
        this.size = size;
    }

    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
        Log.d("SizeLabel", "output：" + output.toString());
        Log.d("SizeLabel", "tag：" + tag);
        if(tag.toLowerCase().contains("size")) {
            if(opening){
                startIndex = output.length();
            }else{
                String sizeStr = tag.toLowerCase().substring(tag.indexOf("-") + 1);
                stopIndex = output.length();
                output.setSpan(new AbsoluteSizeSpan(dip2px(Integer.parseInt(sizeStr))), startIndex, stopIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }

    public int dip2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}