package com.wangng.pindu.base;

import com.wangng.pindu.data.model.Theme;

/**
 * Created by wng on 2017/3/20.
 */

public interface OnThemeChangedListener {
    void onThemeChanged(Theme.ThemeBean themeBean, boolean isChecked);
}
