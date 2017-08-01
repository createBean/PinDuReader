package com.wangng.pindu.ui.home;

import com.wangng.pindu.data.model.StoryList;

import rx.Observable;

/**
 * Created by 小爱 on 2017/3/24.
 */

public class StoryModel implements StoryContract.Model {
    @Override
    public Observable<StoryList> getThemeStoryList(int themeId) {
        return DATA_MANAGER.getThemeStoryList(themeId);
    }
}
