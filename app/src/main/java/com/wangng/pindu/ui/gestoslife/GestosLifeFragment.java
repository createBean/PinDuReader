package com.wangng.pindu.ui.gestoslife;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wangng.pindu.R;
import com.wangng.pindu.adapter.MainPageAdapter;
import com.wangng.pindu.base.BaseView;
import com.wangng.pindu.ui.home.Home1Fragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yu on 2017/4/5.
 */

public class GestosLifeFragment extends Fragment implements BaseView {
    @BindView(R.id.tab_layout)
    TabLayout mTablayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    private Unbinder mUnbinder;
    private ArrayList<Fragment> fragmentList;
    private ArrayList<String> titles;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titles = new ArrayList<>();
        ArrayList<String> titlesIp = new ArrayList<>();
        fragmentList = new ArrayList<>();
        addTitleData(titles, titlesIp);
        for (int i = 0; i < titles.size(); i++) {
            String ipString = titlesIp.get(i);
            fragmentList.add(GestosLifeListFragment.newInstance(ipString));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gestos_life, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        mTablayout.setupWithViewPager(mViewPager);
        MainPageAdapter pageAdapter = new MainPageAdapter(getChildFragmentManager(), fragmentList, titles);
        mViewPager.setAdapter(pageAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    private void addTitleData(ArrayList<String> titles, ArrayList<String> titlesIp) {
        titles.add("人生感悟");
        titlesIp.add("renshengganwu");
        titles.add("人生哲理");
        titlesIp.add("renshengzheli");
        titles.add("青年");
        titlesIp.add("qingnianwenzhai");
        titles.add("读者");
        titlesIp.add("duzhewenzhai");
        titles.add("意林");
        titlesIp.add("yilinzazhi");
        titles.add("故事会");
        titlesIp.add("gushihui");
        titles.add("励志故事");
        titlesIp.add("lizhigushi");
        titles.add("哲理故事");
        titlesIp.add("zheligushi");
        titles.add("小故事大道理");
        titlesIp.add("xiaogushi");
        titles.add("寓言故事");
        titlesIp.add("yuyangushi");
        titles.add("伊索寓言");
        titlesIp.add("yisuoyuyan");
    }
}
