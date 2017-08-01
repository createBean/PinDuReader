package com.wangng.pindu.ui.gestoslife;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wangng.pindu.R;

/**
 * Created by yu on 2017/4/5.
 */

public class GestosLifeItemView extends LinearLayout {
    protected TextView titleView;
    protected TextView timeView;
    private GestosLifeModel mModel;

    public GestosLifeItemView(Context context) {
        super(context);
        initializeData();
    }

    public GestosLifeItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initializeData();
    }

    public GestosLifeItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeData();
    }

    private void initializeData() {
        setOrientation(VERTICAL);
        inflate(getContext(), R.layout.gestos_life_item_view, this);
        initViews();
    }

    private void initViews() {
        titleView = (TextView) findViewById(R.id.tv_title);
        timeView = (TextView) findViewById(R.id.tv_time);
    }

    public void setModel(GestosLifeModel model) {
        mModel = model;
        titleView.setText(mModel.getTitle());
        timeView.setText(mModel.getTime());
    }
}
