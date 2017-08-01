package com.wangng.pindu.ui.gestoslife;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.wangng.pindu.R;
import com.wangng.pindu.base.BaseFragment;
import com.wangng.pindu.base.BasePagerFragment;
import com.wangng.pindu.data.model.StoryList;
import com.wangng.pindu.util.ToastUtil;
import com.wangng.pindu.util.UIHelper;
import com.zhy.http.okhttp.OkHttpUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yu on 2017/4/5.
 */

public class GestosLifeListFragment extends BasePagerFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycle_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private List<GestosLifeModel> dataList = new ArrayList<>();
    private String ipString;
    private boolean mIsRefreshing = false;

    @SuppressWarnings("unused")
    public static GestosLifeListFragment newInstance(String ipString) {
        GestosLifeListFragment fragment = new GestosLifeListFragment();
        Bundle args = new Bundle();
        args.putString("ipString", ipString);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_gestos_life_list;
    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Bundle bundle = getArguments();
//        ipString = bundle.getString("ipString");
//    }

    @Override
    protected void initView() {
        Bundle bundle = getArguments();
        ipString = bundle.getString("ipString");
        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        lazyLoad();
        setAdapter();
        setRecycleViewTouchListenere();
    }

    private void setRecycleViewTouchListenere() {
        mRecyclerView.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean
                    onTouch(View v, MotionEvent event) {
                        if (mIsRefreshing) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
        );
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        if (!TextUtils.isEmpty(ipString)){
            getDataList();
        }
    }

    private void setAdapter() {
        mRecyclerView.setAdapter(mAdapter = new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                RecyclerView.ViewHolder viewHolder = null;
                switch (viewType) {
                    case 0:
                        GestosLifeItemView itemView = new GestosLifeItemView(getActivity());
                        viewHolder = new RecyclerView.ViewHolder(itemView) {
                        };
                        break;
                }
                if (viewHolder != null && viewHolder.itemView != null && viewHolder.itemView.getLayoutParams() == null) {
                    viewHolder.itemView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
                }
                return viewHolder;
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                switch (holder.getItemViewType()) {
                    case 0:
                        final GestosLifeModel model = ((GestosLifeModel) dataList.get(position));
                        final GestosLifeItemView itemView = (GestosLifeItemView) holder.itemView;
                        itemView.setModel(model);
                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                UIHelper.showGestosLifeDetail(getActivity(), model.getDetailUrl2());
                            }
                        });
                        break;
                }
            }

            @Override
            public int getItemCount() {
                return dataList.size();
            }

            @Override
            public int getItemViewType(int position) {
                Object obj = dataList.get(position);
                if (obj instanceof GestosLifeModel) {
                    return 0;
                }
                return -1;
            }
        });
    }

    private void getDataList() {
        GestosLifeService.Instance.getGestosLifeData(ipString)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mIsRefreshing = false;
                        mProgressBar.setVisibility(View.GONE);
                        mSwipeRefreshLayout.setRefreshing(false);
                        showLoadError();
                    }

                    @Override
                    public void onNext(ResponseBody body) {
                        mIsRefreshing = false;
                        mSwipeRefreshLayout.setRefreshing(false);
                        mProgressBar.setVisibility(View.GONE);
                        try {
                            String doc = new String(body.bytes(), "GBK");
                            Document document = Jsoup.parse(doc);
                            Elements es = document.getElementsByClass("p7").get(0).select("li");
                            for (Element e : es) {
                                String titleString = e.select("a").text();
                                String string1 = e.select("a").attr("href");
                                String detailUrl = string1.substring(25);
                                Elements elements = e.getElementsByClass("info");
                                for (Element element : elements) {
                                    GestosLifeModel model = new GestosLifeModel();
                                    model.setTitle(titleString);
                                    model.setTime(element.text());
                                    model.setDetailUrl(detailUrl);
                                    model.setDetailUrl2(string1);
                                    dataList.add(model);
                                }
                            }
                            mAdapter.notifyDataSetChanged();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void showLoadError() {
        ToastUtil.showSnackBar(mRecyclerView, R.string.network_error, R.string.retry, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataList();
            }
        });
    }

    @Override
    public void onRefresh() {
        mIsRefreshing = true;
        dataList.clear();
        getDataList();
    }

    public static class GestosLifeDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.set(0, 0, 0, 8);
        }
    }
}
