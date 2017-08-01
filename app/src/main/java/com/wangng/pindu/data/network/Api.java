package com.wangng.pindu.data.network;

import com.wangng.pindu.data.model.News;
import com.wangng.pindu.data.model.StoryDetail;
import com.wangng.pindu.data.model.StoryList;
import com.wangng.pindu.data.model.Theme;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by wng on 2017/2/21.
 */

public interface Api {

    @GET("api/4/themes")
    Observable<Theme> getTheme();

    @GET("api/4/news/latest")
    Observable<News> getHomeStory();

    @GET("api/4/news/before/{date}")
    Observable<News> getBeforeHomeStory(@Path("date") String date);

    @GET("api/4/theme/{id}")
    Observable<StoryList> getThemeStories(@Path("id") int id);

    @GET("api/4/news/{id}")
    Observable<StoryDetail> getStoryDetail(@Path("id") int id);
}
