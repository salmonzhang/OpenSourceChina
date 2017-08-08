package com.example.opensourcechina.fragment;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.opensourcechina.R;
import com.example.opensourcechina.adapter.FinalListAdapter;
import com.example.opensourcechina.interfaces.FootType;
import com.example.opensourcechina.interfaces.ItemType;
import com.example.opensourcechina.utils.ToastUtil;
import com.example.opensourcechina.utils.Utils;
import com.example.opensourcechina.view.FootViewLayout;
import com.example.opensourcechina.viewHolder.FinalViewholder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * author:salmonzhang
 * Description:布局内容显示为RecyclerView的BaseRecyclerViewFragment
 * Date:2017/8/8 0008 14:57
 */

public abstract class BaseRecyclerViewFragment extends BaseFragment implements FinalListAdapter.FinalAdapterListener, FinalListAdapter.OnRecyclerViewItemClickListener{

    @Bind(R.id.rv_new_list_layout)
    RecyclerView mRvNewListLayout;

    @Bind(R.id.srefresh_new_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private List<ItemType> mShowItems = new ArrayList<>();
    private FinalListAdapter mNewsListAdapter;


    @Override
    protected View getSuccessView() {
        View view = View.inflate(getContext(), R.layout.fragment_new, null);
        ButterKnife.bind(this, view);

        init();
        return view;
    }

    private void init() {
        //布局设置
        mRvNewListLayout.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        //设置数据
        mNewsListAdapter = new FinalListAdapter(mShowItems,createBodyView(),this);
        mRvNewListLayout.setAdapter(mNewsListAdapter);

        //设置下拉刷新
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //如果当前的状态是空闲状态,那么我们应该给个执行下拉动作
                if (mLOADSTATE == LOADSTATE.NONE) {
                    //下拉刷新
                    //重新请求数据
                    //使用框架重新请求数据
                    //刷新数据前告诉系统是什么状态
                    mLOADSTATE = LOADSTATE.LOADING;//下拉
                    refresh();
                } else {
                    ToastUtil.showToast("亲,正在下拉中请勿刷新!");
                }
            }
        });

        //上拉加载更多数据
        mRvNewListLayout.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                //如果是空闲我们进行上拉操作
                if (mLOADSTATE == LOADSTATE.NONE) {

                    //我们找到最后一个条目
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    //最后一条
                    int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();

                    //当前最后一条显示是我们集合的最后一个元素,从滚动到停止时去判断,如果有底部刷新我们才能去上拉加载更多
                    if (lastVisibleItemPosition == mShowItems.size() - 1 && newState == RecyclerView.SCROLL_STATE_IDLE && mShowItems.get(mShowItems.size()-1) instanceof FootType) {
                        //去请求数据
                        mLOADSTATE = LOADSTATE.MORELOAD;//上拉加载更多,这个一定要在刷新之前
                        refresh();
                    }
                } else {
                    ToastUtil.showToast("亲,别扯了!这次真没有了!");
                }
            }
        });

        //设置 刷新控件颜色
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED,Color.GREEN,Color.BLUE);

        //显示头部
        mNewsListAdapter.setHeadLayout(getHeadView());

        //传入显示脚部
        mNewsListAdapter.isShowFoot(isRefreshShowFoot());

        //设置控件
        mSwipeRefreshLayout.setEnabled(isRefreshShow());

        //设置点击监听
        mNewsListAdapter.setOnRecyclerViewItemClickListener(this);
    }

    @Override
    public void onclick(List<ItemType> showItems,int position) {

    }


    //使用枚举定义刷新的状态
    public enum LOADSTATE {
        NONE,//空闲
        LOADING,//下拉刷新
        MORELOAD;//上拉加载更多
    }

    //定义当前的状态
    private LOADSTATE mLOADSTATE = LOADSTATE.NONE;//空闲


    @Override
    protected Object rquestData() {

        //recyclerView操作数据永远在请求数据之后

        //请求头部
        //不是上拉加载更多的时候才去请求头部数据
        if (mLOADSTATE != LOADSTATE.MORELOAD) {
            //请求头部数据
            //mNewHeadBean = LoadData.getInstance().getBeanData("http://www.oschina.net/action/apiv2/banner?catalog=1", NewHeadBean.class);
            requestHeadData();
        }

        //请求body的数据
        List<? extends ItemType> items = getItemDatas(mLOADSTATE);

        //数据判断的逻辑
        //1. 如果总集合的数据大于0
        //1.1 如果有数据,添加
        //1.1.1  下拉刷新,  清空以前的数据,再添加数据
        //1.1.2 上拉加载更多 直接添加 不处理
        //1.2 如果第一次有数据,但是第二次没有数据
        //提示用户网络异常
        //不清空数据,给用户 一个良好的体验
        //1.2.1 下拉刷新 要注意不能清空 不处理
        //1.2.1 上拉加载更多 要注意不能添加 不处理

        //2. 如果总集合的数据等于0
        //2.1 如果有数据,那么添加
        //2.1.1:如果下拉刷新,可清可不清,因为以前数据就是0 不处理
        //2.1.2 :如果上拉刷新,添加数据  不处理
        //2.2 如果没有数据,返回我们的null
        //2.2.1:下拉刷新, 不处理
        //2.2.2:上拉加载更多,不处理


        //1. 如果总集合的数据大于0
        //1.1 如果有数据,添加
        //1.1.1  下拉刷新,  清空以前的数据,再添加数据
        //1.1.2 上拉加载更多 直接添加 不处理
        //1.2 如果第一次有数据,但是第二次没有数据
        //提示用户网络异常
        //不清空数据,给用户 一个良好的体验
        //1.2.1 下拉刷新 要注意不能清空 不处理
        //1.2.1 上拉加载更多 要注意不能添加 不处理
        if (mShowItems.size() > 0) {
            //添加的数据是身体的数据,跟头部没有关系
            if (items != null && items.size() > 0) {

                if (mLOADSTATE != LOADSTATE.MORELOAD) {
                    //清空数据
                    mShowItems.clear();
                }
                //添加数据
                mShowItems.addAll(items);
            } else {
                ToastUtil.showToast("网络异常,亲请重新刷新数据!");
                Utils.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        mFootViewLayout.changeView(FootViewLayout.FOOTSTAUTS.NOMORE);
                    }
                });
            }
        } else {
            //2. 如果总集合的数据等于0
            //2.1 如果有数据,那么添加
            //2.1.1:如果下拉刷新,可清可不清,因为以前数据就是0 不处理
            //2.1.2 :如果上拉刷新,添加数据  不处理
            //2.2 如果没有数据,返回我们的null
            //2.2.1:下拉刷新, 不处理
            //2.2.2:上拉加载更多,不处理
            if (items != null && items.size() > 0) {
                //直接添加数据
                mShowItems.addAll(items);
            } else {
                //不处理也行处理也行,处理直接返回null
            }
        }

  /*
        //在加载数据之前,把以前的头部脚干掉
        for (int i = 0; i < mShowItems.size(); i++) {
            if (mShowItems.get(i) instanceof HeadType) {
                mShowItems.remove(i);
            }
        }

        mShowItems.add(0, mNewHeadBean);*/


        //判断当前下拉的模式
        //只有在上拉加载更多数据的时候不会清空数据
/*        if (mLOADSTATE != LOADSTATE.MORELOAD) {
            mShowItems.clear();
        }

        //在加载数据之前,把以前的头部脚干掉
        for (int i = 0; i < mShowItems.size(); i++) {
            if (mShowItems.get(i) instanceof HeadType) {
                mShowItems.remove(i);
            }
        }*/

    /*    for (int i = 0; i < mShowItems.size(); i++) {
            if (mShowItems.get(i) instanceof FootType) {
                mShowItems.remove(i);
            }
        }*/


/*
        mShowItems.add(0, mNewHeadBean);

        mShowItems.addAll(newBodyBean.getResult().getItems());
*/

        //底部必须手动
        /*mShowItems.add(new FootBean());*/

        //更新adapter
        Utils.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                //刷新
                //mNewsListAdapter.notifyDataSetChanged();

                mNewsListAdapter.updateData();

                //更改状态
                mLOADSTATE = LOADSTATE.NONE;//这个转置空

                //停止刷新的方法
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        return mShowItems;
    }

    //传入的是假头是没有数据的
    @Override
    public void bindHeadView(FinalViewholder holder, int position, List<ItemType> showItems) {

    }

    @Override
    public abstract void bindBodyView(FinalViewholder holder, int position, List<ItemType> showItems);

    private FootViewLayout mFootViewLayout;

    @Override
    public void bindFootView(FootViewLayout footViewLayout) {
        this.mFootViewLayout = footViewLayout;
    }

    //请求头部数据,子类自己 去请求
    //子类不一定要去请求
    public void requestHeadData() {

    }

    //子类传入的数据
    public abstract List<? extends ItemType> getItemDatas(LOADSTATE LOADSTATE);


    //子类传入布局
    protected abstract int createBodyView();

    //头部布局
    public int getHeadView() {
        return 0;
    }

    //底部,上拉加载更多
    public boolean isRefreshShowFoot() {
        return false;
    }

    //控件下拉
    public boolean isRefreshShow() {
        return false;
    }

    //带下拉刷新动画的刷新方法
    public void animRefreshShow() {
        mSwipeRefreshLayout.setRefreshing(true);
        refresh();
    }

}
