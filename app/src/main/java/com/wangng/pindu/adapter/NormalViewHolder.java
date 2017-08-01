package com.wangng.pindu.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wangng.pindu.R;
import com.wangng.pindu.data.model.Story;

/**
 * Created by 小爱 on 2017/3/8.
 */

class NormalViewHolder extends RecyclerView.ViewHolder {

    public final View mView;
    public final TextView mTvTitle;
    public final ImageView mImageView;
    public Story mStory;
    public NormalViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        mImageView = (ImageView) itemView.findViewById(R.id.image_view);
    }
}
