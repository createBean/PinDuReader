package com.wangng.pindu.ui.home;

import com.wangng.pindu.base.BaseModel;
import com.wangng.pindu.base.BasePresenter;
import com.wangng.pindu.base.BaseView;
import com.wangng.pindu.data.model.StoryList;

import rx.Observable;

/**
 * Created by wng on 2017/3/24.
 */

public interface StoryContract {

    interface View extends BaseView {
        void startLoading();
        void stopLoading();
        void showLoadError();
        void showRecycleView(StoryList storyList);
    }

    interface Model extends BaseModel {
        Observable<StoryList> getThemeStoryList(int themeId);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        abstract void getThemeStoryList(int themeId);
    }
}
