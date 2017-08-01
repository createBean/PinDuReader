package com.wangng.pindu.ui.home;

import com.wangng.pindu.data.model.StoryList;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 小爱 on 2017/3/24.
 */

public class StoryPresenter extends StoryContract.Presenter {
    @Override
    void getThemeStoryList(int themeId) {
        mView.startLoading();
        mModel.getThemeStoryList(themeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StoryList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.stopLoading();
                        mView.showLoadError();
                    }

                    @Override
                    public void onNext(StoryList storyList) {
                        mView.stopLoading();
                        mView.showRecycleView(storyList);
                    }
                });
    }
}
