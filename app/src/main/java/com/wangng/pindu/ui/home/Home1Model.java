package com.wangng.pindu.ui.home;

import com.wangng.pindu.data.model.News;

import rx.Observable;

/**
 * Created by wng on 2017/3/26.
 */

public class Home1Model implements Home1Contract.Model {
    @Override
    public Observable<News> getHomeStoryList() {
        return DATA_MANAGER.getHomeStoryList();
    }

    @Override
    public Observable<News> getBeforeHomeStoryList(long before) {
        return DATA_MANAGER.getBeforeHomeStoryList(before);
    }
}
