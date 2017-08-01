package com.wangng.pindu.ui.edittheme;

import com.wangng.pindu.base.BaseModel;
import com.wangng.pindu.base.BasePresenter;
import com.wangng.pindu.base.BaseView;
import com.wangng.pindu.data.model.Theme;

import java.util.List;

import rx.Observable;

/**
 * Created by 小爱 on 2017/3/24.
 */

public interface AllThemeContract {

    interface View extends BaseView {
        void showLoadError();
        void showRecycleView(List<Theme.ThemeBean> themeBeen);
    }

    interface Model extends BaseModel {
        Observable<List<Theme.ThemeBean>> getAllTheme();
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        abstract void getAllTheme();
    }
}
