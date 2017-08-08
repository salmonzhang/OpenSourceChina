package com.example.opensourcechina.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * author:salmonzhang
 * Description:万能的ViewHolder
 * Date:2017/8/8 0008 10:07
 */
public class FinalViewholder extends RecyclerView.ViewHolder{

    public FinalViewholder(View itemView) {
        super(itemView);
    }

    //android中性能高的hashmap
    //但是他的key必须是int类型
    private SparseArray<View> mViewSparseArray = new SparseArray<>();

    //写一个方法,通过id返回一个view
    public View getViewByid(int id) {
        View view = mViewSparseArray.get(id);
        //但是第一次没有
        if (view == null) {
            view = itemView.findViewById(id);
            //第二次要缓存
            mViewSparseArray.put(id, view);
        }
        //到这里一定有数据
        return view;
    }
}
