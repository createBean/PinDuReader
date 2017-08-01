package com.wangng.pindu.ui.bookmark;

import com.wangng.pindu.base.BaseModel;
import com.wangng.pindu.base.BasePresenter;
import com.wangng.pindu.base.BaseView;
import com.wangng.pindu.data.model.Story;

import java.util.List;

import rx.Observable;

/**
 * Created by wng on 2017/3/24.
 */

public interface BookmarkContract {

    interface View extends BaseView {
        void startLoading();
        void stopLoading();
        void showRecyclerView(List<Story> stories);
        void showLoadError();
    }

    interface Model extends BaseModel {
        Observable<List<Story>> getBookmarkedStory();
        boolean unbookmarkStory(int id);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        abstract void getBookmarkStory();
        abstract void unbookmarkStory(int id);
    }
}
