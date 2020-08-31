package testapp.android.com.echartslearn.filter;

import android.view.View;

import java.util.Map;

/**
 * 该接口用于对adapter中数据的处理，因为后台数据多数是name和id字段，所以使用的时候好一些
 * 当然有些筛选接口的数据类型可能会坑爹，所以会加一个set方法，把后台数据的字段set进实现类里
 * 因为adapter的样式，逻辑不同，很可能每一种adapter对应一种该接口的实现类，我们要把接口的对象set进入adapter中
 */
public interface FilterControl {

    /**
     * 筛选列表的点击事件
     * @param item      每一个item的数据
     * @param views     要处理的控件，因为每一种列表的样式，逻辑都不同，所以要处理的控件的数量，类型要根据实际情况定
     */
    void filterClick(Map<String, Object> item, View... views);

    //筛选完成后，返回数据，在该接口的实现类中，保存有对数据的处理结果，该方法是把结果返回的
    Map<String, Object> filterData();

    //返回一个int变量的值，表示当前列表是属于普通、多选还是其他的状态
    int getListType();


    public interface FilterDataBack {
        /**
         * 用于返回筛选结果的，每一个FilterControl接口的实现类都要有该接口的对象
         * @param filterParam
         */
        void filterDataBack(Map<String, Object> filterParam);
    }

    public interface FilterConfirm {
        void filterConfirm();
    }
}
