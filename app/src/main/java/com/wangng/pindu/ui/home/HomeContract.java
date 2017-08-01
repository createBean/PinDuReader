package com.wangng.pindu.ui.home;

import com.wangng.pindu.base.BasePresenter;
import com.wangng.pindu.base.BaseView;
import com.wangng.pindu.data.model.News;

/**
 * Created by wng on 2017/2/20.
 */

public interface HomeContract {
    interface View extends BaseView {
        void showLoading();
        void dismissLoading();
        void showSuccess(News news);
        void showError();
    }

    abstract class Presenter extends BasePresenter {

    }
}
