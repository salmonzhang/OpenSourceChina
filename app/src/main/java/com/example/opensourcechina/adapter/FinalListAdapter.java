package com.example.opensourcechina.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.opensourcechina.bean.FootBean;
import com.example.opensourcechina.bean.HeadBean;
import com.example.opensourcechina.interfaces.BodyType;
import com.example.opensourcechina.interfaces.FootType;
import com.example.opensourcechina.interfaces.HeadType;
import com.example.opensourcechina.interfaces.ItemType;
import com.example.opensourcechina.view.FootViewLayout;
import com.example.opensourcechina.viewHolder.FinalViewholder;

import java.util.ArrayList;
import java.util.List;

/**
 * author:salmonzhang
 * Description:布局为RecyclerView的万能适配器
 * Date:2017/8/8 0008 10:27
 */

public class FinalListAdapter extends RecyclerView.Adapter <FinalViewholder>{

    private List<ItemType> mShowItems = new ArrayList();

    private int mBodyLayout = 0;

    //监听
    private FinalAdapterListener mFinalAdapterListener;


    public FinalListAdapter(List<ItemType> showItems, int bodyLayout, FinalAdapterListener finalAdapterListener) {
        mShowItems = showItems;

        mBodyLayout = bodyLayout;

        this.mFinalAdapterListener = finalAdapterListener;

    }

    //初始化
    //viewType:这是我们条目的类型
    @Override
    public FinalViewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        switch (viewType) {
            case HEADTYPE:

                //布局从外部传入???
                //这个是不是必须的
                //不是必须的
                try {
                    view = LayoutInflater.from(parent.getContext()).inflate(mHeadLyaout, parent, false);

                } catch (Exception e) {

                    throw new RuntimeException("亲，请给个头部");
                }
                break;
            case BODYTYPE:

                //是不是必须的,
                //必须的
                //构造,抽象
                //要不要子类
                //
                try {
                    view = LayoutInflater.from(parent.getContext()).inflate(mBodyLayout, parent, false);

                } catch (Exception e) {

                    throw new RuntimeException("亲，请给个身体");
                }
                break;
            case FOOTTYPE:
                //这里要考虑一下,我们的脚有很多状态,正在加载,失败,没有更多,或者.....
                view = new FootViewLayout(parent.getContext());
                break;

            default:
                break;

        }
        return new FinalViewholder(view);
    }

    //RecyclerView条目点击事件，（接口回调）
    public interface OnRecyclerViewItemClickListener {
        void onclick(List<ItemType> showItems,int position);
    }

    private OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener;

    //点击事件监听
    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.mOnRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }


    //数据更新
    @Override
    public void onBindViewHolder(FinalViewholder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnRecyclerViewItemClickListener != null) {
                    mOnRecyclerViewItemClickListener.onclick(mShowItems,position);
                }
            }
        });

        //谁用谁处理
        int itemViewType = getItemViewType(position);//得到条目类型
        switch (itemViewType) {
            case HEADTYPE:
                mFinalAdapterListener.bindHeadView(holder, position, mShowItems);
                break;
            case BODYTYPE:
                mFinalAdapterListener.bindBodyView(holder, position, mShowItems);

                break;
            case FOOTTYPE:

                mFinalAdapterListener.bindFootView((FootViewLayout) holder.itemView);
                break;

            default:
                break;

        }


    }

    //把数据爆露出去
    //接口回调
    public interface FinalAdapterListener {
        void bindHeadView(FinalViewholder holder, int position, List<ItemType> showItems);

        void bindBodyView(FinalViewholder holder, int position, List<ItemType> showItems);

        void bindFootView(FootViewLayout footViewLayout);
    }
    //接口更新是必须的,那么我们通过构造


    @Override
    public int getItemCount() {
        return mShowItems.size();
    }

    //定义三个类型
    public static final int HEADTYPE = 101;//头部类型
    public static final int BODYTYPE = 102;//身体类型
    public static final int FOOTTYPE = 103;//身体类型

    //根据我们的数据返回不同的类型
    @Override
    public int getItemViewType(int position) {
        if (mShowItems.get(position) instanceof HeadType) {
            return HEADTYPE;
        }
        if (mShowItems.get(position) instanceof BodyType) {
            return BODYTYPE;
        }
        if (mShowItems.get(position) instanceof FootType) {
            return FOOTTYPE;
        }
        //如果上面都不是,那么走到下面,但是一般不会
        return BODYTYPE;
    }

    //刷新数据
    //用来控制头部或者脚部显示的方法
    public void updateData() {

        //删除所有的头
        for (int i = 0; i < mShowItems.size(); i++) {
            if (mShowItems.get(i) instanceof HeadType) {
                mShowItems.remove(i);
            }
        }

        //添加头
        //如果用户传入了头部布局,那么我们就显示头部,如果没有传入我们就不显示
        if (mHeadLyaout > 0) {
            //加头,加个假头,用来显示头部的位置的
            mShowItems.add(0, new HeadBean());
        }

        //删除脚部
        for (int i = 0; i < mShowItems.size(); i++) {
            if (mShowItems.get(i) instanceof FootType) {
                mShowItems.remove(i);
            }
        }

        //显示的
        if (mIsShowFoot) {
            mShowItems.add(new FootBean());
        }

        //刷新数据
        notifyDataSetChanged();
    }

    private int mHeadLyaout = 0;//头部布局

    //钩子方法
    //脚部传入的方法
    public void setHeadLayout(int headLayout) {
        this.mHeadLyaout = headLayout;
    }

    //是否显示脚部
    private boolean mIsShowFoot = false;//默认是false,框架设计的时候只需要满足基本需求

    public void isShowFoot(boolean isShowFoot) {
        this.mIsShowFoot = isShowFoot;
    }
}
